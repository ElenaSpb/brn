package com.epam.brn.model

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Column

@Entity
@Table(name = "USER_DETAILS")
data class UserDetails @JvmOverloads constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = true)
    val email: String? = null,
    @Column(nullable = true)
    val phone: String? = null,
    @Column(nullable = true)
    val password: String? = null
//    @OneToMany(cascade = [CascadeType.ALL])
//    val phoneNumbers: List<PhoneNumber>? = null
)