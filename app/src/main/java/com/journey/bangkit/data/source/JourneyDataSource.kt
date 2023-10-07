package com.journey.bangkit.data.source

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.WorkHistory
import com.journey.bangkit.R
import com.journey.bangkit.data.model.User
import com.journey.bangkit.data.model.Vacancy
import com.journey.bangkit.ui.navigation.NavigationItem
import com.journey.bangkit.ui.navigation.Screen

object JourneyDataSource {
    val navItemsJobSeeker = listOf(
        NavigationItem(
            title = R.string.vacancy,
            icon = Icons.Filled.Home,
            screen = Screen.HomeJobSeeker,
            contentDescription = R.string.vacancy
        ),
        NavigationItem(
            title = R.string.job_apply,
            icon = Icons.Filled.Work,
            screen = Screen.JobApply,
            contentDescription = R.string.job_apply
        ),
        NavigationItem(
            title = R.string.profile,
            icon = Icons.Filled.Person,
            screen = Screen.ProfileJobSeeker,
            contentDescription = R.string.profile
        ),
    )

    val navItemsJobProvider = listOf(
        NavigationItem(
            title = R.string.job_vacancy,
            icon = Icons.Filled.Home,
            screen = Screen.HomeJobProvider,
            contentDescription = R.string.job_vacancy
        ),
        NavigationItem(
            title = R.string.job_vacancy_user,
            icon = Icons.Filled.WorkHistory,
            screen = Screen.JobApplicant,
            contentDescription = R.string.job_vacancy_user
        ),
        NavigationItem(
            title = R.string.profile,
            icon = Icons.Filled.Person,
            screen = Screen.ProfileJobProvider,
            contentDescription = R.string.profile
        ),
    )

    val navigationCategory = listOf(
        R.string.all, R.string.most_popular, R.string.newest, R.string.recommended_for_you
    )

    val user = User(
        id = "sdfsjkdhfksjv",
        full_name = "Novandi Ramadhan",
        email = "novandi@journey.com",
        address = "Kaujon Kidul, Serang, Banten",
        profile_photo_url = "https://storage.googleapis.com/journey-bangkit/profile.png",
        gender = "Male",
        age = "21",
        phone_number = "085156066785",
        disability_name = "Blind",
        skill_one_name = "Web Development",
        skill_two_name = "Vector Illustration"
    )


    val menus = listOf(
        R.string.languages, R.string.about_app
    )

    val jobTypes = listOf(
        "Full-time", "Part-time", "Remote"
    )

    const val dummyDesc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam sit amet blandit mauris. Integer nisl ex, semper ut erat quis, molestie laoreet leo. In faucibus lobortis arcu a rutrum. Suspendisse porttitor posuere varius. Aenean elementum eu lorem a elementum. Etiam lorem tellus, ornare et ullamcorper et, dictum ac turpis. Mauris porta venenatis odio eu porttitor. Aliquam erat volutpat. Praesent elementum ipsum non justo accumsan, ac semper libero ultrices. Nulla facilisi. Integer non metus sem. Phasellus vulputate pellentesque diam et aliquet. Aliquam pellentesque nunc et metus rhoncus fringilla."
    val vacancy = Vacancy(
        id = "",
        placement_address = "Position",
        description = dummyDesc,
        created_at = "2023-06-01T07:59:04.938Z",
        updated_at = "2023-06-01T07:59:04.938Z",
        disability_name = "Disability Name",
        deadline_time = "2023-05-29T09:14:30.000Z",
        skill_one_name = "Skill one",
        skill_two_name = "Skill two",
        company_logo = "",
        job_type = 0,
        company_name = "Company",
        sector_name = "Advertising"
    )
}
