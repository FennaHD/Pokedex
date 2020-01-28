package com.example.pokedex

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class IndividualActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.individual_poke_view)
        val name = intent.getStringExtra("name")

        //action bar for loading previous activity
        val actionbar = supportActionBar
        actionbar!!.title = "Back"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)


        //makes an API request using the name of the pokemon as a parameter
        //retrieves image, weight, height and types
        //processes types (returned in array) for easy use as string
        val pokeService = PokeApiService.create()
        pokeService.retrieveData(name).enqueue(object : Callback<PokemonData>{
            override fun onResponse(call: Call<PokemonData>, response: Response<PokemonData>) {
                val imageView = findViewById<ImageView>(R.id.pokemonPicture)
                Picasso.get().load(response.body()?.sprites?.front_default).resize(400, 400).into(imageView)
                findViewById<TextView>(R.id.name).text = getString(R.string.pokemon_name, response.body()?.name?.capitalize())
                findViewById<TextView>(R.id.weight).text = getString(R.string.pokemon_weight, response.body()?.weight)
                findViewById<TextView>(R.id.height).text = getString(R.string.pokemon_height, response.body()?.height)
                val sb = StringBuilder("Type: ")
                val responseTypes:List<TypeArray>? = response.body()?.types
                responseTypes?.forEach {
                    sb.append((it.type.name).capitalize()).append(", ")
                }
                sb.delete(sb.length - 2, sb.length - 1) // delete ", " at the end of string builder
                findViewById<TextView>(R.id.type).text = getString(R.string.pokemon_type, sb.toString())
            }
            override fun onFailure(call: Call<PokemonData>, t: Throwable) = t.printStackTrace()
        })
    }

    // used for action bar loading previous activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}