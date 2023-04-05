package com.example.project.repository

import com.example.project.model.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {
    @Query("SELECT b FROM Reservation b WHERE b.checkInOn <= :endDate AND b.checkOutOn >= :startDate")
    fun findReservationsBetweenDates(
        @Param("startDate") startDate: LocalDate?,
        @Param("endDate") endDate: LocalDate?,
    ): List<Reservation>
}