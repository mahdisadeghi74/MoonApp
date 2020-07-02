package com.moon.ui.story.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.moon.R
import com.moon.model.Category
import kotlinx.android.synthetic.main.fragment_select_category.*
import kotlinx.android.synthetic.main.fragment_select_category.view.*
import kotlinx.android.synthetic.main.fragment_select_category.view.imbCloseSelectCategory

class SelectCategoryFragment : Fragment() {

    private var category: Category? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.btnPoem.setOnClickListener {
            category = Category(id = Category.POEM_ID)

            view.findNavController().navigate(SelectCategoryFragmentDirections.actionSelectCategoryFragmentToAddStoryContentFragment(
                category!!))
        }
        view.btnStory.setOnClickListener {
            category = Category(id = Category.STORY_ID)
            view.btnPoem.callOnClick()
        }
        view.btnTranslate.setOnClickListener {
            category = Category(id = Category.TRANSLATE_ID)
            view.btnPoem.callOnClick()
        }

        imbCloseSelectCategory.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    fun getCategory(): Category?{
        return category
    }
}