package com.example.pokemonclasses.data.response

import com.squareup.moshi.Json

data class GetAllPokemonsResponse(
    @Json(name = "count") var count: Int? = null,
    @Json(name = "next") var next: String? = null,
    @Json(name = "previous") var previous: String? = null,
    @Json(name = "results") var results: List<Result> = listOf()
)

data class Result(
    @Json(name = "name") var name: String? = null,
    @Json(name = "url") var url: String? = null
)
