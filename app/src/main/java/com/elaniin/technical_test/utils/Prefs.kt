package com.elaniin.technical_test.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(TICKET_APP, Context.MODE_PRIVATE)

    var accountName: String
        get() = preferences.getString(KEY_ACCOUNT_NAME, "")!!
        set(value) = preferences.edit().putString(KEY_ACCOUNT_NAME, value).apply()

    var accountEmail: String
        get() = preferences.getString(KEY_ACCOUNT_EMAIL, "")!!
        set(value) = preferences.edit().putString(KEY_ACCOUNT_EMAIL, value).apply()


    companion object {
        const val TICKET_APP = "TICKET_PREF_APP"
        const val KEY_ACCOUNT_NAME = "account_name"
        const val KEY_ACCOUNT_EMAIL = "account_email"
    }
}