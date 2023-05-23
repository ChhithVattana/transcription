package com.example.project.repository

import com.example.project.model.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    fun findByUsernameAndStatusTrue(username: String): Optional<Account>
    fun findByIdAndStatusTrue(id: Long): Optional<Account>
}