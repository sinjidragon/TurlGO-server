package com.chatbot.domain.test.controller

import com.chatbot.domain.test.dto.AnimalDetailRequest
import com.chatbot.domain.test.service.TestService
import com.chatbot.global.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "Test", description = "반려동물 테스트")
@RestController
@RequestMapping("/test")
class TestController (
    private val testService: TestService
) {
    @Operation(summary = "응답 결과 보내기", description = "species, 강아지 / 고양이\n" +
            "    sizeOrBodyType,  강아지: 소형견/중형견/대형견, 고양이: 체형\n" +
            "    personality,     성격 및 활동성\n" +
            "    furTypeOrLength,  털 유형 or 털 길이\n" +
            "    specialTraits, (optional) 특정 역할, 특징 (고양이면 알러지 강한지/특이 외모 등)\n" +
            "    gender,           성별")
    @PostMapping()
    fun petTest(@RequestBody request: AnimalDetailRequest, principal: Principal): BaseResponse<String> {
        return testService.sendMessage(request, principal)
    }

}