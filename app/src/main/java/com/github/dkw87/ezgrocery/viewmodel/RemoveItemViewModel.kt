package com.github.dkw87.ezgrocery.viewmodel

import androidx.lifecycle.ViewModel
import com.github.dkw87.ezgrocery.data.repository.GroceryItemRepository

class RemoveItemViewModel(
    private val itemRepository: GroceryItemRepository
) : ViewModel() {

    fun removeItem(id: String) {
        itemRepository.removeItem(id)
    }

}