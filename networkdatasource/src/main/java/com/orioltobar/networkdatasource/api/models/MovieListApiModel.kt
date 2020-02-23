package com.orioltobar.networkdatasource.api.models

import com.google.gson.annotations.SerializedName

data class MovieListApiModel(
    @SerializedName("items")
    val movieList: List<MovieApiModel> = emptyList()
)