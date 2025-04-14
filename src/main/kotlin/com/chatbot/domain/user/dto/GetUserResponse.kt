package com.chatbot.domain.user.dto

import com.chatbot.domain.user.entity.UserEntity
import com.chatbot.domain.user.entity.UserState
import java.time.LocalDateTime

data class GetUserResponse(
    val id: Long,

    val username: String,

    val email: String,

    val state: UserState,

    val deletedAt: LocalDateTime? = null,

    val isTested: Boolean,

    val testData: String? = null,
) {
    companion object {
        fun ofEntity(user: UserEntity): GetUserResponse {
            return GetUserResponse(
                id = user.id,
                username = user.username,
                email = user.email,
                state = user.state,
                deletedAt = user.deletedAt,
                isTested = user.isTested,
                testData = user.testData
            )
        }
    }
}
