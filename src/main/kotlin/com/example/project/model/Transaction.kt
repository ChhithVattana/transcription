package com.example.project.model

import com.example.project.model.baseEntity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "tbl_transaction")
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    var customerId: CustomerInformation? = null,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "transactionId", cascade = [CascadeType.ALL])
    var payment: MutableList<Payment>? = null
) : BaseEntity()
