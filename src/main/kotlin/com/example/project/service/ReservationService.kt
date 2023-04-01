package com.example.project.service

import com.example.project.model.Reservation
import com.example.project.model.Room
import com.example.project.model.customModel.ReservationCustom
import org.springframework.data.domain.Page
import java.time.LocalDate

interface ReservationService : BaseService<Reservation> {
    fun getAllByDate(checkIn: LocalDate, checkOut: LocalDate): List<Reservation>
    fun searchAvailable(
        page: Int,
        size: Int,
        checkInOn: LocalDate?,
        checkOutOn: LocalDate?,
        capacity: Int?,
        q: String?
    ): Page<Room>

    fun addNew(reservationCustom: ReservationCustom): Reservation
}