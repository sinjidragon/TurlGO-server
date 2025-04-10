package com.chatbot.domain.animal.service

import com.chatbot.domain.animal.dto.ApiPhotoResponse
import com.chatbot.domain.animal.dto.ApiResponse
import com.chatbot.domain.animal.entity.AnimalEntity
import com.chatbot.domain.animal.repository.AnimalRepository
import org.hibernate.query.sqm.tree.SqmNode.log
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class AnimalScheduledService(
    private val animalRepository: AnimalRepository,
    private val restTemplate: RestTemplate,

    @Value("\${openapi.key1}")
    private val apiUrl1: String,

    @Value("\${openapi.key2}")
    private val apiUrl2: String,
) {
    fun fetchAndSaveAnimalData() {
        try {
            val response = restTemplate.getForObject(apiUrl1, ApiResponse::class.java)
                ?: throw RuntimeException("API 응답 실패")

            if (response.TbAdpWaitAnimalView.RESULT.CODE != "INFO-000") {
                throw RuntimeException("API 오류: ${response.TbAdpWaitAnimalView.RESULT.MESSAGE}")
            }

            log.info("총 ${response.TbAdpWaitAnimalView.row.size}개의 동물 데이터를 받았습니다.")

            val photoMap = fetchPhotoData()

            response.TbAdpWaitAnimalView.row.forEach { animalRow ->
                try {
                    val cleanIntroContent = cleanHtmlTags(animalRow.INTRCN_CN)
                    val cleanProtectionContent = cleanHtmlTags(animalRow.TMPR_PRTC_CN)

                    val animalEntity = AnimalEntity(
                        animalNo = animalRow.ANIMAL_NO,
                        name = animalRow.NM,
                        entranceDate = animalRow.ENTRNC_DATE,
                        species = animalRow.SPCS,
                        breed = animalRow.BREEDS,
                        sex = animalRow.SEXDSTN,
                        age = animalRow.AGE,
                        bodyWeight = animalRow.BDWGH,
                        adoptionStatus = animalRow.ADP_STTUS,
                        temporaryProtectionStatus = animalRow.TMPR_PRTC_STTUS,
                        introductionVideoUrl = animalRow.INTRCN_MVP_URL,
                        introductionContent = cleanIntroContent,
                        temporaryProtectionContent = cleanProtectionContent,
                        photoUrls = photoMap[animalRow.ANIMAL_NO] ?: emptyList()

                    )

                    val existingAnimal = animalRepository.findById(animalEntity.animalNo)

                    if (existingAnimal.isPresent) {
                        log.info("ID: ${animalEntity.animalNo} 데이터 업데이트")
                    } else {
                        log.info("ID: ${animalEntity.animalNo} 새 데이터 저장")
                    }

                    animalRepository.save(animalEntity)
                } catch (e: Exception) {
                    log.error("동물 ID: ${animalRow.ANIMAL_NO} 처리 중 오류 발생", e)
                }
            }

            log.info("동물 데이터 처리 완료")
        } catch (e: Exception) {
            log.error("데이터 조회 또는 저장 중 오류 발생", e)
            throw e
        }
    }

    private fun fetchPhotoData(): Map<String, List<String>> {
        return try {
            val photoResponse = restTemplate.getForObject(apiUrl2, ApiPhotoResponse::class.java)
            log.info("${photoResponse.toString()} 사진데이터 수집")
            photoResponse?.TbAdpWaitAnimalPhotoView?.row
                ?.groupBy { it.ANIMAL_NO }
                ?.mapValues { entry ->
                    entry.value.map { it.PHOTO_URL }
                } ?: emptyMap()

        } catch (e: Exception) {
            log.error("사진 데이터 수집 실패", e)
            emptyMap()
        }
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    fun fetchAnimalDataDaily() {
        log.info("정기 동물 데이터 수집 시작")
        try {
            fetchAndSaveAnimalData()
            log.info("정기 동물 데이터 수집 완료")
        } catch (e: Exception) {
            log.error("정기 동물 데이터 수집 실패", e)
        }
    }

    fun cleanHtmlTags(html: String?): String? {
        return html?.let {
            try {
                Jsoup.parse(it).text()
            } catch (e: Exception) {
                log.warn("HTML 태그 제거 실패", e)
                it
            }
        }
    }
}
