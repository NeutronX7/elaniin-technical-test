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
import com.elaniin.technical_test.databases.Team
import com.elaniin.technical_test.databases.room.PokemonTeamDao
import com.elaniin.technical_test.databases.room.PokemonTeamDatabase
import com.elaniin.technical_test.databinding.ActivityPokemonBinding
import com.elaniin.technical_test.utils.ClickListener
import com.elaniin.technical_test.viewmodels.PokemonViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonActivity : AppCompatActivity(), ClickListener {

    private val viewModel by viewModels<PokemonViewModel>()
    private var binding: ActivityPokemonBinding? = null

    private lateinit var db: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    private lateinit var pokedex:String

    private val adapter = PokemonAdapter(this, this)

    private lateinit var pokemonDao: PokemonTeamDao
    private lateinit var database: PokemonTeamDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        binding = ActivityPokemonBinding.inflate(layoutInflater)
        val view = binding!!.root

        binding!!.pokemonRecyclerView.adapter = adapter
        setContentView(view)

        pokedex = intent.getStringExtra("pokedex").toString()
        getPokemonData()
        viewModel.getRegion(pokedex)
    }

    override fun onBackPressed() {
        database = PokemonTeamDatabase.getDatabase(this)
        pokemonDao = database.pokemonTeamDao()

        if(pokemonDao.getAll()?.size!! in 3..6){
            val editText = EditText(this)
            val dialog: android.app.AlertDialog? = android.app.AlertDialog.Builder(this)
                .setTitle(getString(R.string.create_team_name))
                .setMessage(pokemonDao.getAll()?.size.toString())
                .setView(editText)
                .setPositiveButton("OK", DialogInterface.OnClickListener { _, i ->
                    val editTextInput: String = editText.text.toString()
                    sendPokemonTeam(pokedex)
                    finish()
                })
                .setNegativeButton("Cancel", null)
                .create()
            dialog?.show()
        } else {
            val dialog: android.app.AlertDialog? = android.app.AlertDialog.Builder(this)
                .setTitle(getString(R.string.go_back))
                .setMessage(getString(R.string.not_enough_pokemon))
                .setPositiveButton("OK", DialogInterface.OnClickListener { _, i ->
                    database.clearAllTables()
                    finish()
                })
                .setNegativeButton("Cancel", null)
                .create()
            dialog?.show()
        }

    }

    fun sendPokemonTeam(pokemonTeamName: String){
        db = FirebaseDatabase.getInstance()
        reference = db.getReference("User/users")
        val uniqueKey: String = reference.push().key.toString()
        //val team = arrayListOf(Team(null, teamList))
        db = FirebaseDatabase.getInstance()
        reference = db.getReference("User/users")
        reference.push().setValue(pokemonDao.getAll())
        database.clearAllTables()

    }

    private fun getPokemonData() {
        viewModel.itemsPokemonLiveData.observe(this) {
            adapter.setData(it)
        }
    }

    override fun onClickItemListener(position: Int) {
        val intent = Intent(this, PokemonInformationActivity::class.java)
        intent.putExtra("pokemonName", viewModel.itemsPokemonLiveData.value?.get(position)?.pokemon_species?.name)
        intent.putExtra("name", pokedex)
        startActivity(intent)
        Log.d("ACTIVITY", ""+ viewModel.itemsPokemonLiveData.value?.get(position)?.pokemon_species?.name)
    }
}