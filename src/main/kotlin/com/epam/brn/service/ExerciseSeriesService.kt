package com.epam.brn.service

import com.epam.brn.dto.ExerciseSeriesDto
import com.epam.brn.model.ExerciseSeries
import com.epam.brn.repo.ExerciseSeriesRepository
import org.apache.logging.log4j.kotlin.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ExerciseSeriesService(@Autowired val exerciseSeriesRepository: ExerciseSeriesRepository) {

    private val log = logger()

    fun findSeries(userId: String): List<ExerciseSeriesDto> {
        val series = exerciseSeriesRepository.findAll()
        return series.map { seriesEntry -> seriesEntry.toDto() }
    }
}