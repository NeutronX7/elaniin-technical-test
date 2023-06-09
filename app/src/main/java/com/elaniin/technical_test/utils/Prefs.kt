package com.elaniin.technical_test.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(POKEDEX_APP, Context.MODE_PRIVATE)

    var accountName: String
        get() = preferences.getString(KEY_ACCOUNT_NAME, "")!!
        set(value) = preferences.edit().putString(KEY_ACCOUNT_NAME, value).apply()

    var accountEmail: String
        get() = preferences.getString(KEY_ACCOUNT_EMAIL, "")!!
        set(value) = preferences.edit().putString(KEY_ACCOUNT_EMAIL, value).apply()


    companion object {
        const val POKEDEX_APP = "POKEDEX_APP_PREF"
        const val KEY_ACCOUNT_NAME = "account_name"
        const val KEY_ACCOUNT_EMAIL = "account_email"
    }
}