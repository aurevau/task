package com.example.composetask.login

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    label: String,
    trailing: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false

) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        visualTransformation =
            if (isPassword) PasswordVisualTransformation()
            else VisualTransformation.None,

        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPassword)
                KeyboardType.Password
            else
                KeyboardType.Email
        ),
        label = {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground)
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,

            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,

            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,

            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,

            cursorColor = MaterialTheme.colorScheme.primary

        ),
        trailingIcon = {
            TextButton(onClick =  { }) {
                Text(text = trailing, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground))

            }
        }

    )
}