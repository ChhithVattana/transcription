package com.example.project.service.serviceImpl

import com.example.project.model.CustomerInformation
import com.example.project.repository.CustomerInformationRepository
import com.example.project.service.CustomerInformationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service

@Service
class CustomerInformationServiceImpl : BaseServiceImpl<CustomerInformation>(), CustomerInformationService {

    @Autowired
    lateinit var customerInformationRepository: CustomerInformationRepository

    override fun getRepository(): JpaRepository<CustomerInformation, Long> {
        return customerInformationRepository
    }

    override fun getSpecificationExecutor(): JpaSpecificationExecutor<CustomerInformation> {
        return customerInformationRepository
    }
}