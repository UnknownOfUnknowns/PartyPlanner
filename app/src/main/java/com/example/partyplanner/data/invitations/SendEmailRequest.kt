package com.example.partyplanner.data.invitations

import kotlinx.serialization.Serializable

const val STANDARD_TYPE = "text/plain"

@Serializable
data class SendEmailRequest(
    val personalizations : List<Personalization>,
    val from : AddressAndName,
    val subject: String,
    val content: List<ContentItem>
)

@Serializable
data class ContentItem(
    val type : String,
    val value : String
)

@Serializable
data class AddressAndName(
    val email : String
)

@Serializable
data class Personalization(
    val to : List<AddressAndName>
)