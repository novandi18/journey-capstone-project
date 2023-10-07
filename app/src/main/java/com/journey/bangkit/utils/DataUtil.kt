package com.journey.bangkit.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.journey.bangkit.data.model.City
import com.journey.bangkit.data.model.Province
import com.journey.bangkit.data.model.Sector
import com.journey.bangkit.data.model.Skill
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

fun getProvince(context: Context): List<Province> {
    val province = getJsonDataFromAsset(context, "region/province.json")
    val gson = Gson()
    val listProvince = object : TypeToken<List<Province>>() {}.type

    return gson.fromJson(province, listProvince)
}

fun getCityById(context: Context, id: Int = 11): List<City> {
    val province = getJsonDataFromAsset(context, "region/cities/$id.json")
    val gson = Gson()
    val listCities = object : TypeToken<List<City>>() {}.type

    return gson.fromJson(province, listCities)
}

fun getSector(context: Context): List<Sector> {
    val sector = getJsonDataFromAsset(context, "sector.json")
    val gson = Gson()
    val listSector = object : TypeToken<List<Sector>>() {}.type

    return gson.fromJson(sector, listSector)
}

fun getSkills(context: Context): List<Skill> {
    val skills = getJsonDataFromAsset(context, "skills.json")
    val gson = Gson()
    val listSkills = object : TypeToken<List<Skill>>() {}.type

    return gson.fromJson(skills, listSkills)
}

fun convertMillisToDate(milliseconds: Long): String {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return dateFormatter.format(milliseconds)
}

fun String.toDate(): String = this