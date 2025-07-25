package com.github.dkw87.ezgrocery.di

import android.content.Context
import com.github.dkw87.ezgrocery.data.localstorage.GroceryItemDataStorage
import com.github.dkw87.ezgrocery.data.repository.GroceryItemRepository
import com.github.dkw87.ezgrocery.domain.usecase.AddItemUseCase
import com.github.dkw87.ezgrocery.domain.usecase.ToggleItemUseCase
import com.github.dkw87.ezgrocery.viewmodel.AddItemViewModel

class AppContainer(context: Context) {

    // local storage
    private val itemStorage = GroceryItemDataStorage(context)

    // repositories
    private val itemRepository = GroceryItemRepository(itemStorage)

    // use cases
    private val addItemUseCase = AddItemUseCase(itemRepository)
    private val toggleItemUseCase = ToggleItemUseCase(itemRepository)

    // ViewModels
    fun provideAddItemUseCase(): AddItemViewModel {
        return AddItemViewModel(addItemUseCase)
    }

}