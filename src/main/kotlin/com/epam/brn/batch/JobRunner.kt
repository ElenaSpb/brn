package com.epam.brn.batch

import com.epam.brn.constant.Batch.CLASSPATH_PREFIX_INPUT_DATA
import com.epam.brn.constant.Batch.PATH_TO_PRECESSED_RESOURCES
import com.epam.brn.constant.Batch.PATH_TO_RESOURCES
import org.apache.commons.io.FileUtils
import org.apache.logging.log4j.kotlin.logger
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ResourceLoader
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class JobRunner {

    private val log = logger()

    @Autowired
    private lateinit var jobLauncher: JobLauncher

    @Autowired
    private lateinit var resourceLoader: ResourceLoader

    @Autowired
    private lateinit var sourcesWithJobs: LinkedHashMap<String, Job>

    @Scheduled(cron = "\${cron.expression.input.data.upload}")
    fun perform() {
        val jobId = System.currentTimeMillis().toString()

        val successfullyProcessedResources = HashSet<String>()
        sourcesWithJobs.forEach { (fileName, job) ->

            val currentResource = resourceLoader.getResource(CLASSPATH_PREFIX_INPUT_DATA + fileName)
            if (currentResource.exists()) {
                val filename = currentResource.filename
                log.info("ExerciseGroupJob Start for file $filename")
                val paramExerciseGroupJob =
                    JobParametersBuilder().addString("JobID", "$filename $jobId").toJobParameters()
                val executionExerciseGroupJob = jobLauncher.run(job, paramExerciseGroupJob)

                val status = executionExerciseGroupJob.status
                if (!status.isUnsuccessful) {
                    successfullyProcessedResources.add(fileName)
                }
            } else {
                log.debug("No files for loading...")
            }
        }

        successfullyProcessedResources.stream()
            .forEach { fileName ->
                val oldPath = PATH_TO_RESOURCES + fileName
                val newPath = PATH_TO_PRECESSED_RESOURCES + jobId + "_" + fileName
                FileUtils.moveFile(
                    FileUtils.getFile(oldPath),
                    FileUtils.getFile(newPath)
                )
                log.info("File $oldPath was successfully processed and moved to $newPath")
            }
    }
}