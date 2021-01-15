package soy.gabimoreno.sharekittens.core.presentation.kittens.analytics

import soy.gabimoreno.sharekittens.coreanalytics.AnalyticsEvent

private const val SCREEN_KITTENS = "SCREEN_KITTENS"
private const val AD_ERROR = "AD_ERROR"
private const val AD_LOADED = "AD_LOADED"
private const val AD_CLICKED = "AD_CLICKED"
private const val AD_LOGGING_IMPRESSION = "AD_LOGGING_IMPRESSION"

private const val ERROR = "ERROR"

sealed class KittensEvents(
    override val name: String,
    override val parameters: Map<String, Any> = mapOf()
) : AnalyticsEvent {

    object ScreenKittens : KittensEvents(SCREEN_KITTENS)

    class AdError(error: String) : KittensEvents(
        AD_ERROR,
        mapOf(
            ERROR to error
        )
    )

    object AdLoaded : KittensEvents(AD_LOADED)
    object AdClicked : KittensEvents(AD_CLICKED)
    object AdLoggingImpression : KittensEvents(AD_LOGGING_IMPRESSION)
}
