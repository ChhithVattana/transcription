package com.example.project.model.customModel

import com.example.project.model.Transaction
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class ReservationCustom(
    var id: Long? = 0,
    @JsonFormat(pattern = "yyyy-MM-dd")
    var checkInOn: LocalDate? = null,
    @JsonFormat(pattern = "yyyy-MM-dd")
    var checkOutOn: LocalDate? = null,
    var stayDuration: Long? = 0,
    var specialRequests: String? = "",
    var roomId: Long? = 0,
    var transactionId: Transaction? = null,
)
