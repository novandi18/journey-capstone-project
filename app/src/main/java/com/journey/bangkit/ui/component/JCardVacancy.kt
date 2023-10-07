package com.journey.bangkit.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journey.bangkit.R
import com.journey.bangkit.data.source.JourneyDataSource
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.DarkGray80
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.ui.theme.Light
import com.journey.bangkit.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JCardVacancy(
    position: String,
    jobType: String,
    skill_one: String,
    skill_two: String,
    disability: String,
    description: String? = null,
    closedAt: String? = null,
    navigateToDetail: (String) -> Unit = {},
    id: String = ""
) {
    var showMore by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Light),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        onClick = {
            if (id.isEmpty()) showMore = !showMore else navigateToDetail(id)
        }
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
                text = jobType,
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
        Divider()
        if (description != null) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.description_placeholder),
                    fontSize = 12.sp,
                    color = DarkGray80
                )
                AnimatedVisibility(showMore) {
                    Text(
                        text = description,
                        fontSize = 14.sp
                    )
                }
            }
            Divider()
        }
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
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
        if (closedAt != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 8.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tutup pada",
                    fontSize = 10.sp,
                    color = DarkGray80
                )
                Text(
                    text = closedAt,
                    fontWeight = FontWeight.Medium,
                    color = Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JCardVacancyPreview() {
    JourneyTheme {
        JCardVacancy(
            "Web Developer",
            "Full time",
            "PHP",
            "JavaScript",
            "Mobility Impairment",
            JourneyDataSource.dummyDesc,
            "16 Maret 2023",
            {},
            ""
        )
    }
}