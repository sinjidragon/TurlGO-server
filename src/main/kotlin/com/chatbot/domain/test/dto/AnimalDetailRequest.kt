package com.chatbot.domain.test.dto

data class AnimalDetailRequest(
    val housingType: String,              // 주거형태 (아파트, 단독주택, 빌라 / 다세대 주택)
    val preferredAnimal: String,         // 선호 동물 (고양이, 강아지)
    val preferredPersonality: String,    // 선호 성격
    val sheddingSensitivity: String,     // 털 빠짐 민감도 (매우 민감, 어느정도 신경 씀, 신경쓰지 않음)
    val availableTime: String,           // 반려동물과 보낼 수 있는 예상 시간
    val budget: String,                  // 반려동물을 위해 사용할 수 있는 예산
    val hasAllergy: String               // 알러지 여부 (예, 아니오)
)