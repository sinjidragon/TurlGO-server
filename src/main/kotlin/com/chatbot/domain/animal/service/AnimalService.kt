package com.chatbot.domain.animal.service

import com.chatbot.domain.animal.dto.AnimalResponse
import com.chatbot.domain.animal.entity.AnimalEntity
import com.chatbot.domain.animal.exception.AnimalErrorCode
import com.chatbot.domain.animal.repository.AnimalRepository
import com.chatbot.global.dto.BaseResponse
import com.chatbot.global.exception.CustomException
import org.springframework.stereotype.Service

@Service
class AnimalService (
    val animalRepository: AnimalRepository
) {
    fun getAnimals(): BaseResponse<List<AnimalResponse>> {
        return BaseResponse(
            message = "getAnimals",
            data = animalRepository.findAll()
                .map { AnimalResponse.fromEntity(it) }
        )
    }

    fun getAnimal(animalNo: String): BaseResponse<AnimalEntity> {
        return BaseResponse(
            message = "getAnimal",
            data = animalRepository.findById(animalNo).orElseThrow { throw CustomException(AnimalErrorCode.ANIMAL_NOT_FOUND_EXCEPTION) }
        )
    }
}