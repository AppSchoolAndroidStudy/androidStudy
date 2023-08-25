package com.example.coroutine.repository

import android.content.Context
import android.util.Log
import com.example.coroutine.BuildConfig
import com.example.coroutine.MainActivity
import com.example.coroutine.databinding.ActivityMainBinding
import com.example.coroutine.model.DataClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class DataRepository {

    companion object {

        val clientId = BuildConfig.NAVER_CLIENT_ID
        val clientSecret = BuildConfig.NAVER_CLIENT_SECRET

        fun getDataList(context: Context): MutableList<DataClass> {

            val dataList = mutableListOf<DataClass>()

            // Dispatches : 코루틴의 스레드를 어떠한 형태로 가져갈지를 지정 (IO 스레드와 Main 스레드)
            // IO thread에서 동작하도록 지정
            CoroutineScope(Dispatchers.IO).launch {
                val word = URLEncoder.encode("Chopin", "UTF-8")
                val apiURL =
                    "https://openapi.naver.com/v1/search/book.json?query=$word&display=50&sort=sim"
                val url = URL(apiURL)
                val httpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.requestMethod = "GET"
                httpURLConnection.setRequestProperty("X-Naver-Client-Id", clientId)
                httpURLConnection.setRequestProperty("X-Naver-Client-Secret", clientSecret)

                val responseCode = httpURLConnection.responseCode

                Log.d("response", responseCode.toString())

                if (responseCode == 200) {
                    val reader = BufferedReader(InputStreamReader(httpURLConnection.inputStream))
                    val stringBuffer = StringBuffer()

                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        stringBuffer.append(line)
                    }
                    reader.close()
                    val data = stringBuffer.toString()
                    Log.d("data", data)

                    val root = JSONObject(data)

                    val itemArray = root.getJSONArray("items")

                    for (idx in 0 until itemArray.length()) {

                        val itemObject = itemArray.getJSONObject(idx)
                        val title = itemObject.getString("title")
                        val image = itemObject.getString("image")
                        val description = itemObject.getString("description")

                        val dataClass = DataClass(image, title, description)
                        dataList.add(dataClass)

                    }

                    Log.d("data", dataList.toString())

                    withContext(Dispatchers.Main) {
                        MainActivity.activityMainBinding.recyclerView.adapter?.notifyDataSetChanged()
                    }

                } else {
                    Log.e("error", "Error: $responseCode")
                }
            }

            return dataList
        }

    }
}