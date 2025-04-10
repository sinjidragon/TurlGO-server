package com.chatbot.domain.animal.dto

data class ApiResponse(
    val TbAdpWaitAnimalView: TbAdpWaitAnimalView
)

data class TbAdpWaitAnimalView(
    val list_total_count: Int,
    val RESULT: Result,
    val row: List<AnimalRow>
)

data class Result(
    val CODE: String,
    val MESSAGE: String
)

data class AnimalRow(
    val ANIMAL_NO: String,
    val NM: String,
    val ENTRNC_DATE: String,
    val SPCS: String,
    val BREEDS: String,
    val SEXDSTN: String,
    val AGE: String,
    val BDWGH: Double,
    val ADP_STTUS: String,
    val TMPR_PRTC_STTUS: String,
    val INTRCN_MVP_URL: String,
    val INTRCN_CN: String?,
    val TMPR_PRTC_CN: String?
)