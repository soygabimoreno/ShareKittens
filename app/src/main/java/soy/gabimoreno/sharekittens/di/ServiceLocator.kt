package soy.gabimoreno.sharekittens.di

import soy.gabimoreno.sharekittens.core.presentation.kittens.kittensModule
import soy.gabimoreno.sharekittens.core.presentation.main.mainModule
import soy.gabimoreno.sharekittens.coreanalytics.analyticsTrackerModule
import soy.gabimoreno.sharekittens.coreanalytics.error.errorTrackerModule
import soy.gabimoreno.sharekittens.coredata.di.coreDataModule

val serviceLocator = listOf(
    mainModule,
    kittensModule,

    analyticsTrackerModule,
    errorTrackerModule,

    coreDataModule,
//    coreNetworkModule,
//    coreDbModule,
)
