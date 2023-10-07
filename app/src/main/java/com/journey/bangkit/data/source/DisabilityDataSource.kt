package com.journey.bangkit.data.source

import com.journey.bangkit.data.model.Disability

object DisabilityDataSource {
    val disabilities = listOf(
        Disability(id = 1, name = "All Types of Disabilities"),
        Disability(id = 2, name = "Visual Impairment"),
        Disability(id = 3, name = "Hearing Impairment"),
        Disability(id = 4, name = "Physical Disability"),
        Disability(id = 5, name = "Learning Disability"),
        Disability(id = 6, name = "Cognitive Disability"),
        Disability(id = 7, name = "Hearing Disability"),
        Disability(id = 8, name = "Neurodiversity (Autism Spectrum Disorder)"),
        Disability(id = 9, name = "Neurodiversity (Attention Deficit Hyperactivity Disorder)"),
        Disability(id = 10, name = "Mobility Impairment")
    )
}