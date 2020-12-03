package soy.gabimoreno.sharekittens.coreanalytics

interface AnalyticsEvent {
    val name: String
    val parameters: Map<String, Any>
}
