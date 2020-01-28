package com.example.pokedex

import android.content.Context
import android.content.Intent
import android.graphics.Color.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var pokemonList: List<PokemonSummary>?
        val pokedexService = PokeApiService.create()

        //dynamically create buttons with the name of each of the 964 pokemons
        pokedexService.retrieveData().enqueue(object : Callback<PokedexData> {
            override fun onResponse(call: Call<PokedexData>, response: Response<PokedexData>) {
                pokemonList = response.body()?.results
                pokemonList?.forEach {
                    constraintLayout.addView(createButton(this@MainActivity, it.name))
                }
            }
            override fun onFailure(call: Call<PokedexData>, t: Throwable) = t.printStackTrace()
        })

    }


    fun createButton(context: Context, name: String): Button {
        var button = Button(context)
        button.gravity = Gravity.CENTER_VERTICAL
        button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        button.setPadding(50, 0, 0, 0)
        button.text = name
        button.setBackgroundColor(TRANSPARENT)

        button.setOnClickListener { // load another Activity
            val intent = Intent(context, IndividualActivity::class.java)
            intent.putExtra("name", name)
            startActivity(intent)
        }

        return button
    }
}
