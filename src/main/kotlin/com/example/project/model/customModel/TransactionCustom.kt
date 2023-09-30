package com.example.project.model.customModel

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class TransactionCustom(
    var id: Long? = 0,
    var fName: String? = "",
    var phone: String? = "",
    var email: String? = "",
    @JsonFormat(pattern = "yyyy-MM-dd")
    var checkInOn: LocalDate? = null,
    @JsonFormat(pattern = "yyyy-MM-dd")
    var checkOutOn: LocalDate? = null,
    var stayDuration: Long? = 0,
    var roomNo: String? = "",
    var total: Double? = 0.0,
)