package com.epam.brn.dto

import com.fasterxml.jackson.annotation.JsonIgnore

data class ExerciseDto(
    val id: Long?,
    val name: String,
    val description: String?,
    val level: Short? = 0,
    @JsonIgnore
    var exerciseSeriesId: Long,
    val tasks: MutableSet<TaskDto> = HashSet()
)