package com.elaniin.technical_test.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elaniin.technical_test.databases.room.PokemonTeamDao
import com.elaniin.technical_test.databases.room.entities.PokemonTeamEntity
import com.elaniin.technical_test.models.pokedex_region.PokemonEntry
import com.elaniin.technical_test.models.pokemon_specie.FlavorTextEntry
import com.elaniin.technical_test.models.pokemon_specie.Specie
import com.elaniin.technical_test.models.responses.Pokemon
import com.elaniin.technical_test.models.responses.Type
import com.elaniin.technical_test.repository.ServiceRepositoryUseCase
import com.elaniin.technical_test.utils.Prefs
import com.elaniin.technical_test.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonInformationViewModel @Inject constructor(
    val prefs: Prefs,
    private val serviceUseCase: ServiceRepositoryUseCase
) : ViewModel(){

    private val _pokemonName = MutableLiveData<String>()
    val pokemonName: MutableLiveData<String> = _pokemonName

    private val _pokemonNumber = MutableLiveData<Int>()
    val pokemonNumber: LiveData<Int> = _pokemonNumber

    private val _pokemonType = MutableLiveData<List<Type>>()
    val pokemonType: MutableLiveData<List<Type>> = _pokemonType

    private val _pokemonDescription = MutableLiveData<String>()
    val pokemonDescription: MutableLiveData<String> = _pokemonDescription

    private var pokemonInfoData: Resource<Pokemon>? = null
    private var pokemonSpecieInfoData: Resource<Specie>? = null

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingPokemonInformation = MutableLiveData<Boolean>()
    val isLoadingPokemonInformation: LiveData<Boolean> = _isLoadingPokemonInformation

    fun getPokemonInformation(name: String){
        viewModelScope.launch {
            if (pokemonInfoData == null) {

                val pokemonInfo =
                    withContext(Dispatchers.IO) { serviceUseCase.getPokemon(name) }

                pokemonInfoData = pokemonInfo
            }

            when (val dataFromServer =
                pokemonInfoData) {
                is Resource.Success -> {

                    _pokemonName.value = dataFromServer.data.name
                    _pokemonNumber.value = dataFromServer.data.id
                    _pokemonType.value = dataFromServer.data.types

                    _isLoading.value = false
                }

                is Resource.Error -> {
                    Log.d("ERROR", dataFromServer.errorMessage)
                    _isLoading.value = false
                }

                is Resource.Loading -> {
                    _isLoading.value = true
                }

                else -> {}
            }
        }
    }

    fun getPokemonDescription(name: String){
        viewModelScope.launch {
            if (pokemonSpecieInfoData == null) {

                val pokemonSpecieInfo =
                    withContext(Dispatchers.IO) { serviceUseCase.getPokemonSpecie(name) }

                pokemonSpecieInfoData = pokemonSpecieInfo
            }

            when (val dataFromServer =
                pokemonSpecieInfoData) {
                is Resource.Success -> {

                    for(i in dataFromServer.data.flavor_text_entries.indices){
                        if(dataFromServer.data.flavor_text_entries[i].language.name == "es"){
                            _pokemonDescription.value = dataFromServer.data.flavor_text_entries[i].flavor_text
                        }
                    }

                    _isLoadingPokemonInformation.value = false
                }

                is Resource.Error -> {
                    Log.d("ERROR", dataFromServer.errorMessage)
                    _isLoadingPokemonInformation.value = false
                }

                is Resource.Loading -> {
                    _isLoadingPokemonInformation.value = true
                }

                else -> {}
            }
        }
    }

    fun getName(): String {
        val name = prefs.accountName
        return name.ifEmpty {
            prefs.accountEmail
        }
    }

    fun getEmail(): String {
        val name = prefs.accountName
        return name.ifEmpty {
            prefs.accountEmail
        }
    }

    fun insertPokemon(pokemonDao: PokemonTeamDao, pokemonTeamEntity: PokemonTeamEntity) = viewModelScope.launch(Dispatchers.IO) {
        pokemonDao.insertTeam(pokemonTeamEntity)
    }

}