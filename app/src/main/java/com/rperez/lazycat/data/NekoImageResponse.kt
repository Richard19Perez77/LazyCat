package com.rperez.lazycat.data

data class NekoImageResponse(
    var results: List<Results>
)

data class Results(
    var anime_name: String,
    var url: String
)