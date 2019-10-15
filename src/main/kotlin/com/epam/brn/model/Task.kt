package com.epam.brn.model

import com.epam.brn.dto.TaskDto
import javax.persistence.*


@Entity
data class Task(
    @Id
    @GeneratedValue(generator = "task_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
        name = "task_id_seq",
        sequenceName = "task_id_seq",
        allocationSize = 50
    )
    val id: Long? = null,
    val name: String? = "",
    val serialNumber: Int? = 0,
    @ManyToOne
    @JoinColumn(name = "exercise_id")
    val exercise: Exercise? = null,
    @OneToOne(cascade = [(CascadeType.ALL)], optional = true)
    @JoinColumn(name = "resource_id")
    val resourceId: Resource? = null,
    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "task", orphanRemoval = true)
    val arrayAnswers: MutableSet<Answer> = HashSet()
){
    fun toDto() = TaskDto(
        id = id,
        name = name,
        serialNumber = serialNumber,
        resource = resourceId?.toDto(),
        arrayAnswers = arrayAnswers.map { answer -> answer.toDto() }.toMutableSet()
    )
}