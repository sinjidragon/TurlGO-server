package com.chatbot.domain.test.dto

data class AnimalDetailResponse(
    val species: String,        // 강아지 / 고양이
    val sizeOrBodyType: String, // 강아지: 소형견/중형견/대형견, 고양이: 체형
    val personality: String,    // 성격 및 활동성
    val furTypeOrLength: String, // 털 유형 or 털 길이
    val specialTraits: String?, // (optional) 특정 역할, 특징 (고양이면 알러지 강한지/특이 외모 등)
    val gender: String          // 성별
)