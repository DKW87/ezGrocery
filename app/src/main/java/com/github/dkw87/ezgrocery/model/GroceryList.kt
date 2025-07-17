package com.github.dkw87.ezgrocery.model

data class GroceryList(
    val id: Long,
    var title: String,
    var position: Int,
    var groceryItems: List<GroceryItem>
)