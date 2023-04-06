package com.example.project.service

import com.example.project.model.CustomerInformation
import org.springframework.data.domain.Page

interface CustomerInformationService : BaseService<CustomerInformation> {
    fun getAll(page: Int, size: Int, q: String?): Page<CustomerInformation>
}