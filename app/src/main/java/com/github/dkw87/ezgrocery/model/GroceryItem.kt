package com.github.dkw87.ezgrocery.model

import java.time.LocalDateTime

data class GroceryItem(
    val id: Long,
    var title: String,
    var quantity: Int,
    var position: Int,
    var completed: Boolean,
)