package com.epam.brn.model

import com.epam.brn.dto.ExerciseGroupDto
import javax.persistence.*

@Entity
data class ExerciseGroup(
    @Id
    @GeneratedValue(generator = "exercise_group_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
        name = "exercise_group_id_seq",
        sequenceName = "exercise_group_id_seq",
        allocationSize = 50
    )
    val id: Long? = null,
    @Column(nullable = false)
    val name: String,
    @Column
    val description: String? ="",
    @OneToMany(mappedBy = "exerciseGroup", cascade = [(CascadeType.ALL)])
    val exerciseSeries: MutableSet<ExerciseSeries> = HashSet()
){
    fun toDto() = ExerciseGroupDto(
        id = id,
        name = name,
        description = description,
        exercisesSeries = exerciseSeries.map { series -> series.toDto() }.toMutableSet()
    )
}
