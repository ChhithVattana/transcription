package com.example.project.service

import com.example.project.model.CreditCard
import com.example.project.model.customModel.CreditCardCustom

interface CreditCardService : BaseService<CreditCard> {
    fun addNew(creditCardCustom: CreditCardCustom): CreditCard
    fun update(id: Long, creditCardCustom: CreditCardCustom): CreditCard
    fun delete(id: Long): CreditCard
}