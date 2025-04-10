package com.chatbot.domain.animal.entity

import jakarta.persistence.*

@Entity(name = "animals")
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
    val temporaryProtectionContent: String?,

    @ElementCollection
    @CollectionTable(
        name = "animal_photos",
        joinColumns = [JoinColumn(name = "animal_no")]
    )
    @Column(name = "photo_url")
    val photoUrls: List<String> = emptyList()
)