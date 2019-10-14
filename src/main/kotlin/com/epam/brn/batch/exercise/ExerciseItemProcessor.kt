package com.epam.brn.batch.exercise

import com.epam.brn.dto.ExerciseDto
import org.apache.logging.log4j.kotlin.logger
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class ExerciseItemProcessor : ItemProcessor<ExerciseDto, ExerciseDto> {
    private val log = logger()

    override fun process(item: ExerciseDto): ExerciseDto? {
        log.info("$item is processed")
        return item
    }
}