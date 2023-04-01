package com.example.project.repository

import com.example.project.model.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {
    fun findByCheckInOnBetweenOrderByIdDesc(checkIn: LocalDate?, checkOut: LocalDate?): List<Reservation>
}