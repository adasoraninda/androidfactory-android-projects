package com.codetron.senya.data

import com.squareup.moshi.Json

data class Attraction(
    val description: String = "",
    val facts: List<String> = listOf(),
    val id: String = "",
    @Json(name = "image_urls")
    val imageUrls: List<String?> = emptyList(),
    val location: Location = Location(),
    @Json(name = "months_to_visit")
    val monthsToVisit: String = "",
    val title: String = ""
)