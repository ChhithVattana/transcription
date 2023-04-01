package com.example.project.service.serviceImpl

import com.example.project.exception.ResourceNotFoundException
import com.example.project.model.UserType
import com.example.project.repository.UserTypeRepository
import com.example.project.service.UserTypeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service

@Service
class UserTypeServiceImpl : BaseServiceImpl<UserType>(), UserTypeService {

    @Autowired
    lateinit var userTypeRepository: UserTypeRepository

    override fun getRepository(): JpaRepository<UserType, Long> {
        return userTypeRepository
    }

    override fun getSpecificationExecutor(): JpaSpecificationExecutor<UserType> {
        return userTypeRepository
    }

    override fun update(id: Long, userType: UserType): UserType {
        val r = userTypeRepository.findById(id).orElse(null)
            ?: throw ResourceNotFoundException("User type id: $id not found")
        r.name = userType.name
        return userTypeRepository.save(r)
    }

    override fun delete(id: Long): UserType {
        val r = userTypeRepository.findById(id).orElse(null)
            ?: throw ResourceNotFoundException("User type id: $id not found")
        r.status = false
        return userTypeRepository.save(r)
    }
}