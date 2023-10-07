package com.journey.bangkit.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.journey.bangkit.R
import com.journey.bangkit.data.source.JourneyDataSource
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.Dark
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.ui.theme.Light
import com.journey.bangkit.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JCard(
    title: String,
    imageUrl: String,
    jobType: String,
    disabilityType: String,
    skillOne: String,
    skillTwo: String,
    deadline: String,
    description: String,
    setClick: (String) -> Unit,
    id: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        onClick = {
            setClick(id)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.size(50.dp),
                    model = imageUrl,
                    contentDescription = title
                )
                Column(
                    modifier = Modifier.align(alignment = Alignment.CenterVertically),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier,
                            text = jobType,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                        Icon(
                            modifier = Modifier.size(4.dp),
                            imageVector = Icons.Filled.Circle,
                            contentDescription = null,
                        )
                        Text(
                            modifier = Modifier,
                            text = disabilityType,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(color = Blue40, shape = CircleShape)
                            .padding(vertical = 2.dp, horizontal = 10.dp)
                    ) {
                        Text(
                            text = skillOne,
                            fontSize = 12.sp,
                            color = Light,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(color = Blue40, shape = CircleShape)
                            .padding(vertical = 2.dp, horizontal = 10.dp)
                    ) {
                        Text(
                            text = skillTwo,
                            fontSize = 12.sp,
                            color = Light,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(space = 4.dp, alignment = Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.deadline_close),
                    color = Dark.copy(alpha = .8f),
                    fontSize = 10.sp
                )
                Text(
                    text = deadline,
                    color = Red,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JCardPreview() {
    JourneyTheme {
        JCard(
            title = "Web Developer",
            imageUrl = "",
            jobType = JourneyDataSource.jobTypes[0],
            disabilityType = "Cognitive Disability",
            skillOne = "Javascript",
            skillTwo = "PHP",
            deadline = "15 Juni 2023",
            description = JourneyDataSource.dummyDesc,
            setClick = {},
            id = ""
        )
    }
}