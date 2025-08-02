package com.github.dkw87.ezgrocery.domain.model

data class GroceryItem(
    val id: String,
    var name: String,
    var quantity: Int,
    var isCompleted: Boolean = false,
)