package com.epam.brn.batch.exerciseseries

import com.epam.brn.batch.JobCompletionNotificationListener
import com.epam.brn.constant.Batch.RESOURCE_EXERCISE_SERIES_INPUT_FILE
import com.epam.brn.dto.ExerciseSeriesDto
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
class ExerciseSeriesBatchConfiguration {

    @Autowired
    private lateinit var jobBuilderFactory: JobBuilderFactory

    @Autowired
    private lateinit var stepBuilderFactory: StepBuilderFactory

    @Value(RESOURCE_EXERCISE_SERIES_INPUT_FILE)
    lateinit var inputResource: Resource

    @Bean
    fun importExerciseSeriesJob(listener: JobCompletionNotificationListener, exerciseSeriesImportFirstStep: Step): Job {
        return jobBuilderFactory.get("importExerciseSeriesJob")
            .incrementer(RunIdIncrementer())
            .listener(listener)
            .flow(exerciseSeriesImportFirstStep)
            .end()
            .build()
    }

    @Bean
    fun exerciseSeriesImportFirstStep(
        exerciseSeriesWriter: JdbcBatchItemWriter<ExerciseSeriesDto>,
        exerciseSeriesItemProcessor: ItemProcessor<ExerciseSeriesDto, ExerciseSeriesDto>
    ): Step {
        return stepBuilderFactory.get("exerciseSeriesFirstStep")
            .chunk<ExerciseSeriesDto, ExerciseSeriesDto>(10)
            .reader(exerciseSeriesReader())
            .processor(exerciseSeriesItemProcessor)
            .writer(exerciseSeriesWriter)
            .build()
    }

    @Bean
    fun exerciseSeriesReader(): FlatFileItemReader<ExerciseSeriesDto> {
        val itemReader = FlatFileItemReader<ExerciseSeriesDto>()
        itemReader.setLineMapper(exerciseSeriesLineMapper())
        itemReader.setLinesToSkip(NumberUtils.INTEGER_ONE)
        itemReader.setResource(inputResource)
        return itemReader
    }

    @Bean
    fun exerciseSeriesFieldSetMapper(): FieldSetMapper<ExerciseSeriesDto> {
        return ExerciseSeriesFieldSetMapper()
    }

    @Bean
    fun exerciseSeriesLineMapper(): LineMapper<ExerciseSeriesDto> {
        val lineMapper = DefaultLineMapper<ExerciseSeriesDto>()
        val lineTokenizer = DelimitedLineTokenizer()

        lineTokenizer.setNames("exerciseGroupId", "id", "name", "description")
//        lineTokenizer.setIncludedFields(*intArrayOf(0, 1, 2, 3, 4))
        lineMapper.setLineTokenizer(lineTokenizer)
        lineMapper.setFieldSetMapper(exerciseSeriesFieldSetMapper())

        return lineMapper
    }

    @Bean
    fun exerciseSeriesWriter(dataSource: DataSource): JdbcBatchItemWriter<ExerciseSeriesDto> {
        return JdbcBatchItemWriterBuilder<ExerciseSeriesDto>()

            .itemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider<ExerciseSeriesDto>())
            .sql("INSERT INTO exercise_series (id, name, description,exercise_group_id) VALUES (:id, :name, :description, :exerciseGroup)")
            .dataSource(dataSource)
            .build()
    }

}