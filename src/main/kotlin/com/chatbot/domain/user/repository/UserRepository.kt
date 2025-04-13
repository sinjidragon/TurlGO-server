package com.chatbot.domain.user.repository

import com.chatbot.domain.user.entity.UserEntity
import com.chatbot.domain.user.entity.UserState
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.*

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByStateAndDeletedAtBefore(state: UserState?, date: LocalDateTime?): List<UserEntity?>?
    fun findByEmail(email: String): Optional<UserEntity>
    fun findByUsername(username: String): Optional<UserEntity>
    fun findByEmailOrUsername(email: String, username: String): List<UserEntity>
}