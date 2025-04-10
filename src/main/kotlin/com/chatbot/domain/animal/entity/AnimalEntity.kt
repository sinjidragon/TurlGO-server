package com.chatbot.domain.animal.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "animal")
class AnimalEntity (
    @Id
    val animalNo: String,

    val name: String,
    val entranceDate: String,
    val species: String,
    val breed: String,
    val sex: String,
    val age: String,
    val bodyWeight: Double,
    val adoptionStatus: String,
    val temporaryProtectionStatus: String,
    val introductionVideoUrl: String,

    @Column(columnDefinition = "TEXT")
    val introductionContent: String?,

    @Column(columnDefinition = "TEXT")
    val temporaryProtectionContent: String?
)