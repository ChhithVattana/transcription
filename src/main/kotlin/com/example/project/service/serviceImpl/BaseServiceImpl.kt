package com.example.project.service.serviceImpl

import com.example.project.service.BaseService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service

@Service
abstract class BaseServiceImpl<T : Any> : BaseService<T> {
    abstract fun getRepository(): JpaRepository<T, Long>
    abstract fun getSpecificationExecutor(): JpaSpecificationExecutor<T>

    override fun addNew(t: T): T = getRepository().save(t)

    override fun getByPage(page: Int, size: Int, q: String?): Page<T> {
        return getSpecificationExecutor().findAll({ root, query, cb ->
            val predicates = ArrayList<javax.persistence.criteria.Predicate>()
//            q?.let {
//                val name = cb.like(cb.upper(root.get("name")), "%${q.uppercase()}%")
//                predicates.add(cb.or(name))
//            }
            predicates.add(cb.equal(root.get<String>("status"), true))
            query.orderBy(cb.desc(root.get<String>("id")))
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page, size))
    }

//    override fun getEmployeeByPage(page: Int, size: Int, q: String?): Page<Employee> {
//        return employeeRepository.findAll({root, query, cb ->
//            val predicates = ArrayList<javax.persistence.criteria.Predicate>()
//            q?.let {
//                val firstname = cb.like(cb.upper(root.get("firstname")), "%${q.uppercase()}%")
//                val lastname = cb.like(cb.upper(root.get("lastname")), "%${q.uppercase()}%")
//                predicates.add(cb.or(firstname, lastname))
//            }
//            predicates.add(cb.equal(root.get<String>("status"),  true))
//            query.orderBy(cb.desc(root.get<String>("id")))
//            cb.and(*predicates.toTypedArray())
//        }, PageRequest.of(page, size))
//    }
}