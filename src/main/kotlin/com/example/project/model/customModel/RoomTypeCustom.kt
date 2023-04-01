package com.example.project.model.customModel

import com.example.project.model.Offering

data class RoomTypeCustom(
    var id: Long? = 0,
    var name: String? = "",
    var price: Double? = 0.0,
    var bedType: String? = "",
    var roomSize: Double? = 0.0,
    var mainDescription: String? = "",
    var subDescription: String? = "",
    var url: String? = "",
    var capacity: Int? = 0,
    var floor: String? = "",
    var offering: MutableList<Offering>? = null,
)
