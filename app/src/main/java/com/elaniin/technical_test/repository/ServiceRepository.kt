package com.elaniin.technical_test.repository

import com.elaniin.technical_test.io.retrofit.Service
import com.elaniin.technical_test.models.pokedex_region.RegionPokedex
import com.elaniin.technical_test.models.regions.Region
import com.elaniin.technical_test.models.selected_region.SelectedRegion
import com.elaniin.technical_test.utils.Resource
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

const val ERROR_MESSAGE = "Service error"

class ServiceRepository @Inject constructor(
    private val servicesApi: Service
) : ServiceRepositoryUseCase{

    override suspend fun getRegions(): Resource<Region> {

        val dataFromService = servicesApi.getRegions()
        return if (dataFromService?.body() != null) {
            Resource.Success(dataFromService.body()!!)
        } else {
            getGenericError(dataFromService?.errorBody())
        }

    }

    override suspend fun getRegionInformation(name: String): Resource<SelectedRegion> {

        val dataFromService = servicesApi.getSelectedRegion(name)
        return if (dataFromService?.body() != null) {
            Resource.Success(dataFromService.body()!!)
        } else {
            getGenericError(dataFromService?.errorBody())
        }

    }

    override suspend fun getPokedex(name: String): Resource<RegionPokedex> {
        val dataFromService = servicesApi.getPokemonInformation(name)
        return if (dataFromService?.body() != null) {
            Resource.Success(dataFromService.body()!!)
        } else {
            getGenericError(dataFromService?.errorBody())
        }
    }

    private fun getGenericError(responseBodyError: ResponseBody?): Resource.Error {
        return if (responseBodyError != null) {
            try {
                val data = JSONObject(responseBodyError.string())
                Resource.Error(data.optString("message", ERROR_MESSAGE))
            } catch (_: JSONException) {
                Resource.Error(ERROR_MESSAGE)
            }
        } else {
            Resource.Error(ERROR_MESSAGE)
        }
    }

}