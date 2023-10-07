package com.journey.bangkit.ui.component.auth.register.jobseeker

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssistWalker
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person3
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journey.bangkit.R
import com.journey.bangkit.data.model.Disability
import com.journey.bangkit.data.model.UserJobSeeker
import com.journey.bangkit.ui.component.JDropdownMenu
import com.journey.bangkit.ui.component.JPasswordField
import com.journey.bangkit.ui.component.JTextField
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.JourneyTheme

@Composable
fun RegisterJobSeekerForm(
    modifier: Modifier = Modifier,
    disabilityData: List<Disability>,
    continueRegister: (UserJobSeeker) -> Unit
) {
    val gender = listOf(stringResource(id = R.string.man), stringResource(id = R.string.woman))
    var genderSelected by rememberSaveable { mutableIntStateOf(0) }
    var disabilitySelected by rememberSaveable { mutableIntStateOf(0) }
    val disabilities = disabilityData.map { it.name }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifier.padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JTextField(
            modifier = modifier,
            leadingIcon = Icons.Filled.Person,
            label = stringResource(id = R.string.fullname),
            keyboardType = KeyboardType.Text,
            placeholder = stringResource(id = R.string.fullname_placeholder),
            onKeyUp = { name = it },
            textValue = name
        )
        JTextField(
            modifier = modifier,
            leadingIcon = Icons.Filled.Email,
            label = stringResource(id = R.string.email),
            keyboardType = KeyboardType.Email,
            placeholder = stringResource(id = R.string.email_placeholder),
            onKeyUp = { email = it },
            textValue = email
        )
        JTextField(
            modifier = modifier,
            leadingIcon = Icons.Filled.Phone,
            label = stringResource(id = R.string.phone_number),
            keyboardType = KeyboardType.Phone,
            placeholder = stringResource(id = R.string.phonenumber_placeholder),
            onKeyUp = { phoneNumber = it },
            textValue = phoneNumber
        )
        JTextField(
            modifier = modifier,
            leadingIcon = Icons.Filled.Home,
            label = stringResource(id = R.string.address),
            keyboardType = KeyboardType.Text,
            placeholder = stringResource(id = R.string.address_placeholder),
            onKeyUp = { address = it },
            textValue = address
        )
        JTextField(
            modifier = modifier,
            leadingIcon = Icons.Filled.Person3,
            label = stringResource(id = R.string.age),
            placeholder = stringResource(id = R.string.age_placeholder),
            keyboardType = KeyboardType.Number,
            onKeyUp = { age = it },
            textValue = age
        )
        JDropdownMenu(
            icon = Icons.Filled.Man,
            label = stringResource(id = R.string.gender_placeholder),
            data = gender,
            itemSelected = genderSelected,
            setItemSelected = {
                genderSelected = it
            }
        )
        JDropdownMenu(
            icon = Icons.Filled.AssistWalker,
            label = stringResource(id = R.string.disability_placeholder),
            data = disabilities,
            itemSelected = disabilitySelected,
            setItemSelected = {
                disabilitySelected = it
            }
        )
        JPasswordField(
            onKeyUp = { password = it },
            textValue = password
        )
        Box(
            modifier = modifier.padding(start = 24.dp, end = 24.dp, bottom = 56.dp, top = 24.dp)
        ) {
            Button(
                onClick = {
                    continueRegister(
                        UserJobSeeker(
                            full_name = name, email = email, phone_number = phoneNumber,
                            address = address, age = age.toInt(),
                            id_disability = disabilitySelected,
                            password = password,
                            gender = gender[genderSelected]
                        )
                    )
                },
                modifier = modifier
                    .background(color = Blue40, shape = CircleShape)
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.btn_register),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterJobSeekerFormPreview() {
    JourneyTheme {
        RegisterJobSeekerForm(
            disabilityData = listOf(),
            continueRegister = {}
        )
    }
}