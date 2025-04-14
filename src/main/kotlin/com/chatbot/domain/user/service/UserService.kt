package com.chatbot.domain.user.service

import com.chatbot.domain.user.dto.GetUserResponse
import com.chatbot.domain.user.entity.UserState
import com.chatbot.domain.user.exception.UserErrorCode
import com.chatbot.domain.user.repository.UserRepository
import com.chatbot.global.dto.BaseResponse
import com.chatbot.global.exception.CustomException
import org.springframework.stereotype.Service
import java.security.Principal
import java.time.LocalDateTime

@Service
class UserService (
    val userRepository: UserRepository
) {
    fun getMy(principal: Principal): BaseResponse<GetUserResponse> {
        val user = userRepository.findByUsername(principal.name).orElseThrow { CustomException(
            UserErrorCode.USER_NOT_FOUND) }
        return BaseResponse(
            message = "GetMy Success",
            data = GetUserResponse.ofEntity(user)
        )
    }

    fun deleteUser(principal: Principal): BaseResponse<Unit> {
        val user = userRepository.findByUsername(principal.name).orElseThrow { CustomException(
            UserErrorCode.USER_NOT_FOUND) }

        user.state = UserState.DELETED
        user.deletedAt = LocalDateTime.now()

        userRepository.save(user)

        return BaseResponse(
            message = "User deleted successfully"
        )
    }
}