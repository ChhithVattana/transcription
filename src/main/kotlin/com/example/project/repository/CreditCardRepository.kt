package com.example.project.repository

import com.example.project.model.CreditCard
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface CreditCardRepository : JpaRepository<CreditCard, Long>, JpaSpecificationExecutor<CreditCard> {
}