package com.github.dkw87.ezgrocery.domain.model

data class GroceryList(
    val id: String,
    var name: String,
    var position: Int,
    var items: List<GroceryItem> = emptyList()
)