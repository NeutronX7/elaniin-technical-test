package com.elaniin.technical_test.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.elaniin.technical_test.R
import com.elaniin.technical_test.adapters.PokemonTeamAdapter
import com.elaniin.technical_test.databases.PokemonTeam
import com.elaniin.technical_test.databases.Team
import com.elaniin.technical_test.databinding.ActivityPokemonTeamsBinding
import com.elaniin.technical_test.utils.ClickListener
import com.elaniin.technical_test.viewmodels.PokemonTeamViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PokemonTeamsActivity : AppCompatActivity(), ClickListener {

    private val viewModel by viewModels<PokemonTeamViewModel>()
    private var binding: ActivityPokemonTeamsBinding? = null

    private var adapter = PokemonTeamAdapter(this, this)

    private lateinit var db: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    private lateinit var region: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_teams)

        binding = ActivityPokemonTeamsBinding.inflate(layoutInflater)
        val view = binding!!.root

        binding!!.teamsRecyclerView.adapter = adapter

        val name: String = intent.getStringExtra("pokemonName").toString()
        region = intent.getStringExtra("name").toString()

        setContentView(view)
        getData()
    }

    private fun getData(){
        db = FirebaseDatabase.getInstance()
        reference = db.getReference("User")
        val uniqueKey: String = reference.push().key.toString()

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val pokemonList: MutableList<Team> = ArrayList()
                for (postSnapshot in dataSnapshot.children) {
                    for (propertySnapshot in postSnapshot.children) {
                        val pokemonTeam = mutableListOf(PokemonTeam(null, null, null, null, null))

                        for (pokemonSnapshot in propertySnapshot.children) {

                            val name = pokemonSnapshot.child("pokemonName").value
                            val pokemonDescription = pokemonSnapshot.child("pokemonDescription").value
                            val pokemonNumber = pokemonSnapshot.child("pokemonNumber").value.toString()
                            val pokemonPhoto = pokemonSnapshot.child("email").value
                            val pokemonTypes = pokemonSnapshot.child("types").value
                            pokemonTeam.add(PokemonTeam(name.toString(),
                                pokemonNumber, pokemonTypes.toString(),
                                pokemonDescription.toString(), pokemonPhoto.toString()))
                        }
                        val team = Team(region, pokemonTeam)
                        pokemonList.add(team)
                    }
                    adapter.setData(0, pokemonList)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onClickItemListener(position: Int) {
        Log.d("Funciona", position.toString())
    }
}