package com.elaniin.technical_test.repository

import com.elaniin.technical_test.models.regions.Region
import com.elaniin.technical_test.utils.Resource

interface ServiceRepositoryUseCase {

    suspend fun getRegions(): Resource<Region>

}