package com.journey.bangkit.data.source

import com.journey.bangkit.R
import com.journey.bangkit.data.model.OnBoarding

object OnBoardingDataSource {
    val data = listOf(
        OnBoarding(
            R.string.onboarding_title_1,
            R.string.onboarding_description_1,
            R.drawable.img_welcome_1
        ),
        OnBoarding(
            R.string.onboarding_title_2,
            R.string.onboarding_description_2,
            R.drawable.img_welcome_2
        ),
        OnBoarding(
            R.string.onboarding_title_3,
            R.string.onboarding_description_3,
            R.drawable.img_welcome_3
        )
    )
}