package com.example.project.service

import com.example.project.model.UserType

interface UserTypeService : BaseService<UserType> {
    fun update(id: Long, userType: UserType): UserType
    fun delete(id: Long): UserType
}