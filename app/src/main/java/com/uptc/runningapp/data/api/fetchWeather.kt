package com.uptc.runningapp.data.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

suspend fun fetchWeather(): String? {
    return withContext(Dispatchers.IO) {
        try {
            val url =
                "https://api.openweathermap.org/data/2.5/weather?q=Bogota&appid=c97566f1aafdd1d33ea80ef1a1ccfebd"
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().readText()
                val jsonObject = JSONObject(response)

                val tempMin = jsonObject.getJSONObject("main").getDouble("temp_min")

                "Temperatura mínima: $tempMin K"
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
