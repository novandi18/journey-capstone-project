package com.journey.bangkit.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.journey.bangkit.R
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.Dark
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.ui.theme.Light

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JTextField(
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    placeholder: String,
    isSingleLine: Boolean = true,
    isReadOnly: Boolean = false,
    onKeyUp: (String) -> Unit,
    textValue: String
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = textValue,
        onValueChange = { newText -> onKeyUp(newText) },
        modifier = modifier.fillMaxWidth(),
        singleLine = isSingleLine,
        interactionSource = interactionSource,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        maxLines = if (isSingleLine) 1 else 7,
        minLines = if (isSingleLine) 1 else 7,
        textStyle = TextStyle(textAlign = TextAlign.Start),
        readOnly = isReadOnly
    ) {
        OutlinedTextFieldDefaults.DecorationBox(
            value = textValue,
            innerTextField = { it() },
            enabled = true,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            placeholder = {
                Text(
                    text = placeholder
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = label
                )
            },
            trailingIcon = {
                if (textValue.isNotBlank()) {
                    IconButton(onClick = { onKeyUp("") }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(id = R.string.clear)
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(),
            contentPadding = OutlinedTextFieldDefaults.contentPadding(),
            container = {
                OutlinedTextFieldDefaults.ContainerBox(
                    enabled = true,
                    isError = false,
                    interactionSource = interactionSource,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Blue40,
                        focusedContainerColor = Light,
                        focusedTextColor = Dark,
                        cursorColor = Dark,
                        unfocusedContainerColor = Light
                    )
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun JTextFieldPreview() {
    JourneyTheme {
        JTextField(
            leadingIcon = Icons.Filled.Email,
            label = "Email",
            keyboardType = KeyboardType.Email,
            placeholder = stringResource(id = R.string.email_placeholder),
            onKeyUp = {},
            textValue = ""
        )
    }
}