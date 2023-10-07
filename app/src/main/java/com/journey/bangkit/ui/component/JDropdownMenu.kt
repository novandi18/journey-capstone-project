package com.journey.bangkit.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssistWalker
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.journey.bangkit.R
import com.journey.bangkit.data.model.Skill
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.JourneyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JDropdownMenu(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector,
    data: List<String>,
    itemSelected: Int = 0,
    setItemSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = if (data.isNotEmpty()) data[itemSelected] else stringResource(id = R.string.loading),
            onValueChange = {},
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor(),
            label = {
                Text(text = label)
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            leadingIcon = {
                Icon(imageVector = icon, contentDescription = label)
            },
            readOnly = true
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier
                .padding(0.dp)
                .fillMaxWidth()
        ) {
            if (data.isNotEmpty()) {
                data.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            setItemSelected(index)
                            expanded = false
                        },
                        modifier = modifier
                            .fillMaxWidth()
                            .background(color =
                                if (itemSelected == index) Blue40.copy(alpha = .2f) else Color.White
                            )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JDropdownMenuPreview() {
    JourneyTheme {
        JDropdownMenu(
            icon = Icons.Filled.AssistWalker,
            label = stringResource(id = R.string.disability_placeholder),
            data = listOf(),
            setItemSelected = {}
        )
    }
}