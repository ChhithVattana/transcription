package com.example.project.model

import com.example.project.model.baseEntity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "tbl_credit_card")
data class CreditCard(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    @Column(name = "card_no")
    var cardNo: String? = "",
    var expiry: String? = "",
    var cvv: String? = "",

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var user: User? = null,
) : BaseEntity()
