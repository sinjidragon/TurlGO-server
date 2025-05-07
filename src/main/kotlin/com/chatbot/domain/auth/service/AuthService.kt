package com.chatbot.domain.auth.service

import com.chatbot.domain.auth.dto.request.AuthLoginRequest
import com.chatbot.domain.auth.dto.request.AuthRefreshRequest
import com.chatbot.domain.auth.dto.request.AuthSignupRequest
import com.chatbot.domain.auth.dto.response.AuthTokenResponse
import com.chatbot.domain.user.entity.UserEntity
import com.chatbot.domain.user.entity.UserState
import com.chatbot.domain.user.exception.UserErrorCode
import com.chatbot.domain.user.repository.UserRepository
import com.chatbot.global.dto.BaseResponse
import com.chatbot.global.exception.CustomException
import com.chatbot.global.jwt.JwtUtil
import com.chatbot.global.jwt.execption.JwtErrorCode
import com.chatbot.global.redis.RedisService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService (
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,
    private val redisService: RedisService
) {
    fun checkUsername(username: String?): BaseResponse<Unit> {
        if (username != null) {
            if (userRepository.existsByUsername(username))
                throw CustomException(UserErrorCode.USERNAME_ALREADY_EXIST)
        }

        return BaseResponse(message = "checkUsername successful")
    }

    fun signup(request: AuthSignupRequest) : BaseResponse<Unit>  {
        if (userRepository.existsByUsername(request.username))
            throw CustomException(UserErrorCode.USERNAME_ALREADY_EXIST)
        if(userRepository.findByEmailOrUsername(request.email, request.username).isNotEmpty())
            throw CustomException(UserErrorCode.USER_ALREADY_EXIST)
        val user = UserEntity(
            username = request.username,
            email = request.email,
            password = BCryptPasswordEncoder().encode(request.password),
        )
        userRepository.save(user)

        return BaseResponse(
            message = "회원가입 성공"
        )
    }

    fun login(authLoginRequest: AuthLoginRequest) : BaseResponse<AuthTokenResponse> {
        val user = userRepository.findByUsername(authLoginRequest.username).orElseThrow {
            throw CustomException(UserErrorCode.USER_NOT_FOUND)
        }

        if (!BCryptPasswordEncoder().matches(authLoginRequest.password, user.password))
            throw CustomException(UserErrorCode.USER_NOT_MATCH)

        if (user.state === UserState.DELETED)
            throw CustomException(UserErrorCode.USER_IS_DELETED)

        return BaseResponse(
            message = "로그인 성공",
            data = jwtUtil.generateToken(user)
        )
    }

    fun refresh(authRefreshRequest: AuthRefreshRequest) : BaseResponse<AuthTokenResponse> {
        if (authRefreshRequest.refreshToken.isEmpty())
            throw CustomException(JwtErrorCode.JWT_EMPTY_EXCEPTION)

        val id: Long = jwtUtil.getUserId(authRefreshRequest. refreshToken)
        val user = userRepository.findById(id).orElseThrow {
            throw CustomException(UserErrorCode.USER_NOT_FOUND)
        }

        val cachedToken = redisService.getRefreshToken(id)

        if (cachedToken != authRefreshRequest.refreshToken)
            throw CustomException(JwtErrorCode.JWT_TOKEN_ERROR)

        return BaseResponse(
            message = "리프레시 성공",
            data = jwtUtil.generateToken(user)
        )
    }
}