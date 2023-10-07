package com.journey.bangkit.ui.navigation

import android.os.Bundle

sealed class Screen(val route: String) {
    object OnBoarding : Screen("onboarding")
    object Started : Screen("started")
    object LoginJobSeeker : Screen("login_jobseeker")
    object LoginJobProvider : Screen("login_jobprovider")
    object RegisterJobSeeker : Screen("register_jobseeker")
    object RegisterJobProvider : Screen("register_jobprovider")
    object SkillJobSeeker : Screen("skill_jobseeker")
    object HomeJobSeeker : Screen("home_jobseeker")
    object HomeJobProvider : Screen("home_jobprovider")
    object ProfileJobSeeker : Screen("profile_jobseeker")
    object ProfileJobProvider : Screen("profile_jobprovider")
    object JobApply : Screen("job_apply")
    object JobApplicant : Screen("job_applicant")
    object AddVacancy : Screen("add_vacancy")
    object DetailVacancy : Screen("home_jobseeker/{vacancyId}") {
        fun createRoute(vacancyId: String) = "home_jobseeker/$vacancyId"
    }
    object ApplicantDetail : Screen("job_applicant/{vacancyId}") {
        fun createRoute(vacancyId: String) = "job_applicant/$vacancyId"
    }
}
