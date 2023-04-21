package com.example.project.service

import com.example.project.model.Account
import com.example.project.model.customModel.AccountCustom

interface AccountService : BaseService<Account> {
    fun createAcc(accountCustom: AccountCustom): Account
    fun delete(id: Long): Account
    fun update(id: Long, accountCustom: AccountCustom): Account
}