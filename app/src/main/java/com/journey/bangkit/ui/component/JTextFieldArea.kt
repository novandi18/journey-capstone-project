package com.journey.bangkit.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.Dark
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.ui.theme.Light

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JTextFieldArea(
    modifier: Modifier = Modifier,
    title: String,
    onKeyUp: (String) -> Unit,
    value: String
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = title)
        
        BasicTextField(
            value = value,
            onValueChange = { newText -> onKeyUp(newText) },
            modifier = modifier.fillMaxWidth(),
            singleLine = false,
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            minLines = 7,
            maxLines = 7
        ) {
            OutlinedTextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = { it() },
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
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

}

@Preview(showBackground = true)
@Composable
fun JTextFieldAreaPreview() {
    JourneyTheme {
        JTextFieldArea(title = "Deskripsi", onKeyUp = {}, value = "")
    }
}