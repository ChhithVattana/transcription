package com.example.project.controller

import com.example.project.model.customModel.CreditCardCustom
import com.example.project.service.CreditCardService
import com.example.project.utils.AppConstant
import com.example.project.utils.ResponseObjectMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstant.MAIN_ENDPOINT + "/creditCard")
class CreditCardController {

    @Autowired
    lateinit var creditCardService: CreditCardService

    @Autowired
    lateinit var responseObjectMap: ResponseObjectMap

    @GetMapping
    fun getAll(@RequestParam(required = false) page: Int, size: Int, q: String?): MutableMap<String, Any> {
        val r = creditCardService.getByPage(page, size, q)
        return responseObjectMap.respondObject(r.content, r.totalElements)
    }

    @PostMapping
    fun addNew(@RequestBody creditCardCustom: CreditCardCustom): MutableMap<String, Any> {
        val r = creditCardService.addNew(creditCardCustom)
        return responseObjectMap.respondObject(r)
    }

    @PutMapping("/{id}/update")
    fun update(@PathVariable id: Long, @RequestBody creditCardCustom: CreditCardCustom): MutableMap<String, Any> {
        val r = creditCardService.update(id, creditCardCustom)
        return responseObjectMap.respondObject(r)
    }

    @DeleteMapping
    fun delete(@RequestParam id: Long): MutableMap<String, Any> {
        val r = creditCardService.delete(id)
        return responseObjectMap.respondObject(r)
    }
}