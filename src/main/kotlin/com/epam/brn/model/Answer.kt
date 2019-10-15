package com.epam.brn.model

import com.epam.brn.dto.AnswerDto
import javax.persistence.*

@Entity
class Answer(
    @Id
    @GeneratedValue(generator = "answer_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
        name = "answer_id_seq",
        sequenceName = "answer_id_seq",
        allocationSize = 50
    )
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "task_id")
    val task: Task? = null,
    val rightAnswer: Boolean,
    @Column(nullable = false)
    val answer: String
){
    fun toDto() = AnswerDto(
        id = id,
        rightAnswer = rightAnswer,
        answer = answer,
        taskId = task?.id
    )

    override fun toString(): String {
        return "answer_entity"
    }
}