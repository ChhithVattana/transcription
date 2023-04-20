package com.example.project.service.serviceImpl

import com.example.project.model.Role
import com.example.project.repository.RoleRepository
import com.example.project.service.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl : BaseServiceImpl<Role>(), RoleService{

    @Autowired
    lateinit var roleRepository: RoleRepository

    override fun getRepository(): JpaRepository<Role, Long> {
        return roleRepository
    }

    override fun getSpecificationExecutor(): JpaSpecificationExecutor<Role> {
        return roleRepository
    }
}