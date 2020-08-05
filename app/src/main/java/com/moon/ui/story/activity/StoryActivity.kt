package com.moon.ui.story.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.dependencies.buria.android.support.daggerktx2.viewmodel.ViewModelProvidersFactory
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.moon.R
import com.moon.model.Story
import com.moon.ui.StoryViewModel
import com.moon.ui.story.adapter.StoriesAdapter
import com.moon.ui.story.fragment.AddBranchFragment
import com.moon.ui.story.fragment.ShowStoryFragmentArgs
import com.moon.ui.story.fragment.ShowStoryFragmentDirections
import com.moon.ui.story.fragment.StoryListFragmentDirections
import com.skydoves.balloon.*
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_story_acticity.*
import java.io.Serializable
import javax.inject.Inject

class StoryActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    lateinit var storyViewModel: StoryViewModel
    private var selectedStory: Story? = null

    var currentFragment: Int = R.id.storyListFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_acticity)

        storyViewModel =
            ViewModelProviders.of(this, viewModelProvidersFactory).get(StoryViewModel::class.java)

        val navigationController = fragment2.findNavController()
        val addVisibilityChange: FloatingActionButton.OnVisibilityChangedListener =
            object : FloatingActionButton.OnVisibilityChangedListener() {
                override fun onHidden(fab: FloatingActionButton?) {
                    super.onHidden(fab)
                    when (bapShowStory.fabAlignmentMode) {
                        BottomAppBar.FAB_ALIGNMENT_MODE_CENTER -> {
                            bapShowStory.replaceMenu(R.menu.menu_show_story)
                            bapShowStory.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                            fabStory.setImageDrawable(getDrawable(R.drawable.ic_clapping_fill))
                            currentFragment = R.id.showStoryFragment
                        }
                        BottomAppBar.FAB_ALIGNMENT_MODE_END -> {
                            bapShowStory.replaceMenu(R.menu.menu_main)
                            bapShowStory.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                            fabStory.setImageDrawable(getDrawable(R.drawable.ic_add_24))
                            currentFragment = R.id.storyListFragment

                        }
                        else -> BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    }
                    fabStory.show()
                }
            }

        // go to show story detail fragment
        navigationController.addOnDestinationChangedListener { _, destination, bundle ->
            selectedStory = bundle?.getParcelable("story")
            fabStory.hide(addVisibilityChange)
        }

        // fabStory onClick listener
        fabStory.setOnClickListener {
            when (currentFragment) {
                R.id.showStoryFragment -> {
                    //show balloon clap count
                    val balloon = createBalloon(this) {
                        setArrowSize(10)
                        setWidthRatio(0.15f)
                        setHeight(45)
                        setArrowPosition(0.5f)
                        setCornerRadius(4f)
                        setAlpha(0.9f)
                        autoDismissDuration = 500
                        var clap = (storyViewModel.observeClap().value?.data?.clap ?: -1)
                        if (clap == -1)
                            clap = selectedStory?.clapCount ?: -1
                        setText("+ ${(clap) + if (clap == 50) 0 else 1}")
                        setTextColorResource(R.color.colorSoftWhite)
                        setBackgroundColorResource(R.color.colorPrimary)
                        onBalloonClickListener?.let { it1 -> setOnBalloonClickListener(it1) }
                        setBalloonAnimation(BalloonAnimation.FADE)
                        setLifecycleOwner(lifecycleOwner)
                    }
                    fabStory.showAlignTop(balloon)
                    storyViewModel.clapStory(storyId = selectedStory?.id ?: "-1", clapCount = 1)
                }
                R.id.storyListFragment -> {
                    val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this)
                    startActivity(Intent(applicationContext, AddStoryActivity::class.java), activityOptions.toBundle())
//                    val extras = ActivityNavigatorExtras(activityOptions)
//                    fragment2.findNavController().navigate(StoryListFragmentDirections.asListFragmentToShowStoryActivity(), extras)
                }
            }
        }

        //menu click
        bapShowStory.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mnBranch -> {
                    Log.d("TAG", "onCreate: add_Branch")
                    navigationController.navigate(ShowStoryFragmentDirections.actionShowStoryFragmentToAddBranchFragment(storyId = selectedStory?.id ?: ""))
                }
                R.id.mnMerge -> {
                    Log.d("TAG", "onCreate: add_Merge")
                    startActivity(Intent(applicationContext, JoinRequestActivity::class.java).putExtra("story", selectedStory),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
                }
                R.id.mnJoinStory -> {
                    storyViewModel.joinStory(storyId = selectedStory?.id ?: "")
                }
                R.id.mnEdit -> {
                    startActivity(Intent(applicationContext, EditStoryActivity::class.java).putExtra("story", selectedStory),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
                }
                R.id.mnShowMergeRequests -> {
                    startActivity(Intent(applicationContext, MergeRequestsActivity::class.java).putExtra("story", selectedStory),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
                }
            }
            true
        }
        storyViewModel.observeClap().observe(this, Observer {

        })
    }


}
