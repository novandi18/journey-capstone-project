package com.journey.bangkit.data.source

import com.journey.bangkit.data.model.Vacancy
import com.journey.bangkit.data.model.VacancyResponse
import kotlin.random.Random

object VacancyDataSource {
    fun vacanciesDummy(): VacancyResponse {
        val data = VacancyResponse(
            status = "Success",
            vacancies = mutableListOf()
        )
        val dataVacancy: MutableList<Vacancy> = mutableListOf()

        for (i in 1..100) {
            val randomDisability =
                DisabilityDataSource.disabilities[Random.nextInt(0, DisabilityDataSource.disabilities.size - 1)]

            dataVacancy.add(
                Vacancy(
                    id = i.toString(),
                    placement_address = "Position $i",
                    description = JourneyDataSource.dummyDesc,
                    created_at = "2023-06-01T07:59:04.938Z",
                    updated_at = "2023-06-01T07:59:04.938Z",
                    disability_name = randomDisability.name,
                    deadline_time = "2023-05-29T09:14:30.000Z",
                    skill_one_name = "Skill one $i",
                    skill_two_name = "Skill two $i",
                    company_logo = "",
                    job_type = 0,
                    company_name = "Company",
                    sector_name = "Advertising"
                )
            )

            data.vacancies = dataVacancy
        }

        return data
    }
}