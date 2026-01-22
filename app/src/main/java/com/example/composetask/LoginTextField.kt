package com.example.composetask

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.composetask.ui.theme.focusedTextFieldText
import com.example.composetask.ui.theme.textFieldContainer
import com.example.composetask.ui.theme.unFocusedTextFieldText
import com.example.composetask.ui.theme.whiteVariant

@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    label: String,
    trailing: String
) {
    TextField(
        modifier = modifier,
        value = "",
        onValueChange = {},
        label = {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground)
        },
        colors = TextFieldDefaults.colors(
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.unFocusedTextFieldText,
            focusedPlaceholderColor = MaterialTheme.colorScheme.textFieldContainer,
            focusedContainerColor = MaterialTheme.colorScheme.focusedTextFieldText


        ),
        trailingIcon = {
            TextButton(onClick =  { }) {
                Text(text = trailing, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground))

            }
        }

    )
}