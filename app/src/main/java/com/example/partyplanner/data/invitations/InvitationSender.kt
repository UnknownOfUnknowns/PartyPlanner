package com.example.partyplanner.data.invitations

import com.example.partyplanner.data.Guest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit

class InvitationSender {
    val baseUrl = "https://api.sendgrid.com"

    private val API_KEY = "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"
    fun send(guest: Guest) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()

        val service = retrofit.create(APIService::class.java)

        val jsonObject = MailRequestFactory.createMailRequest(guest)

        CoroutineScope(Dispatchers.IO).launch {
            val t = service.sendNewMail(jsonObject.toRequestBody("application/json".toMediaTypeOrNull()), "Bearer $API_KEY")
            println("d")
        }
    }
}