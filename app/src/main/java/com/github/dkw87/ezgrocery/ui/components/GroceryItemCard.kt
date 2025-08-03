package com.github.dkw87.ezgrocery.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.dkw87.ezgrocery.domain.model.GroceryItem

@Composable
fun GroceryItemCard(
    item: GroceryItem,
    isEditable: (Boolean),
    onToggle: (GroceryItem) -> Unit,
    onRequestRemove: (String) -> Unit,
    modifier: Modifier = Modifier,
    dragHandleContent: (@Composable () -> Unit)? = null
) {
    Card(
        colors = setCardColors(item),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isEditable) {
                dragHandleContent?.invoke()
            }
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = item.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Quantity: ${item.quantity}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            if (isEditable) {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit this item")
                }
                IconButton(onClick = { onRequestRemove(item.id) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove this item")
                }
            }
        }
    }
}

@Composable
fun setCardColors(item: GroceryItem): CardColors {
    return if (!item.isCompleted) {
        CardDefaults.cardColors()
    } else {
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.outline
        )
    }
}
