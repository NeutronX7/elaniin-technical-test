package com.elaniin.technical_test.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elaniin.technical_test.models.pokedex_region.PokemonEntry
import com.elaniin.technical_test.models.pokedex_region.PokemonSpecies
import com.elaniin.technical_test.models.pokedex_region.RegionPokedex
import com.elaniin.technical_test.models.regions.Result
import com.elaniin.technical_test.models.selected_region.Pokedexe
import com.elaniin.technical_test.models.selected_region.SelectedRegion
import com.elaniin.technical_test.repository.ServiceRepositoryUseCase
import com.elaniin.technical_test.utils.Prefs
import com.elaniin.technical_test.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    val prefs: Prefs,
    private val serviceUseCase: ServiceRepositoryUseCase
) : ViewModel() {

    private val itemsMutableLiveData = MutableLiveData<List<Pokedexe>>()
    val itemsLiveData: LiveData<List<Pokedexe>> = itemsMutableLiveData

    private val itemsMutablePokemonLiveData = MutableLiveData<List<PokemonEntry>>()
    val itemsPokemonLiveData: LiveData<List<PokemonEntry>> = itemsMutablePokemonLiveData

    private var regionInfoData: Resource<SelectedRegion>? = null
    private var pokedexInfoData: Resource<RegionPokedex>? = null

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getRegion(name: String){
        viewModelScope.launch {
            if (regionInfoData == null) {

                val regionInfo =
                    withContext(Dispatchers.IO) { serviceUseCase.getRegionInformation(name) }

                regionInfoData = regionInfo
            }

            when (val dataFromServer =
                regionInfoData) {
                is Resource.Success -> {
                    itemsMutableLiveData.value = dataFromServer.data.pokedexes.map {
                        Pokedexe(
                            it.name,
                            it.url
                        )
                    }

                    getPokemonList(itemsMutableLiveData.value!![0].name)
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

    private fun getPokemonList(name: String){
        viewModelScope.launch {
            if (pokedexInfoData == null) {

                val pokedexData =
                    withContext(Dispatchers.IO) { serviceUseCase.getPokedex(name) }

                pokedexInfoData = pokedexData
            }

            when (val dataFromPokedex =
                pokedexInfoData) {
                is Resource.Success -> {
                    itemsMutablePokemonLiveData.value = dataFromPokedex.data.pokemon_entries.map {
                        PokemonEntry(
                            it.entry_number,
                            it.pokemon_species
                        )
                    }

                    _isLoading.value = false
                }

                is Resource.Error -> {

                    _isLoading.value = false
                }

                is Resource.Loading -> {
                    _isLoading.value = true
                }

                else -> {}
            }
        }
    }

}