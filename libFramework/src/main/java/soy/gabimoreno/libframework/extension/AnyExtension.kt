package soy.gabimoreno.libframework.extension

import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

val Any?.exhaustive
    get() = Unit

inline fun <reified T : Any> String.toList(): List<T> =
    Gson().fromJson(
        this,
        Array<T>::class.java
    ).asList()

fun Any.toJSONArray(): JSONArray {
    return JSONArray(
        Gson().toJson(
            this,
            Any::class.java
        )
    )
}

fun Any.toJSONObject(): JSONObject {
    return JSONObject(
        Gson().toJson(
            this,
            Any::class.java
        )
    )
}
