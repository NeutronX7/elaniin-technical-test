package com.elaniin.technical_test.models.regions

data class Region(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)