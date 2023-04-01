package com.example.project.repository

import com.example.project.model.CustomerInformation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface CustomerInformationRepository : JpaRepository<CustomerInformation, Long>, JpaSpecificationExecutor<CustomerInformation>{
}