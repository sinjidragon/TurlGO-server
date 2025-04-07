package com.chatbot.domain.auth.dto.request

import jakarta.validation.constraints.Email

data class SendMailRequest(
    @Email
    val email: String
)
