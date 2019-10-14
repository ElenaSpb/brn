package com.epam.brn.batch.excercisegroup

import com.epam.brn.dto.ExerciseGroupDto
import org.apache.logging.log4j.kotlin.logger
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class ExerciseGroupItemProcessor : ItemProcessor<ExerciseGroupDto, ExerciseGroupDto> {
    private val log = logger()

    override fun process(item: ExerciseGroupDto): ExerciseGroupDto? {
        log.info("$item is processed")
        return ExerciseGroupDto(item.id, item.name, item.description)
    }
}