package com.example.project.service.serviceImpl

import com.example.project.model.CustomerInformation
import com.example.project.repository.CustomerInformationRepository
import com.example.project.service.CustomerInformationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service

@Service
class CustomerInformationServiceImpl : BaseServiceImpl<CustomerInformation>(), CustomerInformationService {

    @Autowired
    lateinit var customerInformationRepository: CustomerInformationRepository

    override fun getRepository(): JpaRepository<CustomerInformation, Long> {
        return customerInformationRepository
    }

    override fun getSpecificationExecutor(): JpaSpecificationExecutor<CustomerInformation> {
        return customerInformationRepository
    }

    override fun getAll(page: Int, size: Int, q: String?): Page<CustomerInformation> {
        return getSpecificationExecutor().findAll({ root, query, cb ->
            val predicates = ArrayList<javax.persistence.criteria.Predicate>()
            q?.let {
                val name = cb.like(cb.upper(root.get("name")), "%${q.uppercase()}%")
                val phone = cb.like(cb.upper(root.get("phone")), "%${q.uppercase()}%")
                val email = cb.like(cb.upper(root.get("email")), "%${q.uppercase()}%")
                predicates.add(cb.or(name, phone, email))
            }
            predicates.add(cb.equal(root.get<String>("status"), true))
            query.orderBy(cb.desc(root.get<String>("id")))
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page, size))
    }
}