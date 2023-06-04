package com.elaniin.technical_test.models.pokedex_region

data class RegionPokedex(
    val descriptions: List<Description>,
    val id: Int,
    val is_main_series: Boolean,
    val name: String,
    val names: List<Name>,
    val pokemon_entries: List<PokemonEntry>,
    val region: Region,
    val version_groups: List<VersionGroup>
)