package com.example.project.repository

import com.example.project.model.Room
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface RoomRepository : JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {
    fun findAllByStatusTrueOrderByIdDesc(): List<Room>
    fun findAllById(id: Long?): Room
}