package com.elaniin.technical_test.models.selected_region

data class SelectedRegion(
    val id: Int,
    val locations: List<Location>,
    val main_generation: MainGeneration,
    val name: String,
    val names: List<Name>,
    val pokedexes: List<Pokedexe>,
    val version_groups: List<VersionGroup>
)