package com.epam.brn.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.validation.constraints.NotBlank

data class AnswerDto(
    @NotBlank
    val id: Long,
    @NotBlank
    @JsonIgnore
    val taskId: Long,
    @JsonIgnore
    val task: TaskDto? = null,
    @NotBlank
    val rightAnswer: Boolean,
    @NotBlank
    val answer: String
)