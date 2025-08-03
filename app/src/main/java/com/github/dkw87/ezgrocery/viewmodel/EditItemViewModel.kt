package com.github.dkw87.ezgrocery.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.github.dkw87.ezgrocery.domain.model.GroceryItem
import com.github.dkw87.ezgrocery.domain.usecase.EditItemUseCase
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

class EditItemViewModel(private val editItemUseCase: EditItemUseCase) {

    private var originalItem: GroceryItem? = null

    var itemName by mutableStateOf("")
        private set
    var itemQuantity by mutableIntStateOf(1)
        private set

    fun initializeWith(itemToSet: GroceryItem) {
        originalItem = itemToSet
        itemName = itemToSet.name
        itemQuantity = itemToSet.quantity
    }

    fun updateName(newName: String) { itemName = newName }
    fun updateQuantity(newQuantity: Int) { itemQuantity = newQuantity }

    fun editItem() {
        originalItem?.let { original ->
            val updatedItem = original.copy(
                name = itemName,
                quantity = itemQuantity
            )
            editItemUseCase.execute(updatedItem)
        }
        itemName = ""
        itemQuantity = 1
    }

}