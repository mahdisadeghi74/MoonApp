package com.moon.ui.story.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.findNavController
import com.moon.R
import com.moon.model.Category
import com.moon.model.Story
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add_story_content.*
import kotlinx.android.synthetic.main.fragment_add_story_content.view.*
import kotlinx.android.synthetic.main.fragment_add_story_content.view.imbNext

class AddStoryContentFragment : Fragment() {
    var story: Story = Story()
    var category: Category? = null
    val args: AddStoryContentFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        category = args.category

        imbNext.setOnClickListener {
            story = Story(title = etStoryTitle.text.toString(), content = context.toString(), category = category)
            view.findNavController().navigate(AddStoryContentFragmentDirections.actionAddStoryContentFragmentToSelectLabelFragment(Story = story))
        }

        imbBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        etStoryTitle.doAfterTextChanged {
            imbNext.visibility = if (checkFillContent()) View.VISIBLE else View.GONE
        }
        etStoryContent.doAfterTextChanged {
            imbNext.visibility = if (checkFillContent()) View.VISIBLE else View.GONE
        }
    }

    private fun checkFillContent() : Boolean{
        return etStoryContent.text.toString().isNotEmpty() && etStoryTitle.text.toString().isNotEmpty()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_story_content, container, false)
    }
}
