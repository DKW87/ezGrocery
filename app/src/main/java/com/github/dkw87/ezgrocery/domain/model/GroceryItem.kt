package com.github.dkw87.ezgrocery.domain.model

data class GroceryItem(
    val id: Long,
    var name: String,
    var quantity: Int,
    var position: Int,
    var isCompleted: Boolean = false,
)