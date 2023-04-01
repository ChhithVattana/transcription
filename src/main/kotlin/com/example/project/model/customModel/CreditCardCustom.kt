package com.example.project.model.customModel

data class CreditCardCustom(
    var id: Long? = 0,
    var cardNo: String? = "",
    var expiry: String? = "",
    var cvv: String? = "",
    var user_id: Long? = 0,
)
