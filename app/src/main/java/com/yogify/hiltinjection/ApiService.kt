package com.yogify.hiltinjection

import com.yogify.hiltinjection.data.ProductList
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(APICONSTANT.PRODUCTS)
    suspend fun getProduct(): Response<ProductList>
}