package soy.gabimoreno.sharekittens.core.presentation.kittens

import androidx.lifecycle.viewModelScope
import com.giphy.sdk.core.models.Media
import kotlinx.coroutines.launch
import soy.gabimoreno.libbase.viewmodel.BaseViewModel
import soy.gabimoreno.sharekittens.core.presentation.kittens.analytics.KittensEvents
import soy.gabimoreno.sharekittens.coreanalytics.AnalyticsTrackerComponent

class KittensViewModel(
    private val analyticsTrackerComponent: AnalyticsTrackerComponent
) : BaseViewModel<
        KittensViewModel.ViewState,
        KittensViewModel.ViewEvents>() {

    init {
        handleLoadContent()
    }

    private fun handleLoadContent() {
        analyticsTrackerComponent.trackEvent(KittensEvents.ScreenKittens)
        updateViewState(ViewState.Loading)
        viewModelScope.launch {
            updateViewState(ViewState.Content("foo"))
        }
    }

    fun handleGifSelected(media: Media) {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.ShareGif(media))
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        object Error : ViewState()
        data class Content(val foo: String) : ViewState()
    }

    sealed class ViewEvents {
        data class ShareGif(val media: Media) : ViewEvents()
    }
}
