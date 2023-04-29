package com.example.project.model

import com.example.project.model.baseEntity.BaseEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "tbl_room")
data class Room(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = 0,
    @Column(name = "room_no")
    var roomNo: String? = "",
    @Column(name = "is_available")
    var available: Boolean? = true,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "roomType_id")
    var roomTypeId: RoomType? = null,

    @ManyToMany(
        fetch = FetchType.LAZY, mappedBy = "roomId",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE]
    )
    @JsonIgnore
    var reservation: MutableList<Reservation>? = null,
) : BaseEntity()
