package com.example.project.service.serviceImpl

import com.example.project.exception.ResourceNotFoundException
import com.example.project.model.User
import com.example.project.model.customModel.UserCustom
import com.example.project.repository.UserRepository
import com.example.project.repository.UserTypeRepository
import com.example.project.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : BaseServiceImpl<User>(), UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userTypeRepository: UserTypeRepository

    override fun getRepository(): JpaRepository<User, Long> {
        return userRepository
    }

    override fun getSpecificationExecutor(): JpaSpecificationExecutor<User> {
        return userRepository
    }

    override fun addNew(userCustom: UserCustom): User {
        val user = User()
        val r = userTypeRepository.findAllById(userCustom.user_type_id)
        user.name = userCustom.name
        user.gender = userCustom.gender
        user.phoneNumber = userCustom.phoneNumber
        user.email = userCustom.email
        user.userType = r
        return userRepository.save(user)
    }

    override fun update(id: Long, userCustom: UserCustom): User {
        val r = userRepository.findById(id).orElse(null)
            ?: throw ResourceNotFoundException("User id: $id not found")
        r.name = userCustom.name
        r.gender = userCustom.gender
        r.email = userCustom.email
        r.phoneNumber = userCustom.phoneNumber
        r.userType = userTypeRepository.findAllById(userCustom.user_type_id)
        return userRepository.save(r)
    }

    override fun delete(id: Long): User {
        val r = userRepository.findById(id).orElse(null)
            ?: throw ResourceNotFoundException("User id: $id not found")
        r.status = false
        return userRepository.save(r)
    }
}