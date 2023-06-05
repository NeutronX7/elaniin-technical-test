package com.elaniin.technical_test.databases

import com.google.firebase.database.Exclude

data class User(
    val email: String,
    val name: String,
    val teams: ArrayList<Team>?
){
    @Exclude
    fun getMap(): Map<String, Any?>{
        return mapOf(
            "teams" to teams
        )
    }
}
