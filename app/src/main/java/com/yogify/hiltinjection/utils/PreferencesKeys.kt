package com.yogify.hiltinjection.utils

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val AGE = intPreferencesKey("USER_AGE")
    val NAME = stringPreferencesKey("USER_NAME")
    val APPLICATION_PREFERENCES = "user_prefs"
}