
package soy.gabimoreno.sharekittens

import android.app.Application
import android.os.StrictMode
import com.amplitude.api.AmplitudeClient
import com.facebook.ads.AudienceNetworkAds
import com.giphy.sdk.ui.Giphy
import com.google.firebase.FirebaseApp
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import soy.gabimoreno.libframework.KLog
import soy.gabimoreno.sharekittens.di.serviceLocator

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        KLog.launch(BuildConfig.DEBUG)
        initKoin()
        initFirebase()
        initAmplitude()
        initGiphy()
        initFacebookAudience()
        enableShareGifs()
    }

    private fun initKoin() {
        startKoin {
            if (BuildConfig.DEBUG) logger(AndroidLogger(Level.ERROR))
            androidContext(this@App)
            modules(serviceLocator)
        }
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun initAmplitude() {
        val amplitudeApiKey = ApiKeyRetriever.getAmplitudeApiKey()
        val amplitudeClient: AmplitudeClient by inject()
        amplitudeClient.initialize(
            this,
            amplitudeApiKey
        )
    }

    private fun initGiphy() {
        val giphyApiKey = ApiKeyRetriever.getGiphyApiKey()
        Giphy.configure(
            this,
            giphyApiKey
        )
    }

    private fun initFacebookAudience() {
        AudienceNetworkAds.initialize(this)
    }

    private fun enableShareGifs() {
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }
}
