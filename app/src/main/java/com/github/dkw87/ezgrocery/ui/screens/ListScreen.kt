package com.github.dkw87.ezgrocery.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.dkw87.ezgrocery.R
import com.github.dkw87.ezgrocery.ui.components.AddItemDialogBox
import com.github.dkw87.ezgrocery.ui.components.GroceryItemCard
import com.github.dkw87.ezgrocery.ui.components.RemoveItemDialogBox
import com.github.dkw87.ezgrocery.viewmodel.AddItemViewModel
import com.github.dkw87.ezgrocery.viewmodel.ListViewModel
import com.github.dkw87.ezgrocery.viewmodel.RemoveItemViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListScreen(
    listViewModel: ListViewModel,
    addItemViewModel: AddItemViewModel,
    removeItemViewModel: RemoveItemViewModel
) {
    val items by listViewModel.items.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var showAddDialogBox by remember { mutableStateOf(false) }
    var showRemoveDialogBox by remember { mutableStateOf(false) }
    var itemToRemove by remember { mutableStateOf<String?>(null) }
    var isEditListScreen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ezGrocery") }
            )
        },
        floatingActionButton = {
            if (isEditListScreen) {
                FloatingActionButton(
                    onClick = { isEditListScreen = false },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Finish editing")
                }
            } else {
                FloatingActionButton(
                    onClick = { showAddDialogBox = true }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add item")
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        if (items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ezlogo),
                        contentDescription = "ezGrocery logo",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(256.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "No items yet",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "Tap + to add your first item",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val activeItems = items.filter { !it.isCompleted }
                val completedItems = items.filter { it.isCompleted }

                item {
                    if (activeItems.isNotEmpty()) {
                        Text(
                            text = "Active",
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                }

                items(activeItems) { item ->
                    GroceryItemCard(
                        item = item,
                        isEditable = isEditListScreen,
                        onToggle = listViewModel::toggleItem,
                        onRequestRemove = { itemID ->
                            itemToRemove = itemID
                            showRemoveDialogBox = true
                        },
                        modifier = Modifier.combinedClickable(
                            onClick = {
                                if (!isEditListScreen) {
                                    listViewModel.toggleItem(item)
                                }
                            },
                            onLongClick = {
                                isEditListScreen = true
                            }
                        )
                    )
                }

                item {
                    if (completedItems.isNotEmpty()) {
                        Text(
                            text = "Completed",
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                }

                items(completedItems) { item ->
                    GroceryItemCard(
                        item = item,
                        isEditable = isEditListScreen,
                        onToggle = listViewModel::toggleItem,
                        onRequestRemove = { itemID ->
                            itemToRemove = itemID
                            showRemoveDialogBox = true
                        },
                        modifier = Modifier.combinedClickable(
                            onClick = {
                                if (!isEditListScreen) {
                                    listViewModel.toggleItem(item)
                                }
                            },
                            onLongClick = {
                                isEditListScreen = true
                            }
                        )
                    )
                }
            }
        }
    }

    if (showAddDialogBox) {
        AddItemDialogBox(
            viewModel = addItemViewModel,
            onDismiss = {
                showAddDialogBox = false
            },
            onSuccess = {
                scope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = "Item successfully added",
                        duration = SnackbarDuration.Short,
                        withDismissAction = true
                    )
                }
            }
        )
    }

    if (showRemoveDialogBox && itemToRemove != null) {
        RemoveItemDialogBox(
            itemID = itemToRemove!!,
            removeItemViewModel = removeItemViewModel,
            onDismiss = {
                showRemoveDialogBox = false
                itemToRemove = null
            },
            onSuccess = {
                scope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = "Item successfully removed",
                        duration = SnackbarDuration.Short,
                        withDismissAction = true
                    )
                }
            }
        )
    }
}
