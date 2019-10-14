package com.epam.brn.dto

import javax.validation.constraints.NotBlank

data class ExerciseSeriesDto(
    val id: Long?,
    @NotBlank
    val name: String,
    val description: String?,
    @NotBlank
    val exerciseGroup: Long,
    val exercises: MutableSet<ExerciseDto> = HashSet()
)