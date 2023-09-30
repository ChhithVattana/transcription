package com.example.project.service.serviceImpl

import com.example.project.exception.ResourceNotFoundException
import com.example.project.model.Room
import com.example.project.model.customModel.RoomCustom
import com.example.project.repository.RoomRepository
import com.example.project.repository.RoomTypeRepository
import com.example.project.service.RoomService
import com.example.project.utils.Pagination
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
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

    override fun getCustomRoom(page: Int, size: Int): Page<RoomCustom> {
        val pagination = Pagination<RoomCustom>()
        val room = roomRepository.findAllByStatusTrueOrderByIdDesc().map {
            RoomCustom(it.id, it.roomNo, it.available, it.roomTypeId!!.id)
        }
        return PageImpl(pagination.paginate(page, size, room), PageRequest.of(page, size), room.size.toLong())
    }

    override fun getRoomById(id: Long): Room {
        return roomRepository.findByIdAndStatusTrue(id)
            .orElseThrow { ResourceNotFoundException("Room id: $id not found") }
    }

    override fun addNew(roomCustom: RoomCustom): Room {
        val room = Room()
        room.roomNo = roomCustom.roomNo
        room.available = true
        room.roomTypeId = roomTypeRepository.findByIdAndStatusTrue(roomCustom.roomTypeId!!)
            .orElseThrow { ResourceNotFoundException("Room type id: ${roomCustom.roomTypeId} not found") }
        return roomRepository.save(room)
    }

    override fun update(id: Long, roomCustom: RoomCustom): Room {
        val r = roomRepository.findByIdAndStatusTrue(id)
            .orElseThrow { ResourceNotFoundException("Room id: $id not found") }
        r.roomNo = roomCustom.roomNo
        r.available = true
        r.roomTypeId = roomTypeRepository.findByIdAndStatusTrue(roomCustom.roomTypeId!!)
            .orElseThrow { ResourceNotFoundException("Room type id: ${roomCustom.roomTypeId} not found") }
        return roomRepository.save(r)
    }

    override fun delete(id: Long): Room {
        val r = roomRepository.findByIdAndStatusTrue(id)
            .orElseThrow { ResourceNotFoundException("Room id: $id not found") }
        r.status = false
        return roomRepository.save(r)
    }
}