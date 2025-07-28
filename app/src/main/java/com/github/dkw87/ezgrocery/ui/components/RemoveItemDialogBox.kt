package com.github.dkw87.ezgrocery.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.github.dkw87.ezgrocery.viewmodel.RemoveItemViewModel

@Composable
fun RemoveItemDialogBox(
    itemID: String,
    removeItemViewModel: RemoveItemViewModel,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Please confirm") },
        text = { Text("Are you sure you want to remove this item?") },
        confirmButton = {
          TextButton(onClick = {
              removeItemViewModel.removeItem(itemID)
              onDismiss()
          }) {
              Text("Remove")
          }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}