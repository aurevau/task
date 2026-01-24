package com.example.composetask.ui.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composetask.ui.theme.AppTheme
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeDropdown(
    currentTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit
) {


    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded }
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = currentTheme.name.replace("_", " "),
            onValueChange = {},
            readOnly = true,
            label = {Text("Choose theme")},
            shape = RoundedCornerShape(14.dp),


            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,

                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,

                focusedIndicatorColor = MaterialTheme.colorScheme.outline,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,

                cursorColor = MaterialTheme.colorScheme.primary
            ),


            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded)}
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false}
        ) {
            AppTheme.values().forEach { theme ->
                DropdownMenuItem(
                    text = {Text(theme.name.replace("_", " "),
                        color = MaterialTheme.colorScheme.onSurface
                    )},
                    onClick = {onThemeSelected(theme)
                    expanded = false}
                )
            }
        }
    }
}