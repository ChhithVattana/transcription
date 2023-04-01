package com.example.project.model

import com.example.project.model.baseEntity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "tbl_user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    var name: String? = "",
    var gender: Boolean = true,
    @Column(name = "phone_number")
    var phoneNumber: String? = "",
    var email: String? = "",

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var userType: UserType? = null,
) : BaseEntity()
