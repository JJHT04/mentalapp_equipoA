package com.example.mentalapp_equipoa

import android.content.Context
import android.content.SharedPreferences

private const val PREF_NAME = "MyAppPrefs"
private const val KEY_FIRST_RUN = "isFirstRun"
private const val KEY_PAG_NUM = "pagNum"
private const val KEY_USERNAME = "username"
private const val KEY_GENDER = "gender"
private const val KEY_AGE = "age"
private const val KEY_PREVIOUS_RESULTS = "previousResults"
private const val KEY_PREVIOUS_ADVICES = "previousAdvices"

class PreferencesUtil(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    private fun writePreference (key: String, value: Any) {
        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Double -> editor.putFloat(key, value.toFloat())
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            is Set<*> -> @Suppress("UNCHECKED_CAST") editor.putStringSet(key, value as Set<String>)

            else -> throw IllegalArgumentException("Error: Class: ${value.javaClass} not supported")
        }

        editor.apply()
    }

    fun isFirstRun (): Boolean = sharedPreferences.getBoolean(KEY_FIRST_RUN, true)
    fun setFirstRun (value: Boolean) {
        writePreference(KEY_FIRST_RUN, value)
    }

    fun getNumPage (): Int = sharedPreferences.getInt(KEY_PAG_NUM, 0)
    fun setNumPage (value: Int) {
        writePreference(KEY_PAG_NUM, value)
    }

    fun getUsername (): String? = sharedPreferences.getString(KEY_USERNAME, null)

    fun setUsername (value: String) {
        writePreference(KEY_USERNAME, value)
    }

    fun getGender (): String? = sharedPreferences.getString(KEY_GENDER, null)

    fun setGender (value: String) {
        writePreference(KEY_GENDER, value)
    }

    fun getAge (): Int = sharedPreferences.getInt(KEY_AGE,0)

    fun setAge (value: Int) {
        writePreference(KEY_AGE, value)
    }

    fun getPreviousResults (): Set<String>? = sharedPreferences.getStringSet(KEY_PREVIOUS_RESULTS, emptySet<String>())

    fun setPreviousResults (value: Set<String>) = writePreference(KEY_PREVIOUS_RESULTS, value)

    fun getPreviousAdvices (): Set<String>? = sharedPreferences.getStringSet(KEY_PREVIOUS_ADVICES, emptySet<String>())

    fun setPreviousAdvices (value: Set<String>) = writePreference(KEY_PREVIOUS_ADVICES, value)

}