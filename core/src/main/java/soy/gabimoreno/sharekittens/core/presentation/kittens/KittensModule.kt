package soy.gabimoreno.sharekittens.core.presentation.kittens

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val kittensModule = module {
    scope(named<KittensFragment>()) {
        viewModel {
            KittensViewModel(
                analyticsTrackerComponent = get()
            )
        }
    }
}
