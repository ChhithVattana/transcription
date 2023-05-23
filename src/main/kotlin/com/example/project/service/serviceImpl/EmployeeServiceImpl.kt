package com.example.project.service.serviceImpl

import com.example.project.exception.ResourceNotFoundException
import com.example.project.model.Employee
import com.example.project.repository.EmployeeRepository
import com.example.project.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service

@Service
class EmployeeServiceImpl : BaseServiceImpl<Employee>(), EmployeeService {

    @Autowired
    lateinit var employeeRepository: EmployeeRepository

    override fun getRepository(): JpaRepository<Employee, Long> {
        return employeeRepository
    }

    override fun getSpecificationExecutor(): JpaSpecificationExecutor<Employee> {
        return employeeRepository
    }

    override fun getAll(page: Int, size: Int, q: String?): Page<Employee> {
        return getSpecificationExecutor().findAll({ root, query, cb ->
            val predicates = ArrayList<javax.persistence.criteria.Predicate>()
            q?.let {
                val name = cb.like(cb.upper(root.get("name")), "%${q.uppercase()}%")
                val email = cb.like(cb.upper(root.get("email")), "%${q.uppercase()}%")
                val phone = cb.like(cb.upper(root.get("phone")), "%${q.uppercase()}%")
                val position = cb.like(cb.upper(root.get("position")), "%${q.uppercase()}%")
                predicates.add(cb.or(name, email, phone, position))
            }
            predicates.add(cb.equal(root.get<String>("status"), true))
            query.orderBy(cb.desc(root.get<String>("id")))
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page, size))
    }

    override fun update(id: Long, employee: Employee): Employee {
        val r = employeeRepository.findByIdAndStatusTrue(id)
            .orElseThrow { ResourceNotFoundException("employee $id is not found") }
        r.name = employee.name
        r.gender = employee.gender
        r.email = employee.email
        r.phone = employee.phone
        r.dob = employee.dob
        r.dateIn = employee.dateIn
        r.salary = employee.salary
        r.position = employee.position
        return employeeRepository.save(r)
    }

    override fun delete(id: Long): Employee {
        val r = employeeRepository.findByIdAndStatusTrue(id)
            .orElseThrow { ResourceNotFoundException("employee $id is not found") }
        r.status = false
        return employeeRepository.save(r)
    }
}