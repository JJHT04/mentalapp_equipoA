package com.example.mentalapp_equipoa

import android.content.Context
import android.content.SharedPreferences
import com.example.mentalapp_equipoa.enums.Gender

private const val PREF_NAME = "MyAppPrefs"
private const val KEY_FIRST_RUN = "isFirstRun"
private const val KEY_PAG_NUM = "pagNum"
private const val KEY_USERNAME = "username"
private const val KEY_GENDER = "gender"
private const val KEY_AGE = "age"
private const val KEY_ID_USER_FIREBASE = "idUserFireBase"

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

    fun getGender (): Gender? = sharedPreferences.getString(KEY_GENDER, null)?.let {
        when(it) {
            Gender.MALE_GENDER_DEFAULT_STRING -> Gender.MALE
            Gender.FEMALE_GENDER_DEFAULT_STRING -> Gender.FEMALE
            else -> Gender.NON_BINARY
        }
    }

    fun setGender (value: Gender) {
        writePreference(KEY_GENDER, value.toString())
    }

    fun getAge (): Int = sharedPreferences.getInt(KEY_AGE,0)

    fun setAge (value: Int) = writePreference(KEY_AGE, value)

    fun getIDFireBase (): Long = sharedPreferences.getLong(KEY_ID_USER_FIREBASE, 0)
    fun setIDFireBase (value: Long) = writePreference(KEY_ID_USER_FIREBASE, value)
}