package com.epam.brn.batch.task

import com.epam.brn.batch.JobCompletionNotificationListener
import com.epam.brn.constant.Batch.RESOURCE_TASK_INPUT_FILE
import com.epam.brn.dto.TaskDto
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
class TaskBatchConfiguration {

    @Autowired
    private lateinit var jobBuilderFactory: JobBuilderFactory

    @Autowired
    private lateinit var stepBuilderFactory: StepBuilderFactory

    @Value(RESOURCE_TASK_INPUT_FILE)
    lateinit var inputResource: Resource

    @Bean
    fun importTaskJob(listener: JobCompletionNotificationListener, taskImportFirstStep: Step): Job {
        return jobBuilderFactory.get("importTaskJob")
            .incrementer(RunIdIncrementer())
            .listener(listener)
            .flow(taskImportFirstStep)
            .end()
            .build()
    }

    @Bean
    fun taskImportFirstStep(
        taskWriter: JdbcBatchItemWriter<TaskDto>,
        taskItemProcessor: ItemProcessor<TaskDto, TaskDto>
    ): Step {
        return stepBuilderFactory.get("taskFirstStep")
            .chunk<TaskDto, TaskDto>(10)
            .reader(taskReader())
            .processor(taskItemProcessor)
            .writer(taskWriter)
            .build()
    }

    @Bean
    fun taskReader(): FlatFileItemReader<TaskDto> {
        val itemReader = FlatFileItemReader<TaskDto>()
        itemReader.setLineMapper(taskLineMapper())
        itemReader.setLinesToSkip(NumberUtils.INTEGER_ONE)
        itemReader.setResource(inputResource)
        return itemReader
    }

    @Bean
    fun taskFieldSetMapper(): FieldSetMapper<TaskDto> {
        return TaskFieldSetMapper()
    }

    @Bean
    fun taskLineMapper(): LineMapper<TaskDto> {
        val lineMapper = DefaultLineMapper<TaskDto>()
        val lineTokenizer = DelimitedLineTokenizer()

        lineTokenizer.setNames("id", "exerciseId", "name", "serialNumber")
//        lineTokenizer.setIncludedFields(*intArrayOf(0, 1, 2, 3, 4))
        lineMapper.setLineTokenizer(lineTokenizer)
        lineMapper.setFieldSetMapper(taskFieldSetMapper())

        return lineMapper
    }

    @Bean
    fun taskWriter(dataSource: DataSource): JdbcBatchItemWriter<TaskDto> {
        return JdbcBatchItemWriterBuilder<TaskDto>()
            .itemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider<TaskDto>())
            .sql("INSERT INTO task (id, exercise_id, name, serial_number) VALUES (:id, :exerciseId,:name, :serialNumber)")
            .dataSource(dataSource)
            .build()
    }
}