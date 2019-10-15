package com.epam.brn.batch

import com.epam.brn.batch.answer.AnswerBatchConfiguration
import com.epam.brn.batch.excercisegroup.ExerciseGroupBatchConfiguration
import com.epam.brn.constant.Batch.ANSWER_INPUT_FILE
import com.epam.brn.constant.Batch.EXERCISE_GROUP_INPUT_FILE
import com.epam.brn.constant.Batch.EXERCISE_INPUT_FILE
import com.epam.brn.constant.Batch.EXERCISE_SERIES_INPUT_FILE
import com.epam.brn.constant.Batch.TASK_INPUT_FILE
import org.springframework.batch.core.Job
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(AnswerBatchConfiguration::class, ExerciseGroupBatchConfiguration::class)
class BatchConfiguration {

    @Autowired
    private lateinit var importAnswersJob: Job

    @Autowired
    private lateinit var importTaskJob: Job

    @Autowired
    private lateinit var importExerciseGroupJob: Job

    @Autowired
    private lateinit var importExerciseSeriesJob: Job

    @Autowired
    private lateinit var importExerciseJob: Job

    @Bean
    fun sourcesWithJobs(): LinkedHashMap<String, Job> {
        return linkedMapOf(
            EXERCISE_GROUP_INPUT_FILE to importExerciseGroupJob,
            EXERCISE_SERIES_INPUT_FILE to importExerciseSeriesJob,
            EXERCISE_INPUT_FILE to importExerciseJob,
            TASK_INPUT_FILE to importTaskJob,
            ANSWER_INPUT_FILE to importAnswersJob
        )
    }
}
