package com.chatbot.domain.test.service

import com.chatbot.domain.test.dto.AnimalDetailResponse
import com.chatbot.domain.user.exception.UserErrorCode
import com.chatbot.domain.user.repository.UserRepository
import com.chatbot.global.dto.BaseResponse
import com.chatbot.global.exception.CustomException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.lang.Thread.sleep
import java.security.Principal

@Service
class TestService (
    private val objectMapper: ObjectMapper,

    @Value("\${spring.ai.openai.api-key}")
    private val apiKey: String,

    @Value("\${spring.ai.openai.assistantId2}")
    private val assistantId: String,

    private val userRepository: UserRepository
) {
    private val restTemplate = RestTemplate()
    private val apiUrl = "https://api.openai.com/v1/threads"

    val headers = HttpHeaders().apply {
        set("Authorization", "Bearer $apiKey")
        set("Content-Type", "application/json")
        set("OpenAI-Beta", "assistants=v2")
    }

    fun sendMessage(request: AnimalDetailResponse, principal: Principal): BaseResponse<String> {
        val user = userRepository.findByUsername(principal.name).orElseThrow { CustomException(UserErrorCode.USER_NOT_FOUND) }

        val message = "${request.species} ${request.sizeOrBodyType} ${request.personality} ${request.furTypeOrLength} ${request.specialTraits} ${request.gender}"
        val threadId = createThread()
        createMessage(threadId, message)
        val runId = runAssistant(threadId)
        sleep(1000)
        val response = getStatus(threadId, runId)

        user.isTested = true
        user.testData = response
        userRepository.save(user)
        return BaseResponse(
            message = "test successful",
            data = response
        )
    }

    // thread 발급
    fun createThread(): String {
        val entity = HttpEntity<String>(headers)
        val response: ResponseEntity<String> = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String::class.java)
        val rootNode: JsonNode = objectMapper.readTree(response.body)
        return rootNode["id"].asText()
    }

    // 메시지 생성
    fun createMessage(threadId: String, message: String) {
        val requestBody = mapOf(
            "role" to "user",
            "content" to message
        )
        val entity = HttpEntity(requestBody, headers)
        restTemplate.exchange("$apiUrl/$threadId/messages", HttpMethod.POST, entity, String::class.java)
    }

    // assistant 실행하기
    fun runAssistant(threadId: String): String {
        val requestBody = mapOf(
            "assistant_id" to assistantId,
        )
        val entity = HttpEntity(requestBody, headers)
        val response = restTemplate.exchange("$apiUrl/$threadId/runs", HttpMethod.POST, entity, String::class.java)
        val jsonNode: JsonNode = objectMapper.readTree(response.body)
        return jsonNode["id"]?.asText() ?: ""
    }

    fun getStatus(threadId: String, runId: String): String {
        while (true) {
            try {
                val entity = HttpEntity<String>(headers)
                val response = restTemplate.exchange("$apiUrl/$threadId/runs/$runId", HttpMethod.GET, entity, String::class.java)
                val jsonNode: JsonNode = objectMapper.readTree(response.body)
                val status = jsonNode["status"]?.asText() ?: ""

                if (status != "completed") {
                    sleep(500)
                } else {
                    return getMessage(threadId)
                }
            } catch (e: Exception) {

            }
        }
    }

    // 메시지 가져오기
    fun getMessage(threadId: String): String {
        val entity = HttpEntity<String>(headers)
        val response = restTemplate.exchange("$apiUrl/$threadId/messages", HttpMethod.GET, entity, String::class.java)
        val responseBody = objectMapper.readValue<Map<String, Any>>(response.body!!)
        val data = responseBody["data"] as? List<Map<String, Any>>
        val firstMessage = data?.firstOrNull()
        val contentList = firstMessage?.get("content") as? List<Map<String, Any>>
        val value = contentList?.firstOrNull()?.let { contentItem ->
            val textMap = contentItem["text"] as? Map<String, Any>
            textMap?.get("value") as? String
        }
        return value ?: ""
    }
}
