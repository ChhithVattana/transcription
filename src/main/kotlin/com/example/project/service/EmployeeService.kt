package com.example.project.service

import com.example.project.model.Employee
import org.springframework.data.domain.Page

interface EmployeeService : BaseService<Employee> {
    fun getAll(page: Int, size: Int, q: String?): Page<Employee>
    fun update(id: Long, employee: Employee): Employee
    fun delete(id: Long): Employee
}