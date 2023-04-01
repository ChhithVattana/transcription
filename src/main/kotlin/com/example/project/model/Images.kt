package com.example.project.model

import com.example.project.model.baseEntity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "tbl_images")
data class Images(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    @Column(name = "url", columnDefinition = "text")
    var url: String? = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_types_id")
    var roomTypeId: RoomType? = null,
) : BaseEntity()