package soy.gabimoreno.sharekittens

object ApiKeyRetriever {

    init {
        System.loadLibrary("api-keys")
    }

    external fun getGiphyApiKey(): String
    external fun getAmplitudeApiKey(): String
}
