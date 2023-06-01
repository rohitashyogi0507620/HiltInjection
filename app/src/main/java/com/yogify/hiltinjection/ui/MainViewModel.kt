package com.yogify.hiltinjection.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yogify.hiltinjection.NetworkResult
import com.yogify.hiltinjection.data.ProductList
import com.yogify.hiltinjection.data.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository):ViewModel() {


    private var _productResponse = MutableLiveData<NetworkResult<ProductList>>()
    val productResponse: LiveData<NetworkResult<ProductList>> = _productResponse

    init {
        fetchAllProduct()
    }

     fun fetchAllProduct() {
        GlobalScope.launch {
            mainRepository.getProductListMovies().collect {
                _productResponse.postValue(it)
            }
        }
    }
}