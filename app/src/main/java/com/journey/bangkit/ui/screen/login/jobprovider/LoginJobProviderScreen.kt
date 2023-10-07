package com.journey.bangkit.ui.screen.login.jobprovider

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.journey.bangkit.R
import com.journey.bangkit.ui.component.auth.AuthHeader
import com.journey.bangkit.ui.component.auth.AuthTitle
import com.journey.bangkit.ui.component.auth.login.LoginBottom
import com.journey.bangkit.ui.component.auth.login.LoginForm
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.viewmodel.LoginJobProviderViewModel
import com.journey.bangkit.ui.common.UiState

@Composable
fun LoginJobProviderScreen(
    backToStarted: () -> Unit,
    navigateToRegister: () -> Unit,
    viewModel: LoginJobProviderViewModel = hiltViewModel(),
    navigateToHome: () -> Unit
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    LoginJobProviderContent(
        backToStarted = backToStarted,
        navigateToRegister = navigateToRegister,
        doLogin = { email, password ->
            isLoading = true
            viewModel.loginCompany(email, password)
        },
        loading = isLoading
    )

    val loginResponse by viewModel.response.collectAsState(initial = UiState.Loading)
    LaunchedEffect(loginResponse) {
        loginResponse.let { response ->
            when (response) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    isLoading = false
                    if (response.data.errorMessage != null) {
                        Toast.makeText(context, response.data.errorMessage, Toast.LENGTH_SHORT).show()
                    } else {
                        navigateToHome()
                    }
                }
                is UiState.Error -> {
                    isLoading = false
                    Toast.makeText(context, response.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun LoginJobProviderContent(
    modifier: Modifier = Modifier,
    backToStarted: () -> Unit,
    navigateToRegister: () -> Unit,
    doLogin: (String, String) -> Unit,
    loading: Boolean
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AuthHeader(
            back = backToStarted,
            title = stringResource(id = R.string.jobprovider)
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            AuthTitle(
                title = stringResource(id = R.string.login_title),
                description = stringResource(id = R.string.login_description)
            )
            LoginForm(
                doLogin = doLogin,
                isLoading = loading
            )
            LoginBottom(
                navigateToRegister = navigateToRegister
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginJobProviderPreview() {
    JourneyTheme {
        LoginJobProviderContent(
            backToStarted = {},
            navigateToRegister = {},
            doLogin = {_,_ ->},
            loading = false
        )
    }
}