package com.chatbot.domain.animal.service

import com.chatbot.domain.animal.entity.AnimalEntity
import com.chatbot.domain.animal.repository.AnimalRepository
import com.chatbot.global.dto.BaseResponse
import org.springframework.stereotype.Service

@Service
class AnimalService (
    val animalRepository: AnimalRepository
) {
    fun getAnimals(): BaseResponse<List<AnimalEntity>> {
        return BaseResponse(
            message = "getAnimals",
            data = animalRepository.findAll()
        )
    }

}