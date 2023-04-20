package com.example.project.service.serviceImpl

import com.example.project.model.Account
import com.example.project.repository.AccountRepository
import com.example.project.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl : BaseServiceImpl<Account>(), AccountService{

    @Autowired
    lateinit var accountRepository: AccountRepository

    override fun getRepository(): JpaRepository<Account, Long> {
        return accountRepository
    }

    override fun getSpecificationExecutor(): JpaSpecificationExecutor<Account> {
        return accountRepository
    }
}