package com.elaniin.technical_test.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.elaniin.technical_test.R
import com.elaniin.technical_test.databases.PokemonTeam
import com.elaniin.technical_test.databases.Team
import com.elaniin.technical_test.databases.User
import com.elaniin.technical_test.databinding.ActivityPokemonInformationBinding
import com.elaniin.technical_test.viewmodels.PokemonInformationViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PokemonInformationActivity : AppCompatActivity() {

    private val viewModel by viewModels<PokemonInformationViewModel>()
    private var binding: ActivityPokemonInformationBinding? = null

    private lateinit var db: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_information)

        binding = ActivityPokemonInformationBinding.inflate(layoutInflater)
        val view = binding!!.root

        val name: String = intent.getStringExtra("pokemonName").toString()

        setContentView(view)

        viewModel.getPokemonInformation(name)
        viewModel.getPokemonDescription(name)

        viewModel.isLoading.observe(this) {
            if (it == false) {
                binding!!.txtName.text = viewModel.pokemonName.value
                binding!!.txtNumber.text = viewModel.pokemonNumber.value.toString()
                var types = ""
                for (i in viewModel.pokemonType.value!!.indices) {
                    types += ", " + viewModel.pokemonType.value?.get(i)?.type?.name.toString()
                }
                binding!!.txtType.text = types
                Glide.with(this)
                    .load("https://img.pokemondb.net/sprites/black-white/normal/${viewModel.pokemonName.value}.png")
                    .placeholder(R.drawable.pokemonempty)
                    .into(binding!!.imageView);
            }
        }

        viewModel.isLoadingPokemonInformation.observe(this) {
            if (it == false) {
                binding!!.txtDescription.text = viewModel.pokemonDescription.value.toString()
            }
        }

        binding!!.fabAdd.setOnClickListener {
            var types = ""
            for (i in viewModel.pokemonType.value!!.indices) {
                types += ", " + viewModel.pokemonType.value?.get(i)?.type?.name.toString()
            }
            val teamList: ArrayList<PokemonTeam> = arrayListOf(
                PokemonTeam(
                    viewModel.pokemonName.value.toString(),
                    viewModel.pokemonNumber.value!!,
                    listOf(types),
                    viewModel.pokemonDescription.value.toString(),
                    "https://img.pokemondb.net/sprites/black-white/normal/${viewModel.pokemonName.value}.png"
                )
            )
            val team = arrayListOf(Team(null, teamList))
            db = FirebaseDatabase.getInstance()
            reference = db.getReference("User/teams/pokemonList")
            val key = reference.child(viewModel.getName() ?: "-").push().key

            if(key != null){
                val user = User(viewModel.getEmail(), viewModel.getName(), team)
                val userMap = user.getMap()

                val addPokemon = hashMapOf<String, Any>(
                    "/${viewModel.getName()}/$key" to userMap
                )

                reference.updateChildren(addPokemon)
                //reference.child(viewModel.getName()).setValue(user)
            }
        }
    }
}