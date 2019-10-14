package com.epam.brn.batch.excercisegroup

import com.epam.brn.dto.ExerciseGroupDto
import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet

class ExerciseGroupFieldSetMapper : FieldSetMapper<ExerciseGroupDto> {

    override fun mapFieldSet(fieldSet: FieldSet): ExerciseGroupDto {
        return ExerciseGroupDto(
            id = fieldSet.readLong("id"),
            name = fieldSet.readString("name"),
            description = fieldSet.readString("description")
        )
    }
}