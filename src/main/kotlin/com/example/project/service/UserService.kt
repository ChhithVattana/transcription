package com.example.project.service

import com.example.project.model.User
import com.example.project.model.customModel.UserCustom

interface UserService : BaseService<User> {
    fun addNew(userCustom: UserCustom): User
    fun update(id: Long, userCustom: UserCustom): User
    fun delete(id: Long): User
}