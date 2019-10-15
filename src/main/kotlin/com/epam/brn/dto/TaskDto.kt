package com.epam.brn.dto

import com.fasterxml.jackson.annotation.JsonIgnore

data class TaskDto(
    val id: Long? = null,
    @JsonIgnore
    val exercise: Long? = null,
    @JsonIgnore
    val exerciseId: Long? = null,
    val name: String? = "",
    @JsonIgnore
    val resource: ResourceDto? = null,
    @JsonIgnore
    val resourceId : Long? = null,
    val serialNumber: Int? = 0,
    val arrayAnswers: MutableSet<AnswerDto> = HashSet()
)