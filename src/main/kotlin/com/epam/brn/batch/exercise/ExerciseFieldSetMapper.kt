package com.epam.brn.batch.exercise

import com.epam.brn.dto.ExerciseDto
import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet

class ExerciseFieldSetMapper : FieldSetMapper<ExerciseDto> {

    override fun mapFieldSet(fieldSet: FieldSet): ExerciseDto {
        return ExerciseDto(
            id = fieldSet.readLong("id"),
            name = fieldSet.readString("name"),
            description = fieldSet.readString("description"),
            exerciseSeriesId = fieldSet.readLong("exerciseSeriesId")
        )
    }
}