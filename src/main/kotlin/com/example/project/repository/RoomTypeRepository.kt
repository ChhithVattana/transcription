package com.example.project.repository

import com.example.project.model.RoomType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomTypeRepository : JpaRepository<RoomType, Long>, JpaSpecificationExecutor<RoomType> {
    fun findByIdAndStatusTrue(id: Long): Optional<RoomType>
}