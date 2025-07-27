package com.github.dkw87.ezgrocery.viewmodel

import androidx.lifecycle.ViewModel
import com.github.dkw87.ezgrocery.domain.usecase.RemoveItemUseCase

class RemoveItemViewModel(
    private val removeItemUseCase: RemoveItemUseCase
) : ViewModel() {

    fun removeItem(id: String) {
        removeItemUseCase.execute(id)
    }

}