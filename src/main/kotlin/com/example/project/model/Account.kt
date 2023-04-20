package com.example.project.model

import com.example.project.model.baseEntity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "tbl_account")
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    @Column(unique = true, name = "username")
    var username: String? = "",
    @Column(name = "password")
    var password: String? = "",
    @Column(name = "enabled")
    var isEnabled: Boolean? = true,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var roles: Set<Role>? = null,
) : BaseEntity()
