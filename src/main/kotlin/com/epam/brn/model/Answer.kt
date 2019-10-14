package com.epam.brn.model

import javax.persistence.*

@Entity
data class Answer(
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
    val task: Task,
    val rightAnswer: Boolean,
    @Column(nullable = false)
    val answer: String
)