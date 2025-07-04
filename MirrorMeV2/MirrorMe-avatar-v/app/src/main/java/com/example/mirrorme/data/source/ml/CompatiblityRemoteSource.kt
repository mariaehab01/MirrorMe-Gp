package com.example.mirrorme.data.source.ml

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class CompatiblityRemoteSource{

    suspend fun getCompatibleItemIds(itemId: Int, topK: Int = 7): List<Int> = withContext(Dispatchers.IO) {
        val url = URL("https://compatibility-model-production.up.railway.app/api/v1/items/recommendations/$itemId")
        val connection = url.openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            if (connection.responseCode == 200) {
                val response = connection.inputStream.bufferedReader().readText()
                Log.d("CompatibleRemoteSource", "API Response: $response")

                val json = JSONObject(response)
                val array = json.getJSONArray("recommendations")

                val result = List(array.length()) { index -> array.getInt(index) }
                Log.d("CompatibleRemoteSource", "Parsed recommended IDs: $result")

                result
            } else {
                Log.e("CompatibleRemoteSource", "API Error: HTTP ${connection.responseCode}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("CompatibleRemoteSource", "Exception: ${e.message}")
            emptyList()
        } finally {
            connection.disconnect()
        }
    }
}
