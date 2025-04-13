package com.chatbot.domain.user.service

import com.chatbot.domain.user.entity.UserEntity
import com.chatbot.domain.user.entity.UserState
import com.chatbot.domain.user.repository.UserRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class UserCleanupScheduler (
    val userRepository: UserRepository,
) {
    @Scheduled(cron = "0 0 0 * * ?")
    fun deleteExpiredUsers() {
        val threeMonthsAgo = LocalDateTime.now().minus(3, ChronoUnit.MONTHS)

        val usersToDelete: List<UserEntity?>? =
            userRepository.findByStateAndDeletedAtBefore(UserState.DELETED, threeMonthsAgo)

        if (usersToDelete != null)
            userRepository.deleteAll(usersToDelete)
    }
}