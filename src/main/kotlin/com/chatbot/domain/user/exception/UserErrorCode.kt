package com.chatbot.domain.user.exception

import com.chatbot.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class UserErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {

    USERNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "이미 사용중인 아이디입니다."),
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "이미 사용중인 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "유저를 찾을 수 없습니다"),
    USER_ALREADY_EXIST(HttpStatus.CONFLICT, "CONFLICT", "유저가 이미 존재합니다"),
    USER_NOT_MATCH(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "비밀번호가 잘못되었습니다."),
    USER_IS_DELETED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED" ,"삭제된 유저입니다."),

}