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
    val description: String? = "",
    @OneToMany(mappedBy = "exerciseGroup", cascade = [(CascadeType.ALL)])
    val series: MutableSet<Series> = HashSet()
) {
    fun toDto() = ExerciseGroupDto(
        id = id,
        name = name,
        description = description,
        series = series.map { series -> series.toDto() }.toMutableSet()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExerciseGroup

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "ExerciseGroup(id=$id, name='$name', description=$description)"
    }


}
