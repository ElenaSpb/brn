package com.epam.brn.dto

import com.fasterxml.jackson.annotation.JsonIgnore

data class ResourceDto(
    val id: Long? = null,
    val audioFileId: String,
    val word: String,
    val pictureId: String,
    val soundsCount: Int,
    @JsonIgnore
    val task: TaskDto
)