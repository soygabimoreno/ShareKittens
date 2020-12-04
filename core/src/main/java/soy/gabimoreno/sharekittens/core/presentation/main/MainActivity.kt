package soy.gabimoreno.sharekittens.core.presentation.main

import android.content.Context
import android.content.Intent
import android.view.Gravity
import androidx.transition.Slide
import com.giphy.sdk.ui.views.GiphyDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import soy.gabimoreno.libbase.activity.StatelessBaseActivity
import soy.gabimoreno.libframework.extension.exhaustive
import soy.gabimoreno.libframework.extension.navigateTo
import soy.gabimoreno.sharekittens.core.R
import soy.gabimoreno.sharekittens.core.presentation.kittens.KittensFragment

class MainActivity : StatelessBaseActivity<
        MainViewModel.ViewEvents,
        MainViewModel
        >() {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(
                context,
                MainActivity::class.java
            )
            context.startActivity(intent)
        }
    }

    override val layoutResId = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else super.onBackPressed()
    }

    override fun initUI() {
        GiphyDialogFragment.newInstance().show(
            supportFragmentManager,
            "GiphyDialogFragment"
        )
    }

    override fun handleViewEvent(viewEvent: MainViewModel.ViewEvents) {
        when (viewEvent) {
            is MainViewModel.ViewEvents.NavigateToMainList -> navigateToMainList()
        }.exhaustive
    }

    private fun navigateToMainList() {
        navigateTo(
            R.id.flContainer,
            KittensFragment.newInstance().apply {
                enterTransition = Slide(Gravity.END)
                exitTransition = Slide(Gravity.START)
            }
        )
    }
}