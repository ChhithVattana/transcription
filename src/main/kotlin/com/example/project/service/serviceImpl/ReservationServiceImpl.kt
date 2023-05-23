package com.example.project.service.serviceImpl

import com.example.project.exception.ResourceNotAvailable
import com.example.project.exception.ResourceNotFoundException
import com.example.project.model.Reservation
import com.example.project.model.Room
import com.example.project.model.RoomType
import com.example.project.model.customModel.ReservationCustom
import com.example.project.repository.ReservationRepository
import com.example.project.repository.RoomRepository
import com.example.project.service.ReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.transaction.Transactional

@Service
class ReservationServiceImpl : BaseServiceImpl<Reservation>(), ReservationService {

    @Autowired
    lateinit var reservationRepository: ReservationRepository

    @Autowired
    lateinit var roomRepository: RoomRepository

    override fun getRepository(): JpaRepository<Reservation, Long> {
        return reservationRepository
    }

    override fun getSpecificationExecutor(): JpaSpecificationExecutor<Reservation> {
        return reservationRepository
    }

    /* check condition from date first then go to filter out the available room */
    override fun searchAvailable(
        page: Int,
        size: Int,
        checkInOn: LocalDate?,
        checkOutOn: LocalDate?,
        capacity: Int?,
        q: String?,
        isAvailable: Boolean?,
    ): Page<Room> {
        val reserved = reservationRepository.findReservationsBetweenDates(checkInOn, checkOutOn)
        if (reserved.isEmpty()) {
            val room = roomRepository.findAllByStatusTrue()
            room.forEach {
                it.available = true
            }
            roomRepository.saveAll(room)
        } else {
            val room = roomRepository.findAllByStatusTrue()
            val roomTmp: MutableList<Room> = mutableListOf()
            reserved.forEach { detail ->
                detail.roomId!!.forEach {
                    it.available = detail.checkOutOn == checkInOn
                    roomTmp.add(it)
                }
            }
            roomTmp.forEach {
                room.forEach { room ->
                    if (it.id == room.id) {
                        room.available = it.available
                    }
                }
            }
            roomRepository.saveAll(room)
        }
        val room = roomRepository.findAll { root, query, cb ->
            val predicates = ArrayList<javax.persistence.criteria.Predicate>()
            capacity?.let {
                predicates.add(
                    cb.greaterThanOrEqualTo(
                        root.join<Room, RoomType>("roomTypeId")
                            .get("capacity"), capacity
                    )
                )
            }
            q?.let {
                val roomNoPredicate =
                    cb.like(cb.upper(root.get("roomNo")), "%${q.uppercase()}%")
                val roomNamePredicate =
                    cb.like(
                        cb.upper(
                            root.join<Room, RoomType>("roomTypeId")
                                .get("name")
                        ), "%${q.uppercase()}%"
                    )
                val bedTypePredicate =
                    cb.like(
                        cb.upper(
                            root.join<Room, RoomType>("roomTypeId")
                                .get("bedType")
                        ), "%${q.uppercase()}%"
                    )
                predicates.add(cb.or(roomNoPredicate, roomNamePredicate, bedTypePredicate))
            }
            isAvailable?.let {
                predicates.add(cb.equal(root.get<Boolean>("available"), isAvailable))
            }
            predicates.add(cb.equal(root.get<Boolean>("status"), true))
            query.orderBy(cb.desc(root.get<Long>("id")))
            cb.and(*predicates.toTypedArray())
        }
        // Use PageImpl(List<T> content, Pageable pageable, long total) to return Page<T>
        return PageImpl(room, PageRequest.of(page, size), room.size.toLong())
    }

    override fun getAllByDate(checkIn: LocalDate, checkOut: LocalDate): List<Reservation> {
        val reservations = reservationRepository.findReservationsBetweenDates(checkIn, checkOut).apply {
            forEach { detail -> detail.roomId!!.forEach { room -> room.available = false } }
            reservationRepository.saveAll(this)
        }
        return reservations
    }

    // ChronoUnit.DAYS.between(V1, V2) to get day between V2-V1, Vn: LocalDate
    @Transactional
    override fun addNew(reservationCustom: ReservationCustom): Reservation {
        val reservation = Reservation()
        val roomTmp = roomRepository.findByIdAndStatusTrue(reservationCustom.roomId!!)
            .orElseThrow { ResourceNotFoundException("room id: ${reservationCustom.roomId} not found") }
        val roomChecked = getAllByDate(reservationCustom.checkInOn!!, reservationCustom.checkOutOn!!)
        roomChecked.forEach { detail ->
            detail.roomId!!.forEach {
                it.available = false
                if (roomTmp.id == it.id) {
                    throw ResourceNotAvailable("This items is not available yet")
                }
            }
        }
        reservation.checkInOn = reservationCustom.checkInOn
        reservation.checkOutOn = reservationCustom.checkOutOn
        reservation.stayDuration = ChronoUnit.DAYS.between(reservationCustom.checkInOn, reservationCustom.checkOutOn)
        reservation.specialRequests = reservationCustom.specialRequests
        reservation.roomId = mutableListOf(roomTmp)
        reservation.transactionId = reservationCustom.transactionId
        reservation.transactionId!!.date = LocalDate.now()
        reservation.transactionId!!.totalPayment = reservation.stayDuration!! * roomTmp.roomTypeId!!.price!!
        return reservationRepository.save(reservation)
    }

    @Transactional
    override fun addBooking(
        page: Int,
        size: Int,
        checkInOn: LocalDate?,
        checkOutOn: LocalDate?,
        capacity: Int?,
        q: String?,
        isAvailable: Boolean?,
        reservationCustom: ReservationCustom,
        noOfRoom: Int,
    ): Reservation {
        val reservation = Reservation()
        val r = searchAvailable(page, size, checkInOn, checkOutOn, capacity, q, isAvailable)
        val roomTmp: MutableList<Room> = mutableListOf()
        var count = 0
        r.forEach {
            if (count < noOfRoom) {
                roomTmp.add(it)
                count++
            } else {
                // exit loop
                return@forEach
            }
        }
        val price = roomTmp.first().roomTypeId!!.price
        reservation.checkInOn = reservationCustom.checkInOn
        reservation.checkOutOn = reservationCustom.checkOutOn
        reservation.stayDuration = ChronoUnit.DAYS.between(reservationCustom.checkInOn, reservationCustom.checkOutOn)
        reservation.specialRequests = reservationCustom.specialRequests
        reservation.roomId = roomTmp
        reservation.transactionId = reservationCustom.transactionId
        reservation.transactionId!!.date = LocalDate.now()
        reservation.transactionId!!.totalPayment = reservation.stayDuration!! * price!! * noOfRoom
        return reservationRepository.save(reservation)
    }
}