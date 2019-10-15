package com.epam.brn.model

import com.epam.brn.dto.ExerciseDto
import javax.persistence.*

@Entity
data class Exercise(
    @Id
    @GeneratedValue(generator = "exercise_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
        name = "exercise_id_seq",
        sequenceName = "exercise_id_seq",
        allocationSize = 50
    )
    val id: Long? = null,
    val name: String,
    val description: String? = "",
    val level: Short? = 0,
    @ManyToOne
    @JoinColumn(name = "exercise_series_id")
    var exerciseSeries: ExerciseSeries,
    @OneToMany(mappedBy = "exercise")
    val tasks: MutableSet<Task> = HashSet()
){
    fun toDto() = ExerciseDto(
        id = id,
        name = name,
        description = description,
        tasks = tasks.map { task -> task.toDto() }.toMutableSet()
    )

}