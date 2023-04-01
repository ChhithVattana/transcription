package com.example.project.repository

import com.example.project.model.UserType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface UserTypeRepository : JpaRepository<UserType, Long>, JpaSpecificationExecutor<UserType> {
    fun findAllById(id: Long?): UserType
}