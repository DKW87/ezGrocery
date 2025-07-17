package com.github.dkw87.ezgrocery.model

data class GroceryItem(
    val id: Long,
    var title: String,
    var quantity: Int,
    var position: Int,
    var completed: Boolean,
)