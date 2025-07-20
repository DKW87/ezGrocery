package com.github.dkw87.ezgrocery.domain.usecase

import com.github.dkw87.ezgrocery.data.repository.GroceryItemRepository
import com.github.dkw87.ezgrocery.domain.model.GroceryItem
import java.util.UUID

class AddItemUseCase(private val repository: GroceryItemRepository) {
    fun execute(name: String, quantity: Int, position: Int) {
        if (name != null) {
            if (name.isNotBlank()) {
                val item = GroceryItem(
                    id = UUID.randomUUID().toString(),
                    name = name.trim(),
                    quantity = quantity,
                    position = position,
                    isCompleted = false
                )
                repository.addItem(item)
            }
        }
    }
}