package com.github.dkw87.ezgrocery.data.localstorage

import android.content.Context
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.dkw87.ezgrocery.domain.model.GroceryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocalDataStorage(private val context: Context) {
    private val preferences = context.getSharedPreferences("ezGrocery_preferences", Context.MODE_PRIVATE)
    private val objectMapper = ObjectMapper().apply {
        registerModule(KotlinModule.Builder().build())
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    private val _items = MutableStateFlow<List<GroceryItem>>(emptyList())
    val items: StateFlow<List<GroceryItem>> = _items.asStateFlow()

    init {
        loadItems()
    }

    private fun loadItems() {
        val itemsJSON = preferences.getString("grocery_items", "[]")
        val items = try {
            objectMapper.readValue(itemsJSON, object : TypeReference<List<GroceryItem>>() {})
        } catch (e: Exception) {
            emptyList()
        }
        _items.value = items
    }

    fun saveItems(items: List<GroceryItem>) {
        val itemsJSON = objectMapper.writeValueAsString(items)
        preferences.edit().putString("grocery_items", itemsJSON).apply()
        _items.value = items
    }

    fun addItem(item: GroceryItem) {
        val currentItems = _items.value.toMutableList()
        currentItems.add(item)
        saveItems(currentItems)
    }

    fun updateItem(item: GroceryItem) {
        val currentItems = _items.value.toMutableList()
        val index = currentItems.indexOfFirst { it.id == item.id }
        if (index != -1) {
            currentItems[index] = item
            saveItems(currentItems)
        }
    }

    fun removeItem(itemID: String) {
        val currentItems = _items.value.filterNot { it.id == itemID }
        saveItems(currentItems)
    }

}