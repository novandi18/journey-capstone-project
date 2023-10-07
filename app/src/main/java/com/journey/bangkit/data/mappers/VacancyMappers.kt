package com.journey.bangkit.data.mappers

import com.journey.bangkit.data.local.vacancy.VacancyEntity
import com.journey.bangkit.data.model.Vacancy

fun Vacancy.toVacancyEntity(): VacancyEntity {
    return VacancyEntity(
        id = id,
        placement_address = placement_address,
        description = description,
        created_at = created_at,
        updated_at = updated_at,
        disability_name = disability_name,
        deadline_time = deadline_time,
        skill_one_name = skill_one_name,
        skill_two_name = skill_two_name,
        company_logo = company_logo,
        job_type = job_type,
        sector_name = sector_name,
        company_name = company_name
    )
}

fun VacancyEntity.toVacancy(): Vacancy {
    return Vacancy(
        id = id,
        placement_address = placement_address,
        description = description,
        created_at = created_at,
        updated_at = updated_at,
        disability_name = disability_name,
        deadline_time = deadline_time,
        skill_one_name = skill_one_name,
        skill_two_name = skill_two_name,
        company_logo = company_logo,
        job_type = job_type,
        sector_name = sector_name,
        company_name = company_name
    )
}