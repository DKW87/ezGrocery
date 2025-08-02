package com.github.dkw87.ezgrocery.domain.usecase

import com.github.dkw87.ezgrocery.data.repository.GroceryItemRepository
import com.github.dkw87.ezgrocery.domain.model.GroceryItem
import java.util.UUID

class AddItemUseCase(private val repository: GroceryItemRepository) {
    fun execute(name: String, quantity: Int) {
        if (name.isNotBlank()) {
            val item = GroceryItem(
                id = UUID.randomUUID().toString(),
                name = name.trim(),
                quantity = quantity,
                isCompleted = false
            )

            repository.addItem(item)
        }
    }
}