package com.epam.brn.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.validation.constraints.NotBlank

data class AnswerDto(
    val id: Long? = null,
    @NotBlank
    @JsonIgnore
    val taskId: Long?,
    @NotBlank
    val rightAnswer: Boolean,
    @NotBlank
    val answer: String
)