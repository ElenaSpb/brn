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
    @Autowired val resourceRepository: ResourceRepository
) {

    @PostConstruct
    fun loadData() {
    }
}