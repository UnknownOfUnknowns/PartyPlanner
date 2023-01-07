package com.example.partyplanner.data.invitations

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit

class InvitationSender {
    val baseUrl = "https://api.sendgrid.com"

    private val API_KEY = "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"
    fun send() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()

        val service = retrofit.create(APIService::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("from", JSONObject().put("email", "s215708@dtu.dk"))
        jsonObject.put("subject", "Test subject")
        val email = JSONObject().put("email", "hans@bilstrupandersen.dk")
        jsonObject.put("personalizations", JSONArray().put(
            JSONObject().put("to", JSONArray().put(email))))

        val content = JSONObject()
        content.put("type", "text/plain")
        content.put("value", "and easy to do")

        jsonObject.put("content", JSONArray().put(content))

        CoroutineScope(Dispatchers.IO).launch {
            val response = service
                .sendNewMail(RequestBody.create("application/json".toMediaTypeOrNull(),jsonObject.toString()), "Bearer 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")
            withContext(Dispatchers.Main) {
                println(response.errorBody())
                val gson = GsonBuilder().setPrettyPrinting().create()
                val prettyJson = gson.toJson(
                    JsonParser.parseString(
                        response.body()?.string()
                    )
                )

                Log.d("Email",prettyJson)
            }
        }
    }
}