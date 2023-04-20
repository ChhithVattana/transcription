package com.example.project.controller

import com.example.project.service.RoleService
import com.example.project.utils.AppConstant
import com.example.project.utils.ResponseObjectMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(AppConstant.MAIN_ENDPOINT + "/role")
class RoleController {

    @Autowired
    lateinit var roleService: RoleService
    @Autowired
    lateinit var responseObjectMap: ResponseObjectMap

    @GetMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_GETALL')")
    fun getAll(@RequestParam(required = false) page: Int, size: Int, q: String?): MutableMap<String, Any> {
        val r = roleService.getByPage(page, size, q)
        return responseObjectMap.respondObject(r.content, r.totalElements)
    }
}