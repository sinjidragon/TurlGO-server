package com.chatbot.domain.chat.controller

import com.chatbot.domain.chat.dto.request.MessageRequest
import com.chatbot.domain.chat.entity.ChatMessage
import com.chatbot.domain.chat.service.ChatService
import com.chatbot.domain.user.entity.UserEntity
import com.chatbot.domain.user.exception.UserErrorCode
import com.chatbot.domain.user.repository.UserRepository
import com.chatbot.global.exception.CustomException
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.security.Principal

@Tag(name = "쓰지마라")
@RestController
@RequestMapping("/chatbot")
class ChatController (
    private val chatService: ChatService,
    private val userRepository: UserRepository
) {
    @PostMapping("/send")
    fun sendMessage(principal: Principal, @RequestBody request: MessageRequest): ChatMessage {
        val user: UserEntity = userRepository.findByUsername(principal.name).orElseThrow{ CustomException(UserErrorCode.USER_NOT_FOUND) }
        return chatService.sendMessage(user.id.toString(), request.message)
    }

    @DeleteMapping("/clear")
    fun clearHistory(principal: Principal) {
        val user: UserEntity = userRepository.findByUsername(principal.name).orElseThrow{CustomException(UserErrorCode.USER_NOT_FOUND)}
        chatService.clearHistory(user.id.toString())
    }

    @GetMapping("/history")
    fun getHistory(principal: Principal): List<ChatMessage> {
        val user: UserEntity = userRepository.findByUsername(principal.name).orElseThrow{CustomException(UserErrorCode.USER_NOT_FOUND)}
        return chatService.getHistory(user.id.toString())
    }
}