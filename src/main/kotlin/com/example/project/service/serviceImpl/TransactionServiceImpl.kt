package com.example.project.service.serviceImpl

import com.example.project.model.CustomerInformation
import com.example.project.model.Transaction
import com.example.project.model.customModel.TransactionCustom
import com.example.project.repository.TransactionRepository
import com.example.project.service.TransactionService
import com.example.project.utils.Pagination
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
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
                    cb.like(
                        cb.upper(root.join<Transaction, CustomerInformation>("customerId").get("name")),
                        "%${q.uppercase()}%"
                    )
                val phonePredicate =
                    cb.like(
                        cb.upper(root.join<Transaction, CustomerInformation>("customerId").get("phone")),
                        "%${q.uppercase()}%"
                    )
                predicates.add(cb.or(payTypePredicate, namePredicate, phonePredicate))
            }
            predicates.add(cb.equal(root.get<Boolean>("status"), true))
            query.orderBy(cb.desc(root.get<Long>("id")))
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page, size))
    }

    override fun getCustomDetail(page: Int, size: Int, date: LocalDate?, q: String?): Page<TransactionCustom> {
        val r = transactionRepository.findAll()
        val detail = mutableListOf<TransactionCustom>()
        val pagination = Pagination<TransactionCustom>()
        r.forEach { tran ->
            tran.reservation!!.forEach { reserved ->
                reserved.roomId!!.forEach { roomDetail ->
                    val tmp = TransactionCustom()
                    tmp.id = roomDetail.id
                    tmp.fName = tran.customerId!!.name
                    tmp.phone = tran.customerId!!.phone
                    tmp.checkInOn = reserved.checkInOn
                    tmp.checkOutOn = reserved.checkOutOn
                    tmp.stayDuration = reserved.stayDuration
                    tmp.roomNo = roomDetail.roomNo
                    detail.add(tmp)
                }
            }
        }
        return PageImpl(pagination.paginate(page, size, detail), PageRequest.of(page, size), detail.size.toLong())
    }
}