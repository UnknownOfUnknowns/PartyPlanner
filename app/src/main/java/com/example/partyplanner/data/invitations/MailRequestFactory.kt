package com.example.partyplanner.data.invitations

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject

object MailRequestFactory {
    fun createMailRequest() : JSONObject {
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
        return jsonObject
    }

    fun createMailRequestNew(): String {
        val content = ContentItem(STANDARD_TYPE, "Hello")
        val receiver = AddressAndName("s215705@student.dtu.dk")
        val sender = AddressAndName("s215708@dtu.dk")
        val personalization = Personalization(listOf(receiver))
        val requestObject = SendEmailRequest(
            personalizations = listOf(personalization),
            from = sender,
            subject = "Test subject",
            content = listOf(content)
        )
        return Json.encodeToString(requestObject)
    }
}