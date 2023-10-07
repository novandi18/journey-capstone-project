package com.journey.bangkit.ui.component.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journey.bangkit.R
import com.journey.bangkit.ui.component.JPasswordField
import com.journey.bangkit.ui.component.JTextField
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.Blue80
import com.journey.bangkit.ui.theme.DarkGray40
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.ui.theme.Light

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    doLogin: (String, String) -> Unit,
    isLoading: Boolean = false
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JTextField(
            modifier = modifier,
            leadingIcon = Icons.Filled.Email,
            label = stringResource(id = R.string.email),
            keyboardType = KeyboardType.Email,
            placeholder = stringResource(id = R.string.email_placeholder),
            onKeyUp = { email = it },
            textValue = email
        )
        JPasswordField(
            onKeyUp = { password = it },
            textValue = password
        )
        Column(
            modifier = modifier.padding(top = 24.dp)
        ) {
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = {
                    doLogin(email, password)
                },
                enabled = (email.isNotEmpty() && password.isNotEmpty()) || isLoading,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Blue40,
                    contentColor = Light,
                    disabledContainerColor = Blue80,
                    disabledContentColor = Light
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AnimatedVisibility(visible = isLoading) {
                        CircularProgressIndicator(
                            modifier = modifier
                                .size(28.dp),
                            color = Light
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.btn_login),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginFormPreview() {
    JourneyTheme {
        LoginForm(doLogin = {_,_ -> }, isLoading = false)
    }
}