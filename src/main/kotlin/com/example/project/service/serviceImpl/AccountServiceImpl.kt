package com.example.project.service.serviceImpl

import com.example.project.model.Account
import com.example.project.model.Role
import com.example.project.model.customModel.AccountCustom
import com.example.project.repository.AccountRepository
import com.example.project.repository.RoleRepository
import com.example.project.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl : BaseServiceImpl<Account>(), AccountService{

    @Autowired
    lateinit var accountRepository: AccountRepository
    @Autowired
    lateinit var roleRepository: RoleRepository

    override fun getRepository(): JpaRepository<Account, Long> {
        return accountRepository
    }

    override fun getSpecificationExecutor(): JpaSpecificationExecutor<Account> {
        return accountRepository
    }

    override fun createAcc(accountCustom: AccountCustom): Account {
        val passwordEncoder = BCryptPasswordEncoder()
        val account = Account()
        val role = roleRepository.findById(accountCustom.roleId!!).get()
        account.username = accountCustom.username
        account.password = passwordEncoder.encode(accountCustom.password)
        account.isEnabled = true
        account.roles = setOf(role)
        return accountRepository.save(account)
    }

    override fun delete(id: Long): Account {
        val r = accountRepository.findById(id).get()
        r.isEnabled = false
        r.status = false
        return accountRepository.save(r)
    }

    override fun update(id: Long, accountCustom: AccountCustom): Account {
        val passwordEncoder = BCryptPasswordEncoder()
        val r = accountRepository.findById(id).get()
        r.password = passwordEncoder.encode(accountCustom.password)
        return accountRepository.save(r)
    }
}