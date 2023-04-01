package com.example.project.service.serviceImpl

import com.example.project.model.Room
import com.example.project.model.customModel.RoomCustom
import com.example.project.repository.RoomRepository
import com.example.project.repository.RoomTypeRepository
import com.example.project.service.RoomService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service

@Service
class RoomServiceImpl : BaseServiceImpl<Room>(), RoomService {

    @Autowired
    lateinit var roomRepository: RoomRepository

    @Autowired
    lateinit var roomTypeRepository: RoomTypeRepository

    override fun getRepository(): JpaRepository<Room, Long> {
        return roomRepository
    }

    override fun getSpecificationExecutor(): JpaSpecificationExecutor<Room> {
        return roomRepository
    }

    override fun getCustomRoom(): List<RoomCustom> {
        return roomRepository.findAllByStatusTrueOrderByIdDesc().map {
            RoomCustom(it.id, it.roomNo, it.available, it.roomType_id!!.id)
        }
    }

    override fun getRoomById(id: Long): Room {
        return roomRepository.findAllById(id)
    }

    override fun addNew(roomCustom: RoomCustom): Room {
        val room = Room()
        room.roomNo = roomCustom.roomNo
        room.available = true
        room.roomType_id = roomTypeRepository.findAllById(roomCustom.roomType_id)
        return roomRepository.save(room)
    }

    override fun update(id: Long, roomCustom: RoomCustom): Room {
        val r = roomRepository.findAllById(id)
        r.roomNo = roomCustom.roomNo
        r.available = true
        r.roomType_id = roomTypeRepository.findAllById(roomCustom.roomType_id)
        return roomRepository.save(r)
    }

    override fun delete(id: Long): Room {
        val r = roomRepository.findAllById(id)
        r.status = false
        return roomRepository.save(r)
    }
}