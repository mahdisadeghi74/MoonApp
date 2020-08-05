package com.moon.ui.story.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.dependencies.buria.android.support.daggerktx2.viewmodel.ViewModelProvidersFactory
import com.moon.R
import com.moon.model.Story
import com.moon.ui.StoryViewModel
import com.moon.ui.comparative.ComparativeViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_submit_merge_request.*
import javax.inject.Inject

class SubmitMergeRequestFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory


    val args: SubmitMergeRequestFragmentArgs by navArgs()
    var selectedStory: Story? = null

    lateinit var comparativeViewModel: ComparativeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_submit_merge_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedStory = args.story

        comparativeViewModel = ViewModelProviders.of(this, viewModelProvidersFactory)
            .get(ComparativeViewModel::class.java)
        if (selectedStory != null)
            comparativeViewModel.comparisionStories(selectedStory!!)
        observe()
        changeScrollViews()
    }

    private fun changeScrollViews() {
        nsvOld.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            nsvNew.scrollTo(scrollX, scrollY)
        }
        nsvNew.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            nsvOld.scrollTo(scrollX, scrollY)
        }
    }

    private fun observe() {
        comparativeViewModel.observeComparative().observe(viewLifecycleOwner, Observer {
            val spannableAdd = SpannableString(it.data?.storyContentNew)
            val spannableDelete = SpannableString(it.data?.storyContentOld)
            it.data?.addPosition?.forEach {
                spannableAdd.setSpan(
                    BackgroundColorSpan(Color.GREEN),
                    it,
                    it + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            it.data?.deletePosition?.forEach {
                spannableDelete.setSpan(
                    BackgroundColorSpan(Color.RED),
                    it,
                    it + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            etOldContent.setText(spannableDelete)
            etNewContent.setText(spannableAdd)
        })
    }


}