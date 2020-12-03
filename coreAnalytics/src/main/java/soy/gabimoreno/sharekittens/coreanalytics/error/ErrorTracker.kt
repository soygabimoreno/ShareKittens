package soy.gabimoreno.sharekittens.coreanalytics.error

class ErrorTracker(
    private val components: List<ErrorTrackerComponent>
) : ErrorTrackerComponent {
    override fun <E : ErrorEvent> trackError(event: E) {
        components.forEach { it.trackError(event) }
    }
}
