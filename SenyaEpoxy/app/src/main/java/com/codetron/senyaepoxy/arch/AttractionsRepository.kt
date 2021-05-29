package com.codetron.senyaepoxy.arch

import android.content.Context
import androidx.annotation.RawRes
import com.codetron.senyaepoxy.data.Attraction
import com.codetron.senyaepoxy.data.AttractionResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class AttractionsRepository(private val context: Context) {

    private val moshi: Moshi by lazy {
        Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    fun parseAttractions(@RawRes resId: Int): List<Attraction> {
        return try {
            val textFromFile =
                context.resources.openRawResource(resId).bufferedReader().use { it.readText() }

            val adapter: JsonAdapter<AttractionResponse> =
                moshi.adapter(AttractionResponse::class.java)

            adapter.fromJson(textFromFile)?.attractions ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()

            emptyList()
        }
    }

}