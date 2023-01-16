package com.example.partyplanner.data.invitations

import com.example.partyplanner.data.Guest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object MailRequestFactory {

    fun createMailRequest(guest: Guest): String {
        val content = ContentItem(STANDARD_TYPE, "Hej ${guest.name}\nDu inviteres hermed til fest. Åbn PartyPlanner appen og " +
                "indtast nedenstående kode når du har trykket på knappen \"Åbn invitation\"\nKode: ${guest.id}")
        val receiver = AddressAndName(guest.contactAddress)
        val sender = AddressAndName("scsolutionsapp@gmail.com")
        val personalization = Personalization(listOf(receiver))
        val requestObject = SendEmailRequest(
            personalizations = listOf(personalization),
            from = sender,
            subject = "Invitation til fest",
            content = listOf(content)
        )
        return Json.encodeToString(requestObject)
    }
}