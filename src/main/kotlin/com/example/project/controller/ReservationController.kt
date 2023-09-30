package com.example.project.controller

import com.example.project.model.customModel.ReservationCustom
import com.example.project.service.ReservationService
import com.example.project.utils.AppConstant
import com.example.project.utils.ResponseObjectMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping(AppConstant.MAIN_ENDPOINT + "/reservation")
class ReservationController {

    @Autowired
    lateinit var reservationService: ReservationService

    val response = ResponseObjectMap()

    @GetMapping
    fun getAllReservation(@RequestParam(required = false) page: Int, size: Int, q: String): MutableMap<String, Any> {
        val r = reservationService.getByPage(page, size, q)
        return response.respondObject(r.content, r.totalElements)
    }

    // convert the dat format by using @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to avoid error
    // Failed to convert value of type 'java.lang.String' to required type 'java.time.LocalDate';
    @GetMapping("/get-by-date")
    fun getByDate(
        @RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) checkIn: LocalDate,
        @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) checkOut: LocalDate,
    ): MutableMap<String, Any> =
        response.respondObject(reservationService.getAllByDate(checkIn, checkOut))

    @GetMapping("/get-by-list")
    fun getByList(
        @RequestParam(required = false) page: Int, size: Int,
        @RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) checkIn: LocalDate,
        @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) checkOut: LocalDate,
    ): MutableMap<String, Any> {
        val r = reservationService.getByList(page, size, checkIn, checkOut)
        return response.respondObject(r.content, r.totalElements)
    }

    @GetMapping("/search-available-list")
    fun getSpecific(
        @RequestParam(required = false) page: Int,
        size: Int,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) checkInOn: LocalDate?,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) checkOutOn: LocalDate?,
        capacity: Int?,
        q: String?,
        isAvailable: Boolean?,
    ): MutableMap<String, Any> {
        val r = reservationService.searchAvailableList(page, size, checkInOn, checkOutOn, capacity, q, isAvailable)
        return response.respondObject(r.content, r.totalElements)
    }

    @GetMapping("/search-available-all")
    fun getAllSpecific(
        @RequestParam(required = false) page: Int,
        size: Int,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) checkInOn: LocalDate?,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) checkOutOn: LocalDate?,
        capacity: Int?,
        q: String?,
        isAvailable: Boolean?,
    ): MutableMap<String, Any> {
        val r = reservationService.searchAvailableAll(checkInOn, checkOutOn, capacity, q, isAvailable)
        return response.respondObject(r)
    }

    @PostMapping
    fun addNew(@RequestBody reservationCustom: ReservationCustom): MutableMap<String, Any> =
        response.respondObject(reservationService.addNew(reservationCustom))

    @PostMapping("/add-booking/search-available")
    fun addBooking(
        @RequestParam(required = false)
        page: Int,
        size: Int,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        checkInOn: LocalDate?,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        checkOutOn: LocalDate?,
        noOfRoom: Int,
        capacity: Int?,
        q: String?,
        isAvailable: Boolean?,
        @RequestBody
        reservationCustom: ReservationCustom,
    ): MutableMap<String, Any> =
        response.respondObject(
            reservationService.addBooking(
                page,
                size,
                checkInOn,
                checkOutOn,
                capacity,
                q,
                isAvailable,
                reservationCustom,
                noOfRoom
            )
        )
}