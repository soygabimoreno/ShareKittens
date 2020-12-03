package soy.gabimoreno.sharekittens.coreanalytics.remoteconfig

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import org.koin.dsl.module

val remoteConfigModule = module {
    single {
        RemoteConfig(
            firebaseRemoteConfig = FirebaseRemoteConfig.getInstance(),
            errorTrackerComponent = get()
        )
    }
}
