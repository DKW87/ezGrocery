package com.github.dkw87.ezgrocery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import com.github.dkw87.ezgrocery.di.AppContainer
import com.github.dkw87.ezgrocery.ui.screens.ListScreen
import com.github.dkw87.ezgrocery.ui.theme.EzGroceryTheme

class MainActivity : ComponentActivity() {

    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appContainer = AppContainer(this)

        enableEdgeToEdge()

        setContent {
            EzGroceryTheme {
                val listViewModel = remember { appContainer.provideListViewModel() }
                val addItemViewModel = remember { appContainer.provideAddItemViewModel() }

                ListScreen(
                    listViewModel = listViewModel,
                    addItemViewModel = addItemViewModel
                )
            }
        }
    }
}