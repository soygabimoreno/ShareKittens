package soy.gabimoreno.sharekittens.coreanalytics

interface AnalyticsTrackerComponent {
    fun <E : AnalyticsEvent> trackEvent(event: E)
}
