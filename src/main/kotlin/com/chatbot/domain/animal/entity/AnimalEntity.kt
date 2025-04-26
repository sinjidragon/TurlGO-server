package com.chatbot.domain.animal.entity

import jakarta.persistence.*

@Entity(name = "animals")
class AnimalEntity (
    @Id
    var animalNo: String,

    var name: String,
    var entranceDate: String,
    var species: String,
    var breed: String,
    var sex: String,
    var age: String,
    var bodyWeight: Double,
    var adoptionStatus: String,
    var temporaryProtectionStatus: String,
    var introductionVideoUrl: String,

    @Column(columnDefinition = "TEXT")
    var introductionContent: String?,

    @Column(columnDefinition = "TEXT")
    var temporaryProtectionContent: String?,

    @ElementCollection
    @CollectionTable(
        name = "animal_photos",
        joinColumns = [JoinColumn(name = "animal_no")]
    )
    @Column(name = "photo_url")
    var photoUrls: List<String> = emptyList(),

    @Column(length = 5000)
    var calculatedData : String? = null
)