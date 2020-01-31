package com.example.pokedex

import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.individual_poke_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IndividualActivity: AppCompatActivity() {

	companion object {
		const val EXTRA_NAME = "name"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.individual_poke_view)

		val name = intent.getStringExtra("name") ?: ""

		//action bar for loading previous activity
		supportActionBar?.title = name.capitalize()
		supportActionBar?.setDisplayHomeAsUpEnabled(true)


		//makes an API request using the name of the pokemon as a parameter
		//retrieves image, weight, height and types
		//processes types (returned in array) populating an array and using joinToString() method
		PokeApiService.create().retrieveData(name).enqueue(object: Callback<PokemonData> {
			override fun onResponse(call: Call<PokemonData>, response: Response<PokemonData>) {
				Picasso.get().load(response.body()?.sprites?.front_default).resize(400, 400).into(pokemon_picture)
				pokemon_name.text = getString(R.string.pokemon_name, response.body()?.name?.capitalize())
				weight.text = getString(R.string.pokemon_weight, response.body()?.weight)
				height.text = getString(R.string.pokemon_height, response.body()?.height)
				val array = ArrayList<String>()
				response.body()?.types?.forEach {
					array.add(it.type.name)
				}
				type.text = getString(R.string.pokemon_type, array.joinToString().capitalize())
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