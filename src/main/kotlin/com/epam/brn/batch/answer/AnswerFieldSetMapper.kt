package com.epam.brn.batch.answer

import com.epam.brn.dto.AnswerDto
import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet

class AnswerFieldSetMapper : FieldSetMapper<AnswerDto> {
    override fun mapFieldSet(fieldSet: FieldSet): AnswerDto {
        return AnswerDto(
            id = fieldSet.readLong("id"),
            taskId = fieldSet.readLong("taskId"),
            rightAnswer = fieldSet.readBoolean("rightAnswer"),
            answer = fieldSet.readString("answer")
        )
    }
}