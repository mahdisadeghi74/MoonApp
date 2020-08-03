package com.moon.ui.story.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.dependencies.buria.android.support.daggerktx2.viewmodel.ViewModelProvidersFactory
import com.moon.R
import com.moon.ui.Response
import com.moon.ui.StoryViewModel
import com.moon.util.setNavigationResult
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add_branch.*
import javax.inject.Inject

class AddBranchFragment: DaggerFragment() {

    companion object{
        val ADD_BRANCH_RESULT: String = "ADD_BRANCH_RESULT"
    }
    val args: AddBranchFragmentArgs by navArgs()
    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    lateinit var storyViewModel: StoryViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storyViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(StoryViewModel::class.java)

        observe()

        etBranchName.doAfterTextChanged {
            if (it?.toString()?.isNotEmpty() == true) {
               imbNext.visibility = View.VISIBLE
            }else imbNext.visibility = View.GONE
        }

        imbNext.setOnClickListener {
            storyViewModel.createBranch(args.storyId, etBranchName.text.toString())
        }
        imbBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun observe() {
        storyViewModel.observeAddStory().observe(viewLifecycleOwner, Observer {story ->
            if (story.status == Response.Status.SUCCESS){
                setNavigationResult(story.data, ADD_BRANCH_RESULT)
                view?.findNavController()?.navigateUp()
            }else{
                Toast.makeText(context, story.data?.title, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_branch, container, false)
    }
}