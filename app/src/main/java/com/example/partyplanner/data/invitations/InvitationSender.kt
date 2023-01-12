package com.example.partyplanner.data.invitations

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit

class InvitationSender {
    val baseUrl = "https://api.sendgrid.com"

    private val API_KEY = "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"
    fun send() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()

        val service = retrofit.create(APIService::class.java)

        val jsonObject = MailRequestFactory.createMailRequestNew()

        CoroutineScope(Dispatchers.IO).launch {
            val response = service
                .sendNewMail(
                    jsonObject.toRequestBody("application/json".toMediaTypeOrNull()), "Bearer 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")
            withContext(Dispatchers.Main) {
                println(response.errorBody())
                val gson = GsonBuilder().setPrettyPrinting().create()
                val prettyJson = gson.toJson(
                    JsonParser.parseString(
                        response.body()?.string()
                    )
                )

                Log.d("Email",prettyJson)
                println(prettyJson)
            }
        }
    }
}