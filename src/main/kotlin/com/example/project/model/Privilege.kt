package com.example.project.model

import javax.persistence.*

@Entity
@Table(name = "tbl_privilege")
data class Privilege(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    @Column(name = "name")
    var name: String = "",
    @Column(name = "description")
    var description: String? = "",
)
