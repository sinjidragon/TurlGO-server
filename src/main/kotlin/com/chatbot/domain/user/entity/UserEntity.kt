package com.chatbot.domain.user.entity

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor
import java.time.LocalDateTime

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    var username: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false) @Enumerated(EnumType.STRING)
    var state: UserState = UserState.CREATED,

    var deletedAt: LocalDateTime? = null,

    var isTested: Boolean = false,

    var testData: String? = null,
)