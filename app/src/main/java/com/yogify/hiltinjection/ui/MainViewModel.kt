package com.yogify.hiltinjection.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogify.hiltinjection.NetworkResult
import com.yogify.hiltinjection.data.MainRepository
import com.yogify.hiltinjection.data.Product
import com.yogify.hiltinjection.utils.PreferencesKeys.AGE
import com.yogify.hiltinjection.utils.PreferencesKeys.NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository):ViewModel() {


    private var _productResponse = MutableLiveData<NetworkResult<List<Product>>>()
    val productResponse: LiveData<NetworkResult<List<Product>>> = _productResponse

    init {
        fetchAllProduct()
    }

     fun fetchAllProduct() {
         viewModelScope.launch {
             mainRepository.getProductListMovies().collect {
                 _productResponse.postValue(it)
             }
         }

    }



}