package com.epam.brn.batch.excercisegroup

import com.epam.brn.batch.JobCompletionNotificationListener
import com.epam.brn.constant.Batch.RESOURCE_EXERCISE_GROUP_INPUT_FILE
import com.epam.brn.dto.ExerciseGroupDto
import org.apache.commons.lang3.math.NumberUtils
import org.apache.logging.log4j.kotlin.logger
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
class ExerciseGroupBatchConfiguration {

    private val log = logger()

    @Autowired
    private lateinit var jobBuilderFactory: JobBuilderFactory

    @Autowired
    private lateinit var stepBuilderFactory: StepBuilderFactory

    @Value(RESOURCE_EXERCISE_GROUP_INPUT_FILE)
    lateinit var inputResource: Resource

    @Bean
    fun importExerciseGroupJob(listener: JobCompletionNotificationListener, exerciseGroupImportFirstStep: Step): Job {
        return jobBuilderFactory.get("importExerciseGroupJob")
            .incrementer(RunIdIncrementer())
            .listener(listener)
            .flow(exerciseGroupImportFirstStep)
            .end()
            .build()
    }

    @Bean
    fun exerciseGroupImportFirstStep(
        exerciseGroupWriter: JdbcBatchItemWriter<ExerciseGroupDto>,
        exerciseGroupItemProcessor: ItemProcessor<ExerciseGroupDto, ExerciseGroupDto>
    ): Step {
        return stepBuilderFactory.get("exerciseGroupFirstStep")
            .chunk<ExerciseGroupDto, ExerciseGroupDto>(10)
            .reader(exerciseGroupReader())
            .processor(exerciseGroupItemProcessor)
            .writer(exerciseGroupWriter)
            .build()
    }

    @Bean
    fun exerciseGroupReader(): FlatFileItemReader<ExerciseGroupDto> {
        val itemReader = FlatFileItemReader<ExerciseGroupDto>()
        itemReader.setLineMapper(exerciseGroupLineMapper())
        itemReader.setLinesToSkip(NumberUtils.INTEGER_ONE)
        itemReader.setResource(inputResource)
        return itemReader
    }

    @Bean
    fun exerciseGroupFieldSetMapper(): FieldSetMapper<ExerciseGroupDto> {
        return ExerciseGroupFieldSetMapper()
    }

    @Bean
    fun exerciseGroupLineMapper(): LineMapper<ExerciseGroupDto> {
        val lineMapper = DefaultLineMapper<ExerciseGroupDto>()
        val lineTokenizer = DelimitedLineTokenizer()

        lineTokenizer.setNames("id", "name", "description")
//        lineTokenizer.setIncludedFields(*intArrayOf(0, 1, 2, 3, 4))
        lineMapper.setLineTokenizer(lineTokenizer)
        lineMapper.setFieldSetMapper(exerciseGroupFieldSetMapper())

        return lineMapper
    }

    @Bean
    fun exerciseGroupWriter(dataSource: DataSource): JdbcBatchItemWriter<ExerciseGroupDto> {
        return JdbcBatchItemWriterBuilder<ExerciseGroupDto>()
            .itemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider<ExerciseGroupDto>())
            .sql("INSERT INTO exercise_group (id, name, description) VALUES (:id, :name, :description)")
            .dataSource(dataSource)
            .build()
    }

}