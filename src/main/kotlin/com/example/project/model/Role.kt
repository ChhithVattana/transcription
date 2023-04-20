package com.example.project.model

import com.example.project.model.baseEntity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "tbl_role")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    @Column(name = "name")
    var name: String? = "",
    @Column(name = "description")
    var description: String? = "",

    @ManyToMany(fetch = FetchType.LAZY)
    var privileges: Set<Privilege> = emptySet()
) : BaseEntity()
