package com.example.project.repository

import com.example.project.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    fun findByIdAndStatusTrue(id: Long): Optional<Employee>
}