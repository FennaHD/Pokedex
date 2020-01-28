package com.example.pokedex

data class PokedexData(
        val count: Int,
        val results: List<PokemonSummary>
)

data class PokemonSummary(
        val name: String,
        val url: String
)

data class PokemonData(
        val name:String,
        val height: Int,
        val weight:Int,
        val types: List<TypeArray>,
        val sprites: Sprites
)

data class TypeArray(
        val type: TypeElement
)

data class TypeElement(
        val name: String
)

data class Sprites (
        val front_default: String
)
