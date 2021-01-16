package soy.gabimoreno.sharekittens.core

object ApiKeyRetriever {

    init {
        System.loadLibrary("api-keys")
    }

    external fun getGiphyApiKey(): String
    external fun getAmplitudeApiKey(): String
    external fun getFacebookAudiencePlacementId(): String
}
