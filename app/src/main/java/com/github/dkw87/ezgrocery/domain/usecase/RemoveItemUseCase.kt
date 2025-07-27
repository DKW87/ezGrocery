package com.github.dkw87.ezgrocery.domain.usecase

import com.github.dkw87.ezgrocery.data.repository.GroceryItemRepository

class RemoveItemUseCase(private val repository: GroceryItemRepository) {

    fun execute(id: String) {
        repository.removeItem(id)
    }

}