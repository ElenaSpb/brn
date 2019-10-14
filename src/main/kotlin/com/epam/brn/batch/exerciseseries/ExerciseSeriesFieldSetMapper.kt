package com.epam.brn.batch.exerciseseries

import com.epam.brn.dto.ExerciseSeriesDto
import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet

class ExerciseSeriesFieldSetMapper : FieldSetMapper<ExerciseSeriesDto> {

    override fun mapFieldSet(fieldSet: FieldSet): ExerciseSeriesDto {
        return ExerciseSeriesDto(
            id = fieldSet.readLong("id"),
            name = fieldSet.readString("name"),
            description = fieldSet.readString("description"),
            exerciseGroup = fieldSet.readLong("exerciseGroupId")
        )
    }
}