package com.github.dkw87.ezgrocery.di

import android.content.Context
import com.github.dkw87.ezgrocery.data.localstorage.GroceryItemDataStorage
import com.github.dkw87.ezgrocery.data.repository.GroceryItemRepository
import com.github.dkw87.ezgrocery.domain.usecase.AddItemUseCase
import com.github.dkw87.ezgrocery.domain.usecase.EditItemUseCase
import com.github.dkw87.ezgrocery.domain.usecase.RemoveItemUseCase
import com.github.dkw87.ezgrocery.domain.usecase.ToggleItemUseCase
import com.github.dkw87.ezgrocery.viewmodel.AddItemViewModel
import com.github.dkw87.ezgrocery.viewmodel.EditItemViewModel
import com.github.dkw87.ezgrocery.viewmodel.ListViewModel
import com.github.dkw87.ezgrocery.viewmodel.RemoveItemViewModel

class AppContainer(context: Context) {

    // local storage
    private val itemStorage = GroceryItemDataStorage(context)

    // repositories
    private val itemRepository = GroceryItemRepository(itemStorage)

    // use cases
    private val addItemUseCase = AddItemUseCase(itemRepository)
    private val toggleItemUseCase = ToggleItemUseCase(itemRepository)
    private val removeItemUseCase = RemoveItemUseCase(itemRepository)
    private val editItemUseCase = EditItemUseCase(itemRepository)

    // ViewModels
    fun provideAddItemViewModel(): AddItemViewModel {
        return AddItemViewModel(addItemUseCase)
    }

    fun provideListViewModel(): ListViewModel {
        return ListViewModel(itemRepository, toggleItemUseCase)
    }

    fun provideRemoveItemViewModel(): RemoveItemViewModel {
        return RemoveItemViewModel(removeItemUseCase)
    }

    fun provideEditItemViewModel(): EditItemViewModel {
        return EditItemViewModel(editItemUseCase)
    }

}