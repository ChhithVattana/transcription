package com.example.project.controller

import com.example.project.service.TransactionService
import com.example.project.utils.AppConstant
import com.example.project.utils.ResponseObjectMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping(AppConstant.MAIN_ENDPOINT + "/transaction")
class TransactionController {
    @Autowired
    lateinit var transactionService: TransactionService

    @Autowired
    lateinit var responseObjectMap: ResponseObjectMap

    @GetMapping
    fun getByDate(
        @RequestParam(required = false)
        page: Int,
        size: Int,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate?,
        q: String?,
    ): MutableMap<String, Any> {
        val r = transactionService.getByDate(page, size, date, q)
        return responseObjectMap.respondObject(r.content, r.totalElements)
    }
}