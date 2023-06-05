package com.elaniin.technical_test.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.viewModels
import com.elaniin.technical_test.R
import com.elaniin.technical_test.adapters.PokemonAdapter
import com.elaniin.technical_test.databinding.ActivityPokemonBinding
import com.elaniin.technical_test.utils.ClickListener
import com.elaniin.technical_test.viewmodels.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonActivity : AppCompatActivity(), ClickListener {

    private val viewModel by viewModels<PokemonViewModel>()
    private var binding: ActivityPokemonBinding? = null

    private val adapter = PokemonAdapter(this, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        binding = ActivityPokemonBinding.inflate(layoutInflater)
        val view = binding!!.root

        binding!!.pokemonRecyclerView.adapter = adapter
        setContentView(view)

        val pokedex:String = intent.getStringExtra("pokedex").toString()
        getPokemonData()
        viewModel.getRegion(pokedex)
    }

    override fun onBackPressed() {
        //super.onBackPressed()

        val editText: EditText = EditText(this)
        val dialog: android.app.AlertDialog? = android.app.AlertDialog.Builder(this)
            .setTitle(getString(R.string.create_team_name))
            .setView(editText)
            .setPositiveButton("OK", DialogInterface.OnClickListener { _, i ->
                val editTextInput: String = editText.text.toString()
            })
            .setNegativeButton("Cancel", null)
            .create()
        //finish()
        dialog?.show()

    }

    private fun getPokemonData() {
        viewModel.itemsPokemonLiveData.observe(this) {
            adapter.setData(it)
        }
    }

    override fun onClickItemListener(position: Int) {
        val intent = Intent(this, PokemonInformationActivity::class.java)
        intent.putExtra("pokemonName", viewModel.itemsPokemonLiveData.value?.get(position)?.pokemon_species?.name)
        startActivity(intent)
        Log.d("ACTIVITY", ""+ viewModel.itemsPokemonLiveData.value?.get(position)?.pokemon_species?.name)
    }
}