package com.orioltobar.data.datasources

import com.orioltobar.commons.Response
import com.orioltobar.domain.models.movie.MovieModel

interface DbDataSource {

    suspend fun getMovie(id: Long): Response<MovieModel>
}