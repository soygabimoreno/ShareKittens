package soy.gabimoreno.sharekittens.core.presentation.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.browser.customtabs.CustomTabsIntent
import androidx.transition.Slide
import org.koin.androidx.viewmodel.ext.android.viewModel
import soy.gabimoreno.libbase.activity.StatelessBaseActivity
import soy.gabimoreno.libframework.extension.exhaustive
import soy.gabimoreno.libframework.extension.navigateTo
import soy.gabimoreno.sharekittens.core.R
import soy.gabimoreno.sharekittens.core.presentation.kittens.KittensFragment
import soy.gabimoreno.sharekittens.coreres.R as CoreResR

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(
            R.menu.menu_main,
            menu
        )
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuShare -> {
                viewModel.handleShareClicked()
                true
            }
            R.id.menuEmail -> {
                viewModel.handleEmailClicked()
                true
            }
            R.id.menuRate -> {
                viewModel.handleRateClicked()
                true
            }
            R.id.menuInfo -> {
                viewModel.handleInfoClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun initUI() {}

    override fun handleViewEvent(viewEvent: MainViewModel.ViewEvents) {
        when (viewEvent) {
            is MainViewModel.ViewEvents.NavigateToKittens -> navigateToKittens()
            MainViewModel.ViewEvents.Share -> share()
            MainViewModel.ViewEvents.SendEmail -> sendEmail()
            MainViewModel.ViewEvents.Rate -> rate()
            is MainViewModel.ViewEvents.NavigateToWeb -> navigateToWeb(viewEvent.uriString)
        }.exhaustive
    }

    private fun navigateToKittens() {
        navigateTo(
            R.id.flContainer,
            KittensFragment.newInstance().apply {
                enterTransition = Slide(Gravity.END)
                exitTransition = Slide(Gravity.START)
            }
        )
    }

    private fun share() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                getString(CoreResR.string.share_text)
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(
            sendIntent,
            getString(CoreResR.string.share_kitten)
        )
        startActivity(shareIntent)
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(
            Intent.EXTRA_EMAIL,
            arrayOf(getString(CoreResR.string.email_to))
        )
        intent.putExtra(
            Intent.EXTRA_SUBJECT,
            getString(CoreResR.string.email_subject)
        )
        startActivity(
            Intent.createChooser(
                intent,
                getString(CoreResR.string.email_title)
            )
        )
    }

    private fun rate() {
        val appPackageName = "soy.gabimoreno.sharekittens"
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (exception: Exception) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }

    private fun navigateToWeb(uriString: String) {
        CustomTabsIntent.Builder()
            .setStartAnimations(
                this,
                CoreResR.anim.browser_in_right,
                CoreResR.anim.browser_out_left
            )
            .setExitAnimations(
                this,
                CoreResR.anim.browser_in_left,
                CoreResR.anim.browser_out_right
            )
            .build()
            .launchUrl(
                this,
                Uri.parse(uriString)
            )
    }
}
