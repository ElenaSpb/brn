package com.epam.brn.batch.task

import com.epam.brn.dto.TaskDto
import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet

class TaskFieldSetMapper : FieldSetMapper<TaskDto> {
    override fun mapFieldSet(fieldSet: FieldSet): TaskDto {
        return TaskDto(
            id = fieldSet.readLong("id"),
            exerciseId = fieldSet.readLong("exerciseId"),
            name = fieldSet.readString("name"),
            serialNumber = fieldSet.readInt("serialNumber")
        )
    }
}