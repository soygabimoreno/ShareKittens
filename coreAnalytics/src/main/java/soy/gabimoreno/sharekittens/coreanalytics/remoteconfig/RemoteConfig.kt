package soy.gabimoreno.sharekittens.coreanalytics.remoteconfig

import arrow.core.Either
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import soy.gabimoreno.sharekittens.coreanalytics.error.ErrorTrackerComponent

class RemoteConfig(
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
    errorTrackerComponent: ErrorTrackerComponent
) {

    companion object {
        private const val AMPLITUDE_API_KEY_VERSUS = "AMPLITUDE_API_KEY_VERSUS"
    }

    init {
        val defaults = mapOf(
            AMPLITUDE_API_KEY_VERSUS to ""
        )

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        firebaseRemoteConfig.setDefaultsAsync(defaults)
        firebaseRemoteConfig
            .fetchAndActivate()
            .addOnFailureListener {
                errorTrackerComponent.trackError(it)
            }
    }

    suspend fun getAmplitudeApiKey(): Either<Throwable, String> = withContext(Dispatchers.IO) {
        Either.catch { firebaseRemoteConfig.getString(AMPLITUDE_API_KEY_VERSUS) }
    }
}
