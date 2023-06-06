package com.elaniin.technical_test.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.elaniin.technical_test.R
import com.elaniin.technical_test.databases.PokemonTeam
import com.elaniin.technical_test.databases.room.PokemonTeamDao
import com.elaniin.technical_test.databases.room.PokemonTeamDatabase
import com.elaniin.technical_test.databases.room.entities.PokemonTeamEntity
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

    private lateinit var pokemonDao: PokemonTeamDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_information)

        val database = PokemonTeamDatabase.getDatabase(this)
        pokemonDao = database.pokemonTeamDao()

        binding = ActivityPokemonInformationBinding.inflate(layoutInflater)
        val view = binding!!.root

        val name: String = intent.getStringExtra("pokemonName").toString()
        val region: String = intent.getStringExtra("name").toString()

        setContentView(view)

        viewModel.getPokemonInformation(name)
        viewModel.getPokemonDescription(name)

        viewModel.isLoading.observe(this) {
            if (it == false) {
                binding!!.txtName.text = viewModel.pokemonName.value
                binding!!.txtNumber.text = "#${viewModel.pokemonNumber.value.toString()}"
                var types = ""
                for (i in viewModel.pokemonType.value!!.indices) {
                    if(i>0){
                        types += ", " + viewModel.pokemonType.value?.get(i)?.type?.name.toString()
                    } else {
                        types += viewModel.pokemonType.value?.get(i)?.type?.name.toString()
                    }
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
                    viewModel.pokemonNumber.value.toString(),
                    types,
                    viewModel.pokemonDescription.value.toString(),
                    "https://img.pokemondb.net/sprites/black-white/normal/${viewModel.pokemonName.value}.png"
                )
            )
            val pokemonTeamEntity = PokemonTeamEntity(
                name = viewModel.getName(),
                email = "https://img.pokemondb.net/sprites/black-white/normal/${viewModel.pokemonName.value}.png",
                teamName = "",
                pokemonName = viewModel.pokemonName.value.toString(),
            pokemonNumber = viewModel.pokemonNumber.value,
            pokemonDescription = viewModel.pokemonDescription.value.toString(),
            pokemonType = viewModel.pokemonType.value.toString(),
            generatedKey = region
            )

            if(pokemonDao.getAll()?.size!! <6){
                viewModel.insertPokemon(pokemonDao, pokemonTeamEntity)
            } else {
                Toast.makeText(this, getString(R.string.pokemon_not_added), Toast.LENGTH_SHORT).show()
            }

            /*val daoTeam = DaoTeam()
            daoTeam.getDao()
            val team = arrayListOf(Team(null, teamList))
            db = FirebaseDatabase.getInstance()
            reference = db.getReference("User/users")
            val uniqueKey: String = reference.push().key.toString()
            reference.child("User").child(uniqueKey).addChildEventListener(object: ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
            val user = User(viewModel.getEmail(), viewModel.getName(), team)
            daoTeam.add(user).addOnSuccessListener {
                Toast.makeText(this, getString(R.string.pokemon_added), Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, getString(R.string.pokemon_not_added), Toast.LENGTH_SHORT).show()
            }*/
        }
    }
}