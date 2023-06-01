package com.yogify.hiltinjection.data


data class Product(
    var category: String,
    var description: String,
    var id: Int,
    var image: String,
    var price: Double,
    var rating: Rating,
    var title: String
)