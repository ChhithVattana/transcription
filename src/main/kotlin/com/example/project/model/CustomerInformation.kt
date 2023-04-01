package com.example.project.model

import com.example.project.model.baseEntity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "tbl_customer_information")
data class CustomerInformation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    var name: String? = "",
    var phoneNumber: String? = "",
    var email: String? = "",

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "customerId")
    var transaction: MutableList<Transaction>? = null
) : BaseEntity()
