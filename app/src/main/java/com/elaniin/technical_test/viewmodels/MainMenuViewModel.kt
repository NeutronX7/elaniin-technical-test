package com.elaniin.technical_test.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elaniin.technical_test.models.regions.Region
import com.elaniin.technical_test.models.regions.Result
import com.elaniin.technical_test.repository.ServiceRepository
import com.elaniin.technical_test.repository.ServiceRepositoryUseCase
import com.elaniin.technical_test.utils.Prefs
import com.elaniin.technical_test.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    val prefs: Prefs,
    private val serviceUseCase: ServiceRepositoryUseCase,
    val repository: ServiceRepository
) : ViewModel() {

    private val itemsMutableLiveData = MutableLiveData<List<Result>>()
    val itemsLiveData: LiveData<List<Result>> = itemsMutableLiveData

    private var regionInfoData: Resource<Region>? = null

    fun getRegions() {
        viewModelScope.launch {
            if (regionInfoData == null) {

                val regionInfo =
                    withContext(Dispatchers.IO) { serviceUseCase.getRegions() }

                regionInfoData = regionInfo
            }

            when (val dataFromServer =
                regionInfoData) {
                is Resource.Success -> {
                    itemsMutableLiveData.value = dataFromServer.data.results.map {
                        Result(
                            it.name,
                            it.url
                        )
                    }
                    Log.d("ERROR", "POSITIVO")
                }

                is Resource.Error -> {
                    Log.d("ERROR", dataFromServer.errorMessage)
                }

                else -> {}
            }
        }
    }
}