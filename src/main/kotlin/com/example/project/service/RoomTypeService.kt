package com.example.project.service

import com.example.project.model.RoomType
import com.example.project.model.customModel.RoomTypeCustom

interface RoomTypeService : BaseService<RoomType> {
    fun update(id: Long, roomTypeCustom: RoomTypeCustom): RoomType
    fun delete(id: Long): RoomType
    fun getById(id: Long): RoomType
    fun addNew(roomTypeCustom: RoomTypeCustom): RoomType
}