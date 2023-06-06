package com.elaniin.technical_test.viewmodels

import androidx.lifecycle.ViewModel
import com.elaniin.technical_test.utils.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonTeamViewModel @Inject constructor(
    val prefs: Prefs
): ViewModel() {
}