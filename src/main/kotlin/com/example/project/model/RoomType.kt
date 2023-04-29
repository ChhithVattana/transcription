package com.example.project.model

import com.example.project.model.baseEntity.BaseEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "tbl_room_type")
data class RoomType(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = 0,
    var name: String? = "",
    var price: Double? = 0.0,
    var bedType: String? = "",
    var roomSize: Double? = 0.0,
    @Column(name = "main_description", columnDefinition = "text")
    var mainDescription: String? = "",
    @Column(name = "sub_description", columnDefinition = "text")
    var subDescription: String? = "",
    @Column(name = "url", columnDefinition = "text")
    var url: String? = "",
    var floor: String? = "",
    var capacity: Int? = 0,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomTypeId")
    @JsonIgnore
    var room: MutableList<Room>? = null,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomTypeId")
    @JsonIgnore
    var images: MutableList<Images>? = null,
    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "tbl_service_offering",
        joinColumns = [JoinColumn(name = "roomType_id")],
        inverseJoinColumns = [JoinColumn(name = "offering_id")]
    )
    var offering: MutableList<Offering>? = null,
) : BaseEntity()
