package com.example.picvoter.domain.model

data class Photo(
    val alt: String,
    val avgColor: String,
    val height: Int,
    val id: Int,
    val liked: Boolean,
    val photographer: String,
    val photographerId: Int,
    val photographerUrl: String,
    val src: Src,
    val url: String,
    val width: Int
)
