package com.example.project.repository

import com.example.project.model.RoomType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface RoomTypeRepository : JpaRepository<RoomType, Long>, JpaSpecificationExecutor<RoomType> {
    fun findAllById(id: Long?): RoomType
}