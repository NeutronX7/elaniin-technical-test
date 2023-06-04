package com.elaniin.technical_test.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.elaniin.technical_test.R
import com.elaniin.technical_test.databinding.ActivityPokemonBinding
import com.elaniin.technical_test.databinding.ActivityRegionsBinding
import com.elaniin.technical_test.viewmodels.PokemonViewModel
import com.elaniin.technical_test.viewmodels.RegionsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonActivity : AppCompatActivity() {

    private val viewModel by viewModels<PokemonViewModel>()
    private var binding: ActivityPokemonBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        binding = ActivityPokemonBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        val pokedex:String = intent.getStringExtra("pokedex").toString()
        getPokemonData(pokedex)
    }

    private fun getPokemonData(name: String) {
        viewModel.getRegion(name)
    }
}