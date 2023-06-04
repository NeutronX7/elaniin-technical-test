package com.elaniin.technical_test.io.retrofit

import com.elaniin.technical_test.models.pokedex_region.RegionPokedex
import com.elaniin.technical_test.models.regions.Region
import com.elaniin.technical_test.models.selected_region.SelectedRegion
import com.elaniin.technical_test.utils.Constants
import com.elaniin.technical_test.utils.Resource
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {

    @GET("region")
    suspend fun getRegions(): Response<Region>?

    @GET("region/{name}")
    suspend fun getSelectedRegion(@Path("name") name: String) : Response<SelectedRegion>?

    @GET("pokedex/{name}")
    suspend fun getPokemonInformation(@Path("name") name: String) : Response<RegionPokedex>?

    companion object{
        fun create() : Service{

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit
                .Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
            return retrofit.create(Service::class.java)
        }
    }

}