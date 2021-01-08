package soy.gabimoreno.sharekittens.core.presentation.kittens

import androidx.lifecycle.viewModelScope
import com.giphy.sdk.core.models.Media
import kotlinx.coroutines.launch
import soy.gabimoreno.libbase.viewmodel.BaseViewModel
import soy.gabimoreno.sharekittens.core.presentation.kittens.analytics.KittensEvents
import soy.gabimoreno.sharekittens.core.presentation.kittens.domain.kittenQueries
import soy.gabimoreno.sharekittens.coreanalytics.AnalyticsTrackerComponent
import kotlin.random.Random

class KittensViewModel(
    private val analyticsTrackerComponent: AnalyticsTrackerComponent
) : BaseViewModel<
    KittensViewModel.ViewState,
    KittensViewModel.ViewEvents>() {

    init {
        handleLoadContent()
    }

    fun handleLoadContent() {
        analyticsTrackerComponent.trackEvent(KittensEvents.ScreenKittens)
        updateViewState(ViewState.Loading)
        viewModelScope.launch {
            val position = Random.nextInt(
                0,
                kittenQueries.size - 1
            )
            val query = kittenQueries[position]
            updateViewState(ViewState.Content(query))
        }
    }

    fun handleGifSelected(media: Media) {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.ShowOverlay)
            val viewState = (getViewState() as ViewState.Content).copy(media = media)
            updateViewState(viewState)
        }
    }

    fun shareGif() {
        viewModelScope.launch {
            val media = (getViewState() as ViewState.Content).media
            sendViewEvent(ViewEvents.ShareGif(media!!))
        }
    }

    fun handleGifDownloaded() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.HideOverlay)
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        object Error : ViewState()
        data class Content(
            val query: String,
            val media: Media? = null
        ) : ViewState()
    }

    sealed class ViewEvents {
        data class ShareGif(val media: Media) : ViewEvents()
        object ShowOverlay : ViewEvents()
        object HideOverlay : ViewEvents()
    }
}
