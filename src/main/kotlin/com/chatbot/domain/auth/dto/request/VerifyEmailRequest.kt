package com.chatbot.domain.auth.dto.request

data class VerifyEmailRequest(
    val email: String,
    val verifyCode: String
)
