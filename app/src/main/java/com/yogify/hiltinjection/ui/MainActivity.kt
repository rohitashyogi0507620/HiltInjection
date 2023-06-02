package com.yogify.hiltinjection.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.yogify.hiltinjection.NetworkResult
import com.yogify.hiltinjection.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    val USER_AGE_KEY = intPreferencesKey("USER_AGE")
    val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
    val dataStore: DataStore<Preferences> by preferencesDataStore("user_prefs")

    var  count  =0

    lateinit var userNameFlow: Flow<String>
    lateinit var  userAgeFlow: Flow<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.productResponse.observe(this) {
            when (it) {
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }

                is NetworkResult.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    binding.textView.setText(it.errorMessage)
                    binding.progressBar.isVisible = false
                }

                is NetworkResult.Success -> {
                    Log.d("DATA", it.data.toString())
                    binding.progressBar.isVisible = false
                }
            }
        }

        binding.btnStoredata.setOnClickListener {
            count++
            GlobalScope.launch {
                storeUserInfo(10, "Rohitash Yogi ${count}")
                Log.d("DATA", "Data Stored")
            }
        }

        binding.btnFetcdata.setOnClickListener {

        }

        userAgeFlow= dataStore.data.map { preferences ->
            preferences[USER_AGE_KEY] ?: 0
        }

        userNameFlow = dataStore.data.map { preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }

        GlobalScope.launch {

            userNameFlow.collect {
                binding.textView.setText(it)

            }

        }


    }

    suspend fun storeUserInfo(age: Int, name: String) {
        dataStore.edit { preferences ->
            preferences[USER_AGE_KEY] = age
            preferences[USER_NAME_KEY] = name
        }
    }
}