package com.example.project.model

import com.example.project.model.baseEntity.BaseEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "tbl_offering")
data class Offering(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    var wifi: Boolean? = false,
    var pool: Boolean? = false,
    var transportation: Boolean? = false,
    var breakfast: Boolean? = false,
    var launch: Boolean? = false,
    var dinner: Boolean? = false,
    var bathtub: Boolean? = false,
    var coffee: Boolean? = false,
    var extraBed: Boolean? = false,

    @ManyToMany(
        mappedBy = "offering", fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE]
    )
    @JsonIgnore
    var roomType: MutableList<RoomType>? = null,
) : BaseEntity()
