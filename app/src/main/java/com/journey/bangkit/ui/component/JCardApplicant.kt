package com.journey.bangkit.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.journey.bangkit.R
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.DarkGray80
import com.journey.bangkit.ui.theme.Green
import com.journey.bangkit.ui.theme.Green80
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.ui.theme.Light
import com.journey.bangkit.ui.theme.Red
import com.journey.bangkit.ui.theme.Red80

@Composable
fun JCardApplicant(
    onAccept: (Boolean) -> Unit,
    name: String,
    email: String,
    skillOne: String,
    skillTwo: String,
    disabilityName: String,
    appliedAt: String,
    photo: String,
    isLoading: Boolean = false
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Light),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                model = photo,
                contentDescription = name
            )
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = email,
                    fontSize = 14.sp,
                    color = DarkGray80
                )
            }
        }
        Column(
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = stringResource(id = R.string.skill_applicant),
                fontSize = 12.sp,
                color = DarkGray80
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .background(color = Blue40, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 2.dp, horizontal = 8.dp),
                    text = skillOne,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Light,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .background(color = Blue40, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 2.dp, horizontal = 8.dp),
                    text = skillTwo,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Light,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = stringResource(id = R.string.disability),
                fontSize = 12.sp,
                color = DarkGray80
            )
            Text(
                text = disabilityName
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = stringResource(id = R.string.applied_at),
                fontSize = 12.sp,
                color = DarkGray80
            )
            Text(text = appliedAt)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 8.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.End)
        ) {
            Button(
                onClick = { onAccept(false) },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Red,
                    contentColor = Light,
                    disabledContainerColor = Red80,
                    disabledContentColor = Light
                ),
                enabled = !isLoading
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AnimatedVisibility(visible = isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Light,
                            strokeWidth = 2.dp
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.reject),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Button(
                onClick = { onAccept(true) },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Green,
                    contentColor = Light,
                    disabledContainerColor = Green80,
                    disabledContentColor = Light
                ),
                enabled = !isLoading
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AnimatedVisibility(visible = isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Light,
                            strokeWidth = 2.dp
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.accept),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JCardApplicantPreview() {
    JourneyTheme {
        JCardApplicant(
            onAccept = {},
            name = "Novandi Ramadhan",
            email = "novandiramadhan80@gmail.com",
            skillOne = "PHP",
            skillTwo = "Javascript",
            disabilityName = "Mobility Impairment",
            appliedAt = "16 Agustus 2023",
            photo = ""
        )
    }
}