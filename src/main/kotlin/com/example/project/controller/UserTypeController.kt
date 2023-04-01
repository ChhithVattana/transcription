package com.example.project.controller

import com.example.project.model.UserType
import com.example.project.service.UserTypeService
import com.example.project.utils.AppConstant
import com.example.project.utils.ResponseObjectMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstant.MAIN_ENDPOINT + "/userType")
class UserTypeController {

    @Autowired
    lateinit var userTypeService: UserTypeService

    @Autowired
    lateinit var responseObjectMap: ResponseObjectMap

    @GetMapping
    fun getAllUserType(@RequestParam(required = false) page: Int, size: Int, q: String?): MutableMap<String, Any> {
        val r = userTypeService.getByPage(page, size, q)
        return responseObjectMap.respondObject(r.content, r.totalElements)
    }

    @PostMapping
    fun postUserType(@RequestBody userType: UserType): MutableMap<String, Any> {
        val r = userTypeService.addNew(userType)
        return responseObjectMap.respondObject(r)
    }

    @PutMapping("/{id}/update")
    fun update(@PathVariable id: Long, @RequestBody userType: UserType): MutableMap<String, Any> {
        val r = userTypeService.update(id, userType)
        return responseObjectMap.respondObject(r)
    }

    @DeleteMapping
    fun delete(@RequestParam id: Long): MutableMap<String, Any> {
        val r = userTypeService.delete(id)
        return responseObjectMap.respondObject(r)
    }
}