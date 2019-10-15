package com.epam.brn.model

import com.epam.brn.dto.ResourceDto
import javax.persistence.*

@Entity
data class Resource(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val audioFileId: String,
    @Column(nullable = false)
    val word: String,
    @Column(nullable = false)
    val pictureId: String,
    @Column(nullable = false)
    val soundsCount: Int,
    @OneToOne(optional = false)
    val task: Task
){
    fun toDto() = ResourceDto(
        id = id,
        audioFileId = audioFileId,
        word = word,
        pictureId = pictureId,
        soundsCount = soundsCount
    )
}