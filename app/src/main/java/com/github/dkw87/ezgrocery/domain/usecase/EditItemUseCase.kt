package com.github.dkw87.ezgrocery.domain.usecase

import com.github.dkw87.ezgrocery.data.repository.GroceryItemRepository
import com.github.dkw87.ezgrocery.domain.model.GroceryItem

class EditItemUseCase(private val itemRepository: GroceryItemRepository) {
    fun execute(edittedItem: GroceryItem) {
        itemRepository.updateItem(edittedItem)
    }
}