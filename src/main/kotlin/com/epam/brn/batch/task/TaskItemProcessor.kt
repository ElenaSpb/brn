package com.epam.brn.batch.task

import com.epam.brn.dto.TaskDto
import org.apache.logging.log4j.kotlin.logger
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class TaskItemProcessor : ItemProcessor<TaskDto, TaskDto> {
    private val log = logger()

    override fun process(item: TaskDto): TaskDto? {
        log.info("$item is processed")
        return TaskDto(
            id = item.id,
            exerciseId = item.exerciseId,
            name = item.name)
    }
}