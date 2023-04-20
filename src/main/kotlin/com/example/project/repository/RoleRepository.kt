package com.example.project.repository

import com.example.project.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long>, JpaSpecificationExecutor<Role>{
}