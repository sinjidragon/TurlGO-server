package com.chatbot.domain.auth.exception

import com.chatbot.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class AuthErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "유저를 찾을 수 없습니다"),
    USERNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST,"BAD_REQUEST", "아이디가 이미 사용중입니다"),
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST,"BAD_REQUEST", "이미 사용중인 이메일입니다"),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST,"BAD_REQUEST", "비밀번호가 일치하지 않습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED,"UNAUTHORIZED", "토큰을 찾을 수 없습니다."),
    CODE_NOT_FOUND(HttpStatus.BAD_REQUEST,"BAD_REQUEST", "인증 코드가 만료되었거나 존재하지 않습니다."),
    CODE_NOT_MATCH(HttpStatus.BAD_REQUEST,"BAD_REQUEST", "인증 코드가 일치하지 않습니다."),
}