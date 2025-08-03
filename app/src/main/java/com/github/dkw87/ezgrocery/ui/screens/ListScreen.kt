package com.github.dkw87.ezgrocery.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DragIndicator
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.dkw87.ezgrocery.R
import com.github.dkw87.ezgrocery.domain.model.GroceryItem
import com.github.dkw87.ezgrocery.ui.components.AddItemDialogBox
import com.github.dkw87.ezgrocery.ui.components.EditItemDialogBox
import com.github.dkw87.ezgrocery.ui.components.GroceryItemCard
import com.github.dkw87.ezgrocery.ui.components.RemoveItemDialogBox
import com.github.dkw87.ezgrocery.viewmodel.AddItemViewModel
import com.github.dkw87.ezgrocery.viewmodel.EditItemViewModel
import com.github.dkw87.ezgrocery.viewmodel.ListViewModel
import com.github.dkw87.ezgrocery.viewmodel.RemoveItemViewModel
import kotlinx.coroutines.launch
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListScreen(
    listViewModel: ListViewModel,
    addItemViewModel: AddItemViewModel,
    removeItemViewModel: RemoveItemViewModel,
    editItemViewModel: EditItemViewModel
) {
    val items by listViewModel.items.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val hapticFeedback = LocalHapticFeedback.current

    var showAddDialogBox by remember { mutableStateOf(false) }
    var showRemoveDialogBox by remember { mutableStateOf(false) }
    var showEditDialogBox by remember { mutableStateOf(false) }
    var itemToRemove by remember { mutableStateOf<String?>(null) }
    var itemToEdit by remember { mutableStateOf<GroceryItem?>(null) }
    var isEditListScreen by remember { mutableStateOf(false) }
    var areActiveItemsHidden by remember { mutableStateOf(false) }
    var areCompletedItemsHidden by remember { mutableStateOf(false) }

    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        if (isEditListScreen) {
            // the text header (the 1 item) counts as 1 apparently,
            // so need to -1 to prevent out of bounds
            val actualToIndex = to.index - 1
            val actualFromIndex = from.index - 1

            val reorderedItems = items.toMutableList().apply {
                add(actualToIndex, removeAt(actualFromIndex))
            }

            listViewModel.reorderItems(reorderedItems)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ezGrocery") }
            )
        },
        floatingActionButton = {
            if (!isEditListScreen) {
                FloatingActionButton(
                    onClick = { showAddDialogBox = true }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add item")
                }
            }
        },
        bottomBar = {
            if (isEditListScreen) {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.height(
                        40.dp + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                    )
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = { isEditListScreen = false },
                        modifier = Modifier.padding(end = 25.dp)
                    ) {
                        Icon(Icons.Default.Done, contentDescription = "Finalize changes")
                    }
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
                verticalArrangement = Arrangement.spacedBy(4.dp),
                state = lazyListState
            ) {
                if (isEditListScreen) {

                    item {
                        Text(
                            text = "Edit, reorder or remove",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }

                    items(items, key = { it.id }) { item ->
                        ReorderableItem(reorderableLazyListState, key = item.id) { isDragging ->
                            val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)

                            GroceryItemCard(
                                item = item,
                                isEditable = isEditListScreen,
                                onToggle = listViewModel::toggleItem,
                                onRequestRemove = { itemID ->
                                    itemToRemove = itemID
                                    showRemoveDialogBox = true
                                },
                                onRequestEdit = { requestedItem ->
                                    itemToEdit = requestedItem
                                    showEditDialogBox = true
                                },
                                dragHandleContent = {
                                    Icon(
                                        Icons.Default.DragIndicator,
                                        contentDescription = "Drag to reorder",
                                        modifier = Modifier
                                            .padding(end = 4.dp)
                                            .size(40.dp)
                                            .draggableHandle(enabled = true)
                                    )
                                }
                            )

                        }
                    }

                } else {
                    val activeItems = items.filter { !it.isCompleted }
                    val completedItems = items.filter { it.isCompleted }

                    item {
                        if (activeItems.isNotEmpty()) {
                            if (areActiveItemsHidden) {
                                TextButton(onClick = {
                                    areActiveItemsHidden = false
                                }) {
                                    Text(
                                        text = "Active",
                                        style = MaterialTheme.typography.headlineSmall,
                                        modifier = Modifier.padding(horizontal = 6.dp)
                                    )
                                    Icon(Icons.Default.ExpandMore, contentDescription = null)
                                }
                            } else {
                                TextButton(onClick = {
                                    areActiveItemsHidden = true
                                }) {
                                    Text(
                                        text = "Active",
                                        style = MaterialTheme.typography.headlineSmall,
                                        modifier = Modifier.padding(horizontal = 6.dp)
                                    )
                                    Icon(Icons.Default.ExpandLess, contentDescription = null)
                                }
                            }
                        }
                    }

                    if (!areActiveItemsHidden) {
                        items(activeItems) { item ->
                            GroceryItemCard(
                                item = item,
                                isEditable = isEditListScreen,
                                onToggle = listViewModel::toggleItem,
                                onRequestRemove = { itemID ->
                                    itemToRemove = itemID
                                    showRemoveDialogBox = true
                                },
                                onRequestEdit = { requestedItem ->
                                    itemToEdit = requestedItem
                                    showEditDialogBox = true
                                },
                                modifier = Modifier.combinedClickable(
                                    onClick = {
                                        if (!isEditListScreen) {
                                            listViewModel.toggleItem(item)
                                        }
                                    },
                                    onLongClick = {
                                        isEditListScreen = true
                                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                    }
                                )
                            )
                        }
                    }

                    item {
                        if (completedItems.isNotEmpty()) {
                            if (areCompletedItemsHidden) {
                                TextButton(onClick = {
                                    areCompletedItemsHidden = false
                                }) {
                                    Text(
                                        text = "Completed",
                                        style = MaterialTheme.typography.headlineSmall,
                                        modifier = Modifier.padding(horizontal = 6.dp)
                                    )
                                    Icon(Icons.Default.ExpandMore, contentDescription = null)
                                }
                            } else {
                                TextButton(onClick = {
                                    areCompletedItemsHidden = true
                                }) {
                                    Text(
                                        text = "Completed",
                                        style = MaterialTheme.typography.headlineSmall,
                                        modifier = Modifier.padding(horizontal = 6.dp)
                                    )
                                    Icon(Icons.Default.ExpandLess, contentDescription = null)
                                }
                            }
                        }
                    }

                    if (!areCompletedItemsHidden) {
                        items(completedItems) { item ->
                            GroceryItemCard(
                                item = item,
                                isEditable = isEditListScreen,
                                onToggle = listViewModel::toggleItem,
                                onRequestRemove = { itemID ->
                                    itemToRemove = itemID
                                    showRemoveDialogBox = true
                                },
                                onRequestEdit = { requestedItem ->
                                    itemToEdit = requestedItem
                                    showEditDialogBox = true
                                },
                                modifier = Modifier.combinedClickable(
                                    onClick = {
                                        if (!isEditListScreen) {
                                            listViewModel.toggleItem(item)
                                        }
                                    },
                                    onLongClick = {
                                        isEditListScreen = true
                                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                    }
                                )
                            )
                        }
                    }
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
                    if (items.isEmpty() && isEditListScreen) isEditListScreen = false

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

    if (showEditDialogBox && itemToEdit != null) {
        LaunchedEffect(itemToEdit) {
            editItemViewModel.initializeWith(itemToEdit!!)
        }
        EditItemDialogBox(
            editItemViewModel = editItemViewModel,
            onDismiss = {
                showEditDialogBox = false
                itemToEdit = null
            },
            onSuccess = {
                scope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = "Item successfully edited",
                        duration = SnackbarDuration.Short,
                        withDismissAction = true
                    )
                }
            }
        )
    }
}
