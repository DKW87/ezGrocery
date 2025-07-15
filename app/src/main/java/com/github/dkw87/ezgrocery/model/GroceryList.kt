package com.github.dkw87.ezgrocery.model

import java.time.LocalDateTime

data class GroceryList(
    val id: Long,
    val createdOn: LocalDateTime,
    var title: String,
    var position: Int,
    var lastModified: LocalDateTime,
    var groceryItems: List<GroceryItem>
)