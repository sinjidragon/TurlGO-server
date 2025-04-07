package com.chatbot.domain.auth.controller

import com.chatbot.domain.auth.dto.request.*
import com.chatbot.domain.auth.dto.response.AuthTokenResponse
import com.chatbot.domain.auth.service.AuthService
import com.chatbot.global.dto.BaseResponse
import com.chatbot.domain.auth.service.MailService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Auth", description = "인증/인가")
@RestController
@RequestMapping("/auth")
class AuthController (
    private val authService: AuthService,
    private val mailService: MailService

) {
    @Operation(summary = "Login")
    @PostMapping("/login")
    fun login(@RequestBody authLoginRequest: AuthLoginRequest): BaseResponse<AuthTokenResponse> {
        return authService.login(authLoginRequest)
    }

    @Operation(summary = "User Sign-Up")
    @PostMapping("/sign-up")
    fun register(@RequestBody authSignupRequest: AuthSignupRequest): BaseResponse<Unit> {
        return authService.signup(authSignupRequest)
    }

    @Operation(summary = "Token Refresh")
    @PostMapping("/refresh")
    fun refresh(
        @RequestBody authRefreshRequest: AuthRefreshRequest): BaseResponse<AuthTokenResponse> {
        return authService.refresh(authRefreshRequest)
    }

    @Operation(summary = "이메일 중복 확인, 인증코드 발송")
    @PostMapping("/sendmail")
    fun sendMail(@RequestBody request: SendMailRequest): BaseResponse<Unit> {
        return mailService.sendMail(request)
    }

    @Operation(summary = "인증코드 확인")
    @PostMapping("/verify")
    fun verify(@RequestBody emailRequest: VerifyEmailRequest): BaseResponse<Unit> {
        return mailService.verify(emailRequest)
    }
}