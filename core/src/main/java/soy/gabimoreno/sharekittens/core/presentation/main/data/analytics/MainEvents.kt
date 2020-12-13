package soy.gabimoreno.sharekittens.core.presentation.main.data.analytics

import soy.gabimoreno.sharekittens.coreanalytics.AnalyticsEvent

private const val SCREEN_MAIN = "SCREEN_MAIN"
private const val CLICK_SHARE = "CLICK_SHARE"
private const val CLICK_EMAIL = "CLICK_EMAIL"
private const val CLICK_RATE = "CLICK_RATE"
private const val CLICK_INFO = "CLICK_INFO"

sealed class MainEvents(
    override val name: String,
    override val parameters: Map<String, Any> = mapOf()
) : AnalyticsEvent {

    object ScreenMain : MainEvents(SCREEN_MAIN)
    object ClickShare : MainEvents(CLICK_SHARE)
    object ClickEmail : MainEvents(CLICK_EMAIL)
    object ClickRate : MainEvents(CLICK_RATE)
    object ClickInfo : MainEvents(CLICK_INFO)
}
