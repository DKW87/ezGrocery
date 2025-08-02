package com.github.dkw87.ezgrocery.data.repository

import com.github.dkw87.ezgrocery.data.localstorage.GroceryItemDataStorage
import com.github.dkw87.ezgrocery.domain.model.GroceryItem
import kotlinx.coroutines.flow.StateFlow

class GroceryItemRepository(private val dataStorage : GroceryItemDataStorage) {

    fun getAllGroceryItems(): StateFlow<List<GroceryItem>> = dataStorage.items

    fun addItem(item: GroceryItem) {
        dataStorage.addItem(item)
    }

    fun updateItem(item: GroceryItem) {
        dataStorage.updateItem(item)
    }

    fun removeItem(itemID: String) {
        dataStorage.removeItem(itemID)
    }

    fun toggleItem(item: GroceryItem) {
        val toggledItem = item.copy(isCompleted = !item.isCompleted)
        dataStorage.updateItem(toggledItem)
    }

    fun saveAllItems(items: List<GroceryItem>) {
        dataStorage.saveItems(items)
    }

}