package com.github.dkw87.ezgrocery.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.dkw87.ezgrocery.domain.usecase.AddItemUseCase

class AddItemViewModel(private val addItemUseCase: AddItemUseCase) : ViewModel() {

    var itemName by mutableStateOf("")
        private set

    var itemQuantity by mutableIntStateOf(1)
        private set

    fun updateItemName(name: String) {
        itemName = name
    }

    fun updateItemQuantity(quantity: Int) {
        itemQuantity = quantity
    }

}