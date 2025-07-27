package com.github.dkw87.ezgrocery.viewmodel

import androidx.lifecycle.ViewModel
import com.github.dkw87.ezgrocery.data.repository.GroceryItemRepository
import com.github.dkw87.ezgrocery.domain.model.GroceryItem
import com.github.dkw87.ezgrocery.domain.usecase.ToggleItemUseCase

class ListViewModel(
    private val repository: GroceryItemRepository,
    private val toggleItemUseCase: ToggleItemUseCase
): ViewModel() {
    val items = repository.getAllGroceryItems()

    fun toggleItem(item: GroceryItem) {
        toggleItemUseCase.execute(item)
    }

    fun removeItem(id: String) {
        repository.removeItem(id)
    }
}