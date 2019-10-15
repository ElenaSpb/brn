package com.epam.brn.dto

data class ResourceDto(
    val id: Long? = null,
    val audioFileId: String,
    val word: String,
    val pictureId: String,
    val soundsCount: Int,
    val taskId: Long? = null
)