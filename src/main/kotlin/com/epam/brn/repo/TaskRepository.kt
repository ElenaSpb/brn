package com.epam.brn.repo

import com.epam.brn.model.Task
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : CrudRepository<Task, Long> {

    fun findByExerciseIdLike(exerciseId: String): List<Task>
}