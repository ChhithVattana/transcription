package com.example.project.controller

import com.example.project.model.Account
import com.example.project.service.AccountService
import com.example.project.utils.AppConstant
import com.example.project.utils.ResponseObjectMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstant.MAIN_ENDPOINT + "/account")
class AccountController {

    @Autowired
    lateinit var accountService: AccountService
    @Autowired
    lateinit var responseObjectMap: ResponseObjectMap

    @GetMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_GETALL')")
    fun getAll(@RequestParam(required = false) page: Int, size: Int, q: String?): MutableMap<String, Any> {
        val r = accountService.getByPage(page, size, q)
        return responseObjectMap.respondObject(r.content, r.totalElements)
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_INSERT')")
    fun addNew(@RequestBody account: Account): MutableMap<String, Any> {
        val r = accountService.addNew(account)
        return responseObjectMap.respondObject(r)
    }
}