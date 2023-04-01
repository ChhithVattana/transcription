package com.example.project.model

import com.example.project.model.baseEntity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "tbl_payment")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    var paymentType: String? = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transactionId")
    var transactionId: Transaction? = null
) : BaseEntity()
