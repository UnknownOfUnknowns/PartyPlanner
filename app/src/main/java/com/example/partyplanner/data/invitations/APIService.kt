package com.example.partyplanner.data.invitations

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface APIService {

    @POST("/v3/mail/send")
    suspend fun sendNewMail(@Body requestBody: RequestBody, @Header("Authorization") auth : String) : Response<ResponseBody>
}