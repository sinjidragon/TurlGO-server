package com.chatbot.domain.auth.dto.request

import jakarta.validation.constraints.Email

data class AuthSignupRequest(
    val username: String,
    @Email val email: String,
    val password: String
)
