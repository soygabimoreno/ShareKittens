package soy.gabimoreno.sharekittens.core.presentation.main

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import soy.gabimoreno.libbase.viewmodel.StatelessBaseViewModel
import soy.gabimoreno.sharekittens.core.presentation.main.data.analytics.MainEvents
import soy.gabimoreno.sharekittens.coreanalytics.AnalyticsTrackerComponent

class MainViewModel(
    private val analyticsTrackerComponent: AnalyticsTrackerComponent
) : StatelessBaseViewModel<
        MainViewModel.ViewEvents>() {

    init {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.NavigateToKittens)
        }
    }

    fun handleShareClicked() {
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.ClickShare)
            sendViewEvent(ViewEvents.Share)
        }
    }

    fun handleEmailClicked() {
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.ClickEmail)
            sendViewEvent(ViewEvents.SendEmail)
        }
    }

    fun handleRateClicked() {
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.ClickRate)
            sendViewEvent(ViewEvents.Rate)
        }
    }

    fun handleInfoClicked() {
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.ClickInfo)
            sendViewEvent(ViewEvents.NavigateToWeb("https://gabimoreno.soy"))
        }
    }

    sealed class ViewEvents {
        object NavigateToKittens : ViewEvents()
        object Share : ViewEvents()
        object SendEmail : ViewEvents()
        object Rate : ViewEvents()
        data class NavigateToWeb(val uriString: String) : ViewEvents()
    }
}
