package com.chatbot.domain.animal.controller

import com.chatbot.domain.animal.service.AnimalScheduledService
import com.chatbot.domain.animal.service.AnimalService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Animal", description = "동물 관련")
@RestController
@RequestMapping("/animal")
class AnimalController (
    private val animalScheduledService: AnimalScheduledService,
    private val animalService: AnimalService
) {
    @Operation(summary = "쓰지마라")
    @GetMapping("/test")
    fun test():String {
        animalScheduledService.fetchAnimalDataDaily()
        return "아니쓰지말라고햇잖아"
    }

    @Operation(summary = "동물 목록")
    @GetMapping()
    fun getAnimals() = animalService.getAnimals()
}