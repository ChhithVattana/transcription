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
    @Autowired
    lateinit var responseObjectMap: ResponseObjectMap

    @GetMapping
    fun getAll(page: Int, size: Int, q: String?): MutableMap<String, Any> {
        val r = employeeService.getAll(page, size, q)
        return responseObjectMap.respondObject(r.content, r.totalElements)
    }

    @PostMapping
    fun addNew(@RequestBody employee: Employee): MutableMap<String, Any> {
        val r = employeeService.addNew(employee)
        return responseObjectMap.respondObject(r)
    }

    @PutMapping("/{id}/update")
    fun update(@PathVariable id: Long, @RequestBody employee: Employee): MutableMap<String, Any> {
        val r = employeeService.update(id, employee)
        return responseObjectMap.respondObject(r)
    }

    @DeleteMapping("/delete")
    fun delete(@RequestParam id: Long): MutableMap<String, Any> {
        val r = employeeService.delete(id)
        return responseObjectMap.respondObject(r)
    }
}