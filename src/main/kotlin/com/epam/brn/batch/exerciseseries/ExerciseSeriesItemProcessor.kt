package com.epam.brn.batch.exerciseseries

import com.epam.brn.dto.ExerciseSeriesDto
import org.apache.logging.log4j.kotlin.logger
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class ExerciseSeriesItemProcessor : ItemProcessor<ExerciseSeriesDto, ExerciseSeriesDto> {
    private val log = logger()

    override fun process(item: ExerciseSeriesDto): ExerciseSeriesDto? {
        log.info("$item is processed")
        return ExerciseSeriesDto(item.id, item.name, item.description, item.exerciseGroup)
    }
}