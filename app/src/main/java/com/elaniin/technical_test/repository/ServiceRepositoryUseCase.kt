package com.elaniin.technical_test.repository

import com.elaniin.technical_test.models.pokedex_region.RegionPokedex
import com.elaniin.technical_test.models.regions.Region
import com.elaniin.technical_test.models.selected_region.SelectedRegion
import com.elaniin.technical_test.utils.Resource

interface ServiceRepositoryUseCase {

    suspend fun getRegions(): Resource<Region>

    suspend fun getRegionInformation(name: String): Resource<SelectedRegion>

    suspend fun getPokedex(name: String): Resource<RegionPokedex>

}