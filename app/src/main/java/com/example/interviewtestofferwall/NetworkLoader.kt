package com.example.interviewtestofferwall

import org.json.JSONException
import org.json.JSONObject
import java.net.URL

class NetworkLoader {
    fun getIdList(): ArrayList<String>? {
        var idsList : ArrayList<String>? = null
        val thread = Thread {
            try {
                idsList = parseJsonToListIds(URL("https://demo3005513.mockable.io/api/v1/entities/getAllIds").readText())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        thread.start()
        thread.join()
        return idsList
    }

    private fun parseJsonToListIds(ids: String): ArrayList<String>? {
        val parsedIdList = ArrayList<String>()
        try {
            val jsonObject = JSONObject(ids)
            val idsArray = jsonObject.getJSONArray("data")
            for (i in 0 until  idsArray.length()){
                parsedIdList.add(idsArray.getJSONObject(i).getString("id"))
            }
        }catch (e: JSONException){
            e.printStackTrace()
            return null
        }
        return parsedIdList
    }

    fun getResourceById(id: String, typeOfRes: String): String? {
        var record: JSONObject? = null
        val thread = Thread {
            try {
                record = JSONObject(URL("http://demo3005513.mockable.io/api/v1/object/$id").readText())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
        thread.join()
        return record?.getString(typeOfRes)
    }
}