package com.epam.brn.dto

import com.epam.brn.model.Exercise
import com.epam.brn.model.Resource
import com.fasterxml.jackson.annotation.JsonIgnore

data class TaskDto(
    val id: Long? = null,
    @JsonIgnore
    val exercise: Exercise? = null,
    @JsonIgnore
    val exerciseId: Long,
    val name: String? = "",
    @JsonIgnore
    val resource: ResourceDto? = null,
    @JsonIgnore
    val resourceId : Long? = null,
    val serialNumber: Int? = 0,
    val arrayAnswers: MutableSet<AnswerDto> = HashSet()
)