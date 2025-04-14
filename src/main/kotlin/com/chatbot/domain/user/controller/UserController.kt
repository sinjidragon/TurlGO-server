package com.chatbot.domain.user.controller

import com.chatbot.domain.user.dto.GetUserResponse
import com.chatbot.domain.user.service.UserService
import com.chatbot.global.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "User", description = "유저")
@RestController
@RequestMapping("/users")
class UserController (
    private val userService: UserService
) {
    @Operation(summary = "내 정보 페이지")
    @GetMapping
    fun getMy(principal: Principal): BaseResponse<GetUserResponse> {
        return userService.getMy(principal)
    }

    @Operation(summary = "탈퇴하기")
    @DeleteMapping
    fun deleteUser(principal: Principal): BaseResponse<Unit> {
        return userService.deleteUser(principal)
    }
}