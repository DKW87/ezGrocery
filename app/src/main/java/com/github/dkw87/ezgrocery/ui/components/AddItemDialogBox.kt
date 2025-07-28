package com.github.dkw87.ezgrocery.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.dkw87.ezgrocery.viewmodel.AddItemViewModel

@Composable
fun AddItemDialogBox(
    viewModel: AddItemViewModel,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add item") },
        text = {
            Column {
                OutlinedTextField(
                    value = viewModel.itemName,
                    onValueChange = viewModel::updateItemName,
                    label = { Text("Item name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.itemQuantity.toString(),
                    onValueChange = { viewModel.updateItemQuantity(it.toIntOrNull() ?: 1) },
                    label = { Text("Item quantity") },
                    modifier = Modifier.fillMaxWidth()
                )

            }
        },
        confirmButton = {
            TextButton(onClick = {
                viewModel.addItem()
                onDismiss()
            },
                enabled = viewModel.itemName.isNotBlank()) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}