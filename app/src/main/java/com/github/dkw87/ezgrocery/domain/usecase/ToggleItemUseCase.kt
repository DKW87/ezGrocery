package com.github.dkw87.ezgrocery.domain.usecase

import com.github.dkw87.ezgrocery.data.repository.GroceryItemRepository
import com.github.dkw87.ezgrocery.domain.model.GroceryItem

class ToggleItemUseCase(private val repository: GroceryItemRepository) {
    fun execute(item: GroceryItem) {
        repository.toggleItem(item)
    }
}