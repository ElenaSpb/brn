package com.epam.brn.batch.exercise

import com.epam.brn.batch.JobCompletionNotificationListener
import com.epam.brn.constant.Batch.RESOURCE_EXERCISE_INPUT_FILE
import com.epam.brn.dto.ExerciseDto
import org.apache.commons.lang3.math.NumberUtils
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.LineMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import javax.sql.DataSource

@Configuration
class ExerciseBatchConfiguration {
    @Autowired
    private lateinit var jobBuilderFactory: JobBuilderFactory

    @Autowired
    private lateinit var stepBuilderFactory: StepBuilderFactory

    @Value(RESOURCE_EXERCISE_INPUT_FILE)
    lateinit var inputResource: Resource

    @Bean
    fun importExerciseJob(listener: JobCompletionNotificationListener, exerciseImportFirstStep: Step): Job {
        return jobBuilderFactory.get("importExerciseJob")
            .incrementer(RunIdIncrementer())
            .listener(listener)
            .flow(exerciseImportFirstStep)
            .end()
            .build()
    }

    @Bean
    fun exerciseImportFirstStep(
        exerciseWriter: JdbcBatchItemWriter<ExerciseDto>,
        exerciseItemProcessor: ItemProcessor<ExerciseDto, ExerciseDto>
    ): Step {
        return stepBuilderFactory.get("exerciseFirstStep")
            .chunk<ExerciseDto, ExerciseDto>(10)
            .reader(exerciseReader())
            .processor(exerciseItemProcessor)
            .writer(exerciseWriter)
            .build()
    }

    @Bean
    fun exerciseReader(): FlatFileItemReader<ExerciseDto> {
        val itemReader = FlatFileItemReader<ExerciseDto>()
        itemReader.setLineMapper(exerciseLineMapper())
        itemReader.setLinesToSkip(NumberUtils.INTEGER_ONE)
        itemReader.setResource(inputResource)
        return itemReader
    }

    @Bean
    fun exerciseFieldSetMapper(): FieldSetMapper<ExerciseDto> {
        return ExerciseFieldSetMapper()
    }

    @Bean
    fun exerciseLineMapper(): LineMapper<ExerciseDto> {
        val lineMapper = DefaultLineMapper<ExerciseDto>()
        val lineTokenizer = DelimitedLineTokenizer()

        lineTokenizer.setNames("exerciseSeriesId", "id", "name", "description")
//        lineTokenizer.setIncludedFields(*intArrayOf(0, 1, 2, 3, 4))
        lineMapper.setLineTokenizer(lineTokenizer)
        lineMapper.setFieldSetMapper(exerciseFieldSetMapper())

        return lineMapper
    }

    @Bean
    fun exerciseWriter(dataSource: DataSource): JdbcBatchItemWriter<ExerciseDto> {
        return JdbcBatchItemWriterBuilder<ExerciseDto>()
            .itemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider<ExerciseDto>())
            .sql("INSERT INTO exercise (id, exercise_series_id, name, description) VALUES (:id,:exerciseSeriesId, :name, :description)")
            .dataSource(dataSource)
            .build()
    }
}