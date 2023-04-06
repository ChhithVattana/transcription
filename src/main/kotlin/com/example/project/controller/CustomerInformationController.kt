package com.example.project.controller

import com.example.project.service.CustomerInformationService
import com.example.project.utils.AppConstant
import com.example.project.utils.ResponseObjectMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(AppConstant.MAIN_ENDPOINT + "/customer")
class CustomerInformationController {

    @Autowired
    lateinit var customerInformationService: CustomerInformationService
    @Autowired
    lateinit var responseObjectMap: ResponseObjectMap

    @GetMapping
    fun getAll(@RequestParam(required = false) page: Int, size: Int, q: String?): MutableMap<String, Any> {
        val r = customerInformationService.getAll(page, size, q)
        return responseObjectMap.respondObject(r.content, r.totalElements)
    }
}