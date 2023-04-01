package com.example.project.service.serviceImpl

import com.example.project.exception.ResourceNotFoundException
import com.example.project.model.CreditCard
import com.example.project.model.customModel.CreditCardCustom
import com.example.project.repository.CreditCardRepository
import com.example.project.repository.UserRepository
import com.example.project.service.CreditCardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service

@Service
class CreditCardServiceImpl : BaseServiceImpl<CreditCard>(), CreditCardService {

    @Autowired
    lateinit var creditCardRepository: CreditCardRepository

    @Autowired
    lateinit var userRepository: UserRepository

    override fun getRepository(): JpaRepository<CreditCard, Long> {
        return creditCardRepository
    }

    override fun getSpecificationExecutor(): JpaSpecificationExecutor<CreditCard> {
        return creditCardRepository
    }

    override fun addNew(creditCardCustom: CreditCardCustom): CreditCard {
        val creditCard = CreditCard()
        creditCard.cardNo = creditCardCustom.cardNo
        creditCard.cvv = creditCardCustom.cvv
        creditCard.expiry = creditCardCustom.expiry
        creditCard.user = userRepository.findAllById(creditCardCustom.user_id!!)
        return creditCardRepository.save(creditCard)
    }

    override fun update(id: Long, creditCardCustom: CreditCardCustom): CreditCard {
        val r = creditCardRepository.findById(id).orElse(null)
            ?: throw ResourceNotFoundException("Credit Card id: $id not found")
        r.cardNo = creditCardCustom.cardNo
        r.cvv = creditCardCustom.cvv
        r.expiry = creditCardCustom.expiry
        return creditCardRepository.save(r)
    }

    override fun delete(id: Long): CreditCard {
        val r = creditCardRepository.findById(id).orElse(null)
            ?: throw ResourceNotFoundException("Credit Card id: $id not found")
        r.status = false
        return creditCardRepository.save(r)
    }
}