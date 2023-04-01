package com.example.project.service.serviceImpl

import com.example.project.model.Payment
import com.example.project.repository.PaymentRepository
import com.example.project.service.PaymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service

@Service
class PaymentServiceImpl : BaseServiceImpl<Payment>(), PaymentService {

    @Autowired
    lateinit var paymentRepository: PaymentRepository

    override fun getRepository(): JpaRepository<Payment, Long> {
        return paymentRepository
    }

    override fun getSpecificationExecutor(): JpaSpecificationExecutor<Payment> {
        return paymentRepository
    }
}