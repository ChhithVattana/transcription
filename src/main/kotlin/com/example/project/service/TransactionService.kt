package com.example.project.service

import com.example.project.model.Transaction
import org.springframework.data.domain.Page
import java.time.LocalDate

interface TransactionService : BaseService<Transaction> {
    fun getByDate(
        page: Int,
        size: Int,
        date: LocalDate?,
        q: String?
    ) : Page<Transaction>
}