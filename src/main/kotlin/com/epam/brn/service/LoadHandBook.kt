package com.epam.brn.service

import com.epam.brn.repo.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class LoadHandBook(
    @Autowired val groupRepository: ExerciseGroupRepository,
    @Autowired val seriesRepository: ExerciseSeriesRepository,
    @Autowired val exerciseRepository: ExerciseRepository,
    @Autowired val resourceRepository: ResourceRepository,
    @Autowired val taskRepository: TaskRepository
) {

    @PostConstruct
    fun loadData() {


/*
        val group = ExerciseGroup(name = "речевые упражения", description = "речевые упражения")
        groupRepository.save(group)
        val series1 = ExerciseSeries(name = "распознование слов", description = "распознование слов", exerciseGroup = group)
        seriesRepository.save(series1)
        val series2 = ExerciseSeries(name = "диахоничкеское слушание", description = "диахоничкеское слушание", exerciseGroup = group)
        seriesRepository.save(series2)
        val exercise = Exercise(name = "First",description = "desc", level = 0, exerciseSeries = series1)
        exerciseRepository.save(exercise)
*/


    }
}