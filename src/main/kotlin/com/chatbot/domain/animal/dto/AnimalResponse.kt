package com.chatbot.domain.animal.dto

import com.chatbot.domain.animal.entity.AnimalEntity

data class AnimalResponse(
    val animalNo: String,

    val name: String,

    var age: String,

    val species: String,

    val photoUrls: List<String> = emptyList(),
) {
    companion object {
        fun fromEntity(entity: AnimalEntity): AnimalResponse {
            return AnimalResponse(
                animalNo = entity.animalNo,
                name = entity.name,
                age = entity.age,
                species = entity.species,
                photoUrls = entity.photoUrls,
            )
        }
    }
}
