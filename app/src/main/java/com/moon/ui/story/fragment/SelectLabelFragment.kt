package com.moon.ui.story.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.dependencies.buria.android.support.daggerktx2.viewmodel.ViewModelProvidersFactory
import com.google.android.material.chip.Chip
import com.moon.R
import com.moon.model.Label
import com.moon.model.Story
import com.moon.ui.StoryViewModel
import com.moon.ui.label.LabelViewModel
import com.moon.util.addChipView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_select_label.*
import javax.inject.Inject

class SelectLabelFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    lateinit var labelViewMode: LabelViewModel
    lateinit var storyViewModel: StoryViewModel

    val args: SelectLabelFragmentArgs by navArgs()
    var story: Story? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("TAG", "observe:label 4")
        labelViewMode =
            ViewModelProviders.of(this, viewModelProvidersFactory).get(LabelViewModel::class.java)
        storyViewModel =
            ViewModelProviders.of(this, viewModelProvidersFactory).get(StoryViewModel::class.java)

        story = args.Story
        labelViewMode.getLabels()
        observe()

    }

    private fun observeAddStory() {
        storyViewModel.observeAddStory().observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "observeAddStory: ${it.status} : ${it.data?.id}")
            requireActivity().finish()
        })
    }

    private fun observe() {
        labelViewMode.observe().observe(viewLifecycleOwner, Observer {
            Log.d("TAG", "observe:label ${it.data}")
            val label = it.data
            val arrayAdapter: ArrayAdapter<Label> = ArrayAdapter(
                requireContext(), android.R.layout.simple_dropdown_item_1line,
                label ?: arrayListOf()
            )
            actLabel.setAdapter(arrayAdapter)
            actLabel.setOnItemClickListener { _, view, _, _ -> addLabelToChip((view as TextView).text.toString())}
        })
    }

    private fun addLabelToChip(labelName: String?) {
        actLabel.setText("")
        Chip(context).apply {
            text = labelName
            isClickable = false
            isCloseIconVisible = true
            chgLabels.addChipView(this)
            setOnCloseIconClickListener {removedItem -> chgLabels.removeView(removedItem) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imbNext.setOnClickListener {
            story?.label?.addAll(getSelectedLabels())
            storyViewModel.createStory(story = story ?: Story())
            observeAddStory()
        }

        imbBack.setOnClickListener {
            view.findNavController().navigateUp()
        }
        actLabel.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                addLabelToChip(v.text.toString().replace(" ", "_"))
                true
            }else false
        }
    }

    private fun getSelectedLabels(): ArrayList<Label> {
        val labels = arrayListOf<Label>()
        chgLabels.forEach {chip ->
            labels.add(Label(name = (chip as Chip).text.toString()))
        }
        return labels
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_label, container, false)
    }
}