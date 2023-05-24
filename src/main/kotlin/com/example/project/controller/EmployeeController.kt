package com.example.project.controller

import com.example.project.model.Employee
import com.example.project.service.EmployeeService
import com.example.project.utils.AppConstant
import com.example.project.utils.ResponseObjectMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstant.MAIN_ENDPOINT + "/employee")
class EmployeeController {

    @Autowired
    lateinit var employeeService: EmployeeService

    val response = ResponseObjectMap()

    @GetMapping
    fun getAll(page: Int, size: Int, q: String?): MutableMap<String, Any> {
        val r = employeeService.getAll(page, size, q)
        return response.respondObject(r.content, r.totalElements)
    }

    @PostMapping
    fun addNew(@RequestBody employee: Employee): MutableMap<String, Any> =
        response.respondObject(employeeService.addNew(employee))

    @PutMapping("/{id}/update")
    fun update(@PathVariable id: Long, @RequestBody employee: Employee): MutableMap<String, Any> =
        response.respondObject(employeeService.update(id, employee))

    @DeleteMapping("/delete")
    fun delete(@RequestParam id: Long): MutableMap<String, Any> =
        response.respondObject(employeeService.delete(id))
}