package com.yogify.hiltinjection.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.yogify.hiltinjection.NetworkResult
import com.yogify.hiltinjection.R
import com.yogify.hiltinjection.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)

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
                    binding.textView.setText(it.data.toString())
                    Log.d("DATA",it.data.toString())
                    binding.progressBar.isVisible = false
                }
            }
        }
    }
}