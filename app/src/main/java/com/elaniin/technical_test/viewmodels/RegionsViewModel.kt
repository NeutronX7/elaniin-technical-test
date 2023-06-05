package com.elaniin.technical_test.viewmodels

import android.content.Context
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegionsViewModel @Inject constructor(
    val prefs: Prefs,
    private val serviceUseCase: ServiceRepositoryUseCase,
    val repository: ServiceRepository
) : ViewModel() {

    private val itemsMutableLiveData = MutableLiveData<List<Result>>()
    val itemsLiveData: LiveData<List<Result>> = itemsMutableLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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

    fun signOutGoogle(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        googleSignInClient.signOut()
    }
}