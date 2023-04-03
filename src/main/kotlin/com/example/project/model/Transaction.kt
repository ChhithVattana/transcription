package com.example.project.model

import com.example.project.model.baseEntity.BaseEntity
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "tbl_transaction")
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var paymentType: String? = "",
    var totalPayment: Double? = 0.0,
    @Column(name = "date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    var date: LocalDate? = null,
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var customerId: CustomerInformation? = null,
    @OneToMany(mappedBy = "transactionId", fetch = FetchType.LAZY)
    var reservation: MutableList<Reservation>? = null,
) : BaseEntity()
