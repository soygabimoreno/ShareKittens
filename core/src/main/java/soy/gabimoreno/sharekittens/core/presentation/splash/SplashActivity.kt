package soy.gabimoreno.sharekittens.core.presentation.splash

import android.animation.Animator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import soy.gabimoreno.sharekittens.core.R
import soy.gabimoreno.sharekittens.core.presentation.main.MainActivity
import soy.gabimoreno.sharekittens.coreres.R as CoreResR

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lav?.addAnimatorListener(
            object : Animator.AnimatorListener {
                val initialFadeInDuration = resources.getInteger(CoreResR.integer.splash_time).toLong()

                override fun onAnimationStart(animation: Animator?) {
                    lav
                        .animate()
                        .alpha(1f)
                        .setDuration(initialFadeInDuration)
                        .start()
                }

                override fun onAnimationEnd(animation: Animator?) {
                    navigateToMain()
                    overridePendingTransition(
                        CoreResR.anim.fade_in,
                        CoreResR.anim.fade_out
                    )
                }

                override fun onAnimationRepeat(animation: Animator?) {}

                override fun onAnimationCancel(animation: Animator?) {}
            })
    }

    private fun navigateToMain() {
        MainActivity.launch(this)
        finish()
    }
}
