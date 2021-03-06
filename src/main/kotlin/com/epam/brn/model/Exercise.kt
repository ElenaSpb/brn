package com.epam.brn.model

import com.epam.brn.dto.ExerciseDto
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.SequenceGenerator
import javax.persistence.CascadeType
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

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
    var series: Series,
    @OneToMany(mappedBy = "exercise", cascade = [CascadeType.ALL])
    val tasks: MutableSet<Task> = HashSet()
) {
    fun toDto() = ExerciseDto(
        id = id,
        name = name,
        description = description,
        tasks = tasks.map { task -> task.toDto() }.toMutableSet()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Exercise

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (level != other.level) return false
        if (series != other.series) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (level ?: 0)
        result = 31 * result + series.hashCode()
        return result
    }

    override fun toString(): String {
        return "Exercise(id=$id, name='$name', description=$description, level=$level)"
    }
}