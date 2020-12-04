package soy.gabimoreno.sharekittens.core.presentation.kittens.analytics

import soy.gabimoreno.sharekittens.coreanalytics.AnalyticsEvent

private const val SCREEN_KITTENS = "SCREEN_KITTENS"

sealed class KittensEvents(
    override val name: String,
    override val parameters: Map<String, Any> = mapOf()
) : AnalyticsEvent {

    object ScreenKittens : KittensEvents(SCREEN_KITTENS)
}
