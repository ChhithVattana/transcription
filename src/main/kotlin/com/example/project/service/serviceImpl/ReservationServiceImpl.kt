package com.example.project.service.serviceImpl

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
    ): Page<Room> {
        val reserved = reservationRepository.findByCheckInOnBetweenOrderByIdDesc(checkInOn, checkOutOn)
        var roomTmp: MutableList<Room> = mutableListOf()
        val room = roomRepository.findAll { root, query, cb ->
            val predicates = ArrayList<javax.persistence.criteria.Predicate>()
            capacity?.let {
                predicates.add(
                    cb.greaterThanOrEqualTo(
                        root.join<Room, RoomType>("roomType_id")
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
                            root.join<Room, RoomType>("roomType_id")
                                .get("name")
                        ), "%${q.uppercase()}%"
                    )
                val bedTypePredicate =
                    cb.like(
                        cb.upper(
                            root.join<Room, RoomType>("roomType_id")
                                .get("bedType")
                        ), "%${q.uppercase()}%"
                    )
                predicates.add(cb.or(roomNoPredicate, roomNamePredicate, bedTypePredicate))
            }
            predicates.add(cb.equal(root.get<Boolean>("status"), true))
            query.orderBy(cb.desc(root.get<Long>("id")))
            cb.and(*predicates.toTypedArray())
        }
        reserved.forEach { detail ->
            detail.roomId!!.forEach {
                it.available = detail.checkOutOn == checkInOn
            }
            roomTmp = detail.roomId!!
        }
        room.forEach { detail ->
            roomTmp.forEach {
                if (detail.id == it.id) {
                    detail.available = it.available
                } else {
                    detail.available = true
                }
            }
        }
        // Use PageImpl(List<T> content, Pageable pageable, long total) to return Page<T>
        return PageImpl(room, PageRequest.of(page, size), room.size.toLong())
    }

    override fun getAllByDate(checkIn: LocalDate, checkOut: LocalDate): List<Reservation> {
        val reservation = reservationRepository.findByCheckInOnBetweenOrderByIdDesc(checkIn, checkOut)
        reservation.forEach { detail ->
            detail.roomId!!.forEach {
                it.available = false
            }
        }
        return reservation
    }

    // ChronoUnit.DAYS.between(V1, V2) to get day between V2-V1, Vn: LocalDate
    @Transactional
    override fun addNew(reservationCustom: ReservationCustom): Reservation {
        val reservation = Reservation()
        val roomTmp = roomRepository.findAllById(reservationCustom.roomId)
        val roomChecked = getAllByDate(reservationCustom.checkInOn!!, reservationCustom.checkOutOn!!)
        roomChecked.forEach { detail ->
            detail.roomId!!.forEach {
                it.available = false
                if(roomTmp.id == it.id) {
                    return reservation
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
        reservation.transactionId!!.totalPayment = reservation.stayDuration!! * roomTmp.roomType_id!!.price!!
        return reservationRepository.save(reservation)
    }
}