package com.example.project.service

import com.example.project.model.Room
import com.example.project.model.customModel.RoomCustom
import org.springframework.data.domain.Page

interface RoomService : BaseService<Room> {
    fun getCustomRoom(page: Int, size: Int): Page<RoomCustom>
    fun getRoomById(id: Long): Room
    fun addNew(roomCustom: RoomCustom): Room
    fun update(id: Long, roomCustom: RoomCustom): Room
    fun delete(id: Long): Room
}