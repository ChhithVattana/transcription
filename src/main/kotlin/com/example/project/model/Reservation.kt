package com.example.project.model

import com.example.project.model.baseEntity.BaseEntity
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "tbl_reservation")
data class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    @Column(name = "check_in_on")
    @JsonFormat(pattern = "yyyy-MM-dd")
    var checkInOn: LocalDate? = null,
    @Column(name = "check_out_on")
    @JsonFormat(pattern = "yyyy-MM-dd")
    var checkOutOn: LocalDate? = null,
    var stayDuration: Long? = 0,
    @Column(name = "special_requests")
    var specialRequests: String? = "",

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "tbl_room_reservation",
        joinColumns = [JoinColumn(name = "reservation_id")],
        inverseJoinColumns = [JoinColumn(name = "room_id")]
    )
    var roomId: MutableList<Room>? = null,
) : BaseEntity()