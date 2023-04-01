package com.example.project.model.customModel

data class UserCustom(
    var id: Long? = 0,
    var name: String? = "",
    var gender: Boolean = true,
    var phoneNumber: String? = "",
    var email: String? = "",
    var user_type_id: Long? = 0,
)
