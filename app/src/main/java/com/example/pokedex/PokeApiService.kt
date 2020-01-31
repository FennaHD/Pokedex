package com.example.pokedex

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface  PokeApiService{
    @GET("pokemon/?offset=0&limit=964")
    fun retrieveData() : Call<PokedexData>

    @GET("pokemon/{name}/")
    fun retrieveData(@Path(value="name")name: String) :Call<PokemonData>

    companion object Factory {
        fun create(): PokeApiService {
			return Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/").addConverterFactory(MoshiConverterFactory.create())
					.build().create(PokeApiService::class.java)
        }
    }

}