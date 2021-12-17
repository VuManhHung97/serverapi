package com.vmh.kubetool.data.source.local

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.vmh.kubetool.data.models.AccessToken
import com.vmh.kubetool.utils.Constants
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class SharedPrefsApi @Inject constructor(context: Application, private val gson: Gson) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.PHOTO_PREFERENCES_STRING, Context.MODE_PRIVATE)

    operator fun <T> get(key: String, clazz: Class<T>): T? {
        when (clazz) {
            String::class.java -> {
                return sharedPreferences.getString(key, Constants.DEFAULT_STRING_VALUE) as T?
            }
            Boolean::class.java -> {
                return java.lang.Boolean.valueOf(sharedPreferences.getBoolean(key, false)) as T?
            }
            Float::class.java -> {
                return java.lang.Float.valueOf(sharedPreferences.getFloat(key, Constants.DEFAULT_NUMBER_VALUE.toFloat())) as T?
            }
            Int::class.java -> {
                return Integer.valueOf(sharedPreferences.getInt(key, Constants.DEFAULT_NUMBER_VALUE)) as T?
            }
            Long::class.java -> {
                return java.lang.Long.valueOf(sharedPreferences.getLong(key, Constants.DEFAULT_NUMBER_VALUE.toLong())) as T?
            }
            AccessToken::class.java -> {
                return gson.fromJson(sharedPreferences.getString(key, null), clazz) as T?
            }
            else -> return null
        }
    }

    fun <T> put(key: String, data: T) {
        val editor = sharedPreferences.edit()
        when (data) {
            is String -> {
                editor.putString(key, data as String)
            }
            is Boolean -> {
                editor.putBoolean(key, data as Boolean)
            }
            is Float -> {
                editor.putFloat(key, data as Float)
            }
            is Int -> {
                editor.putInt(key, data as Int)
            }
            is Long -> {
                editor.putLong(key, data as Long)
            }
            is AccessToken -> {
                editor.putString(key, gson.toJson(data))
            }
        }
        editor.apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
