package com.epam.brn.batch.answer

import com.epam.brn.batch.JobCompletionNotificationListener
import com.epam.brn.constant.Batch.RESOURCE_ANSWER_INPUT_FILE
import com.epam.brn.dto.AnswerDto
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
class AnswerBatchConfiguration {

    @Autowired
    private lateinit var jobBuilderFactory: JobBuilderFactory

    @Autowired
    private lateinit var stepBuilderFactory: StepBuilderFactory

    @Value(RESOURCE_ANSWER_INPUT_FILE)
    lateinit var inputResource: Resource

    @Bean
    fun importAnswersJob(listener: JobCompletionNotificationListener, answerImportFirstStep: Step): Job {
        return jobBuilderFactory.get("importAnswerJob")
            .incrementer(RunIdIncrementer())
            .listener(listener)
            .flow(answerImportFirstStep)
            .end()
            .build()
    }

    @Bean
    fun answerImportFirstStep(
        answerWriter: JdbcBatchItemWriter<AnswerDto>,
        answerItemProcessor: ItemProcessor<AnswerDto, AnswerDto>
    ): Step {
        return stepBuilderFactory.get("answerFirstStep")
            .chunk<AnswerDto, AnswerDto>(10)
            .reader(answersReader())
            .processor(answerItemProcessor)
            .writer(answerWriter)
            .build()
    }

    @Bean
    fun answersReader(): FlatFileItemReader<AnswerDto> {
        val itemReader = FlatFileItemReader<AnswerDto>()
        itemReader.setLineMapper(answerLineMapper())
        itemReader.setLinesToSkip(NumberUtils.INTEGER_ONE)
        itemReader.setResource(inputResource)
        return itemReader
    }

    @Bean
    fun answerFieldSetMapper(): FieldSetMapper<AnswerDto> {
        return AnswerFieldSetMapper()
    }

    @Bean
    fun answerLineMapper(): LineMapper<AnswerDto> {
        val lineMapper = DefaultLineMapper<AnswerDto>()
        val lineTokenizer = DelimitedLineTokenizer()

        lineTokenizer.setNames("id", "taskId", "rightAnswer", "answer")
        lineMapper.setLineTokenizer(lineTokenizer)
        lineMapper.setFieldSetMapper(answerFieldSetMapper())

        return lineMapper
    }

    @Bean
    fun answerWriter(dataSource: DataSource): JdbcBatchItemWriter<AnswerDto> {
        return JdbcBatchItemWriterBuilder<AnswerDto>()
            .itemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider<AnswerDto>())
            .sql("INSERT INTO answer (id, task_id, right_answer, answer) VALUES (:id, :taskId, :rightAnswer, :answer)")
            .dataSource(dataSource)
            .build()
    }
}