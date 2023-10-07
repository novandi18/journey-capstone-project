package com.journey.bangkit.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.journey.bangkit.R
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.utils.convertMillisToDate
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JDatePicker(
    setDate: (String) -> Unit,
    value: String
) {
    val scope = rememberCoroutineScope()
    var openDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val confirmEnabled by remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = stringResource(id = R.string.deadline_time_placeholder))
        JDateField(
            openDialog = {
                openDialog = true
            },
            value = value
        )
    }

    if (openDialog) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        scope.launch {
                            setDate(convertMillisToDate(datePickerState.selectedDateMillis!!))
                        }
                    },
                    enabled = confirmEnabled
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JDatePickerPreview() {
    JourneyTheme {
        JDatePicker(
            setDate = {},
            value = ""
        )
    }
}