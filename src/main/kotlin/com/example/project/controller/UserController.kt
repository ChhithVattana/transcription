package com.example.project.controller

import com.example.project.model.customModel.UserCustom
import com.example.project.service.UserService
import com.example.project.utils.AppConstant
import com.example.project.utils.ResponseObjectMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstant.MAIN_ENDPOINT + "/user")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var responseObjectMap: ResponseObjectMap

    @GetMapping
    fun getAllUser(@RequestParam(required = false) page: Int, size: Int, q: String?): MutableMap<String, Any> {
        val r = userService.getByPage(page, size, q)
        return responseObjectMap.respondObject(r.content, r.totalElements)
    }

    @PostMapping
    fun postUser(@RequestBody userCustom: UserCustom): MutableMap<String, Any> {
        val r = userService.addNew(userCustom)
        return responseObjectMap.respondObject(r)
    }

    @PutMapping("/{id}/update")
    fun update(@PathVariable id: Long, @RequestBody userCustom: UserCustom): MutableMap<String, Any> {
        val r = userService.update(id, userCustom)
        return responseObjectMap.respondObject(r)
    }

    @DeleteMapping
    fun delete(@RequestParam id: Long): MutableMap<String, Any> {
        val r = userService.delete(id)
        return responseObjectMap.respondObject(r)
    }
}