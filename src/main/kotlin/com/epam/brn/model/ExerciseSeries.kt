package com.epam.brn.model

import com.epam.brn.dto.ExerciseSeriesDto
import javax.persistence.*

@Entity
data class ExerciseSeries(
    @Id
    @GeneratedValue(generator = "exercise_series_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
        name = "exercise_series_id_seq",
        sequenceName = "exercise_series_id_seq",
        allocationSize = 50
    )
    val id: Long? = null,
    @Column(nullable = false)
    val name: String,
    @Column
    val description: String,
    @ManyToOne
    @JoinColumn(name = "exercise_group_id")
    val exerciseGroup: ExerciseGroup,
    @OneToMany(mappedBy = "exerciseSeries", cascade = [(CascadeType.ALL)])
    val exercises: MutableSet<Exercise> = HashSet()
)
{
        fun toDto() = ExerciseSeriesDto(
            id = id,
            name = name,
            description = description,
            exercises = exercises . map { exercise -> exercise.toDto() }.toMutableSet()
        )
}