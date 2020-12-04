package soy.gabimoreno.sharekittens.di

import soy.gabimoreno.sharekittens.coreanalytics.analyticsTrackerModule
import soy.gabimoreno.sharekittens.coreanalytics.error.errorTrackerModule
import soy.gabimoreno.sharekittens.coreanalytics.remoteconfig.remoteConfigModule
import soy.gabimoreno.sharekittens.coredata.di.coreDataModule

val serviceLocator = listOf(
    appModule,
//
//    mainModule,
//    mainListModule,
//    mainDetailModule,
//
    remoteConfigModule,
    analyticsTrackerModule,
    errorTrackerModule,

    coreDataModule,
//    coreNetworkModule,
//    coreDbModule,
)
