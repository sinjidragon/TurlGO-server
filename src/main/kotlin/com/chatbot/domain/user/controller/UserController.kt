package com.chatbot.domain.user.controller

import com.chatbot.domain.user.service.UserService
import com.chatbot.global.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.security.Principal

@Tag(name = "User", description = "유저")
@Controller
@RequestMapping("/users")
class UserController (
    private val userService: UserService
) {
//    @Operation(summary = "내 정보 페이지")
//    @GetMapping
//    fun getMy(principal: Principal): BaseResponse<> {
//
//    }

    @Operation(summary = "탈퇴하기")
    @DeleteMapping
    fun deleteUser(principal: Principal): BaseResponse<Unit> {
        return userService.deleteUser(principal)
    }
}