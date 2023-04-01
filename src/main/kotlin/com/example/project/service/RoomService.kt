package com.example.project.service

import com.example.project.model.Room
import com.example.project.model.customModel.RoomCustom

interface RoomService : BaseService<Room> {
    fun getCustomRoom(): List<RoomCustom>
    fun getRoomById(id: Long): Room
    fun addNew(roomCustom: RoomCustom): Room
    fun update(id: Long, roomCustom: RoomCustom): Room
    fun delete(id: Long): Room
}