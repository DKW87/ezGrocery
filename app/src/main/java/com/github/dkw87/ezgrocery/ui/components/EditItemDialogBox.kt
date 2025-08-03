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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.github.dkw87.ezgrocery.viewmodel.EditItemViewModel
import kotlinx.coroutines.delay

@Composable
fun EditItemDialogBox(
    editItemViewModel: EditItemViewModel,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    AlertDialog(
        title = { Text("Edit item") },
        text = {
            Column {
                OutlinedTextField(
                    label = { Text("Item name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = editItemViewModel.itemName,
                    onValueChange = editItemViewModel::updateName
                )


                Spacer(modifier = Modifier.height(8.dp))


                OutlinedTextField(
                    label = { Text("Item quantity") },
                    modifier = Modifier.fillMaxWidth(),
                    value = editItemViewModel.itemQuantity.toString(),
                    onValueChange = { editItemViewModel.updateQuantity(it.toIntOrNull() ?: 1) }
                )
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    editItemViewModel.editItem()
                    onDismiss()
                    onSuccess()
                },
                enabled = editItemViewModel.itemName.isNotBlank()
            ) {
                Text("Edit")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )

    LaunchedEffect(Unit) {
        delay(50)
        focusRequester.requestFocus()
    }

}