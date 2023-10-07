package com.journey.bangkit.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journey.bangkit.R
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.DarkGray80
import com.journey.bangkit.ui.theme.Green
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.ui.theme.Light
import com.journey.bangkit.ui.theme.Red

@Composable
fun JCardApply(
    position: String,
    company: String,
    skill_one: String,
    skill_two: String,
    disability: String,
    appliedAt: String,
    status: String,
    onClick: (String) -> Unit,
    id: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Light),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = position,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = company,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
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
                    text = skill_one,
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
                    text = skill_two,
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
                text = disability
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
            Text(
                text = appliedAt
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 8.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier
                    .background(
                        color = when (status) {
                            "Pending" -> DarkGray80
                            "Accepted" -> Green
                            else -> Red
                        }, shape = CircleShape
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = status,
                color = Light
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JCardApplyPreview() {
    JourneyTheme {
        JCardApply(
            "Web Developer",
            "PT Juhdi Sakti Engineering",
            "PHP",
            "JavaScript",
            "Mobility Impairment",
            "16 Agustus 2023",
            "Accepted",
            onClick = {},
            id = ""
        )
    }
}