package com.example.project.model.customModel

data class AccountCustom(
    var id: Long? = 0,
    var username: String? = "",
    var password: String? = "",
    var isEnabled: Boolean? = true,
    var roleId: Long? = 0,
)