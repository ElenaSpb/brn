package com.epam.brn.batch

import org.apache.logging.log4j.kotlin.logger
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class JobCompletionNotificationListener : JobExecutionListenerSupport() {
    private val log = logger()

    override fun beforeJob(jobExecution: JobExecution) {
        val jobStatus = jobExecution.status
        val jobId = jobExecution.jobId
        log.info("!!! JOB $jobId STARTED! Result: $jobStatus")

    }

    override fun afterJob(jobExecution: JobExecution) {
        val jobStatus = jobExecution.status
        val jobId = jobExecution.jobId
        log.info("!!! JOB $jobId FINISHED! Result: $jobStatus")
    }
}