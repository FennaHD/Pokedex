package com.example.pokedex

import android.content.Context
import android.content.Intent
import android.graphics.Color.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity: AppCompatActivity() {


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		//dynamically create buttons with the name of each of the 964 pokemons
		PokeApiService.create().retrieveData().enqueue(object: Callback<PokedexData> {
			override fun onResponse(call: Call<PokedexData>, response: Response<PokedexData>) {
				response.body()?.results?.forEach {
					constraintLayout.addView(createButton(this@MainActivity, it.name))
				}
			}

			override fun onFailure(call: Call<PokedexData>, t: Throwable) = t.printStackTrace()
		})

	}

	fun createButton(context: Context, name: String): Button {
//		val button = MaterialButton(context)
		return Button(context).apply{
			gravity = Gravity.CENTER_VERTICAL
			layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
			setPadding(50,0,0,0)
			text = name
			setBackgroundColor(TRANSPARENT)
			setOnClickListener {
				startActivity(Intent(context, IndividualActivity::class.java).putExtra(IndividualActivity.EXTRA_NAME, name))
			}
		}
	}
}
