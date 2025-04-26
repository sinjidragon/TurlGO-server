package com.chatbot.domain.animal.exception

import com.chatbot.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class AnimalErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {

    ANIMAL_NOT_FOUND_EXCEPTION(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "동물을 찾을 수 없습니다.")

}