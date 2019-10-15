package com.epam.brn.batch.answer

import com.epam.brn.dto.AnswerDto
import org.apache.logging.log4j.kotlin.logger
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class AnswerItemProcessor : ItemProcessor<AnswerDto, AnswerDto> {
    private val log = logger()

    override fun process(item: AnswerDto): AnswerDto? {
        log.info("$item is processed")
        return AnswerDto(
            id = item.id,
            taskId = item.taskId,
            rightAnswer = item.rightAnswer,
            answer = item.answer
        )
    }
}