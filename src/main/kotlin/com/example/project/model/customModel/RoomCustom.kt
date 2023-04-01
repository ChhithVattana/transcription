package com.example.project.model.customModel

data class RoomCustom(
    var id: Long? = 0,
    var roomNo: String? = "",
    var available: Boolean? = true,
    var roomType_id: Long? = 0,
)