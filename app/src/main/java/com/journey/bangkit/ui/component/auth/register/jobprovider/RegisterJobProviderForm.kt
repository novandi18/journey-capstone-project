package com.journey.bangkit.ui.component.auth.register.jobprovider

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MapsHomeWork
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.journey.bangkit.data.model.City
import com.journey.bangkit.data.model.Province
import com.journey.bangkit.data.model.Sector
import com.journey.bangkit.data.model.UserJobProvider
import com.journey.bangkit.ui.component.JDropdownMenu
import com.journey.bangkit.ui.component.JPasswordField
import com.journey.bangkit.ui.component.JTextField
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.Blue80
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.ui.theme.Light

@Composable
fun RegisterJobProviderForm(
    modifier: Modifier = Modifier,
    province: List<Province>,
    city: List<City>,
    sector: List<Sector>,
    onProvinceChange: (Int) -> Unit,
    doRegister: (UserJobProvider) -> Unit,
    isLoading: Boolean
) {
    val provinceData = province.map { it.nama }
    val cityData = city.map { it.nama }
    val sectorData = sector.map { it.name }

    var provinceSelected by rememberSaveable { mutableIntStateOf(0) }
    var citySelected by rememberSaveable { mutableIntStateOf(0) }
    var sectorSelected by rememberSaveable { mutableIntStateOf(0) }
    var companyName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var employees by remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JTextField(
            modifier = modifier,
            leadingIcon = Icons.Filled.Apartment,
            label = stringResource(id = R.string.company_name),
            keyboardType = KeyboardType.Text,
            placeholder = stringResource(id = R.string.companyname_placeholder),
            onKeyUp = { companyName = it },
            textValue = companyName
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
        JPasswordField(onKeyUp = { password = it }, textValue = password)
        JDropdownMenu(
            label = stringResource(id = R.string.sector_placeholder),
            icon = Icons.Filled.Factory,
            data = sectorData,
            itemSelected = sectorSelected,
            setItemSelected = {
                sectorSelected = it
            }
        )
        JTextField(
            modifier = modifier,
            leadingIcon = Icons.Filled.MapsHomeWork,
            label = stringResource(id = R.string.address),
            keyboardType = KeyboardType.Text,
            placeholder = stringResource(id = R.string.address_placeholder),
            onKeyUp = { address = it },
            textValue = address
        )
        JTextField(
            modifier = modifier,
            leadingIcon = Icons.Filled.Groups,
            label = stringResource(id = R.string.total_employee),
            keyboardType = KeyboardType.Text,
            placeholder = stringResource(id = R.string.totalemployee_placeholder),
            onKeyUp = { employees = it },
            textValue = employees
        )
        JDropdownMenu(
            label = stringResource(id = R.string.province_placeholder),
            icon = Icons.Filled.LocationOn,
            data = provinceData,
            itemSelected = provinceSelected,
            setItemSelected = {
                provinceSelected = it
                citySelected = 0
                onProvinceChange(it)
            }
        )
        JDropdownMenu(
            label = stringResource(id = R.string.city_placeholder),
            icon = Icons.Filled.LocationCity,
            data = cityData,
            itemSelected = citySelected,
            setItemSelected = {
                citySelected = it
            }
        )
        Box(
            modifier = modifier.padding(vertical = 56.dp, horizontal = 24.dp)
        ) {
            Button(
                onClick = {
                    doRegister(
                        UserJobProvider(
                            name = companyName,
                            address = address,
                            email = email,
                            password = password,
                            city = cityData[citySelected],
                            province = provinceData[provinceSelected],
                            employees = employees.toInt(),
                            id_sector = sectorSelected
                        )
                    )
                },
                modifier = modifier.fillMaxWidth().height(56.dp),
                enabled = email.isNotEmpty() && password.isNotEmpty() &&
                        companyName.isNotEmpty() && address.isNotEmpty() &&
                        employees.isNotEmpty() || isLoading,
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
                        text = stringResource(id = R.string.btn_register),
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
fun RegisterJobProviderFormPreview() {
    JourneyTheme {
        RegisterJobProviderForm(
            province = listOf(),
            city = listOf(),
            sector = listOf(),
            onProvinceChange = {},
            doRegister = {},
            isLoading = false
        )
    }
}