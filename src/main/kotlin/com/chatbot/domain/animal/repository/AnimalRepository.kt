package com.chatbot.domain.animal.repository

import com.chatbot.domain.animal.entity.AnimalEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AnimalRepository : JpaRepository<AnimalEntity, String> {

}