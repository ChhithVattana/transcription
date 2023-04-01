package com.example.project.service.serviceImpl

import com.example.project.model.Transaction
import com.example.project.repository.TransactionRepository
import com.example.project.service.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl : BaseServiceImpl<Transaction>(), TransactionService {

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    override fun getRepository(): JpaRepository<Transaction, Long> {
        return transactionRepository
    }

    override fun getSpecificationExecutor(): JpaSpecificationExecutor<Transaction> {
        return transactionRepository
    }
}