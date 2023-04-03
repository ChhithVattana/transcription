package com.example.project.service.serviceImpl

import com.example.project.model.CustomerInformation
import com.example.project.model.Transaction
import com.example.project.repository.TransactionRepository
import com.example.project.service.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service
import java.time.LocalDate

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

    override fun getByDate(page: Int, size: Int, date: LocalDate?, q: String?): Page<Transaction> {
        return getSpecificationExecutor().findAll({ root, query, cb ->
            val predicates = ArrayList<javax.persistence.criteria.Predicate>()
            date?.let {
                predicates.add(cb.equal(root.get<LocalDate>("date"), it))
            }
            q?.let {
                val payTypePredicate = cb.like(cb.upper(root.get("paymentType")), "%${q.uppercase()}%")
                val namePredicate =
                    cb.like(cb.upper(root.join<Transaction, CustomerInformation>("customerId").get<String>("name")),
                        "%${q.uppercase()}%")
                predicates.add(cb.or(payTypePredicate, namePredicate))
            }
            predicates.add(cb.equal(root.get<Boolean>("status"), true))
            query.orderBy(cb.desc(root.get<Long>("id")))
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page, size))
    }
}