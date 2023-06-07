package com.elaniin.technical_test.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elaniin.technical_test.R
import com.elaniin.technical_test.adapters.PokemonTeamAdapter
import com.elaniin.technical_test.adapters.PokemonTeamSelectedAdapter
import com.elaniin.technical_test.databases.PokemonTeam
import com.elaniin.technical_test.databases.Team
import com.elaniin.technical_test.databinding.ActivityPokemonTeamSelectedBinding
import com.elaniin.technical_test.databinding.ActivityPokemonTeamsBinding
import com.elaniin.technical_test.utils.ClickListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PokemonTeamSelectedActivity() : AppCompatActivity(), ClickListener {

    private var binding: ActivityPokemonTeamSelectedBinding? = null

    private var adapter = PokemonTeamSelectedAdapter(this, this)

    private lateinit var db: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    private lateinit var region: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_team_selected)

        binding = ActivityPokemonTeamSelectedBinding.inflate(layoutInflater)
        val view = binding!!.root

        binding!!.recyclerViewTeamSelected.adapter = adapter

        region = intent.getStringExtra("region").toString()

        setContentView(view)
        getData()

    }

    private fun getData() {
        db = FirebaseDatabase.getInstance()
        reference = db.getReference("User")
        val uniqueKey: String = reference.push().key.toString()

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val pokemonList: MutableList<Team> = ArrayList()
                for (postSnapshot in dataSnapshot.children) {
                    for (propertySnapshot in postSnapshot.children) {
                        val pokemonTeam = mutableListOf<PokemonTeam>()

                        for (pokemonSnapshot in propertySnapshot.children) {

                            val name = pokemonSnapshot.child("pokemonName").value
                            val pokemonDescription =
                                pokemonSnapshot.child("pokemonDescription").value
                            val pokemonNumber =
                                pokemonSnapshot.child("pokemonNumber").value.toString()
                            val pokemonPhoto = pokemonSnapshot.child("email").value
                            val pokemonTypes = pokemonSnapshot.child("types").value
                            pokemonTeam.add(
                                PokemonTeam(
                                    name.toString(),
                                    pokemonNumber, pokemonTypes.toString(),
                                    pokemonDescription.toString(), pokemonPhoto.toString()
                                )
                            )
                        }
                        val team = Team(region, pokemonTeam)
                        pokemonList.add(team)
                    }
                    adapter.setData(pokemonList)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onClickItemListener(position: Int) {

    }
}