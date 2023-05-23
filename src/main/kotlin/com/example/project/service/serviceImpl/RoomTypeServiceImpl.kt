package com.example.project.service.serviceImpl

import com.example.project.exception.ResourceNotFoundException
import com.example.project.model.RoomType
import com.example.project.model.customModel.RoomTypeCustom
import com.example.project.repository.RoomTypeRepository
import com.example.project.service.RoomTypeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class RoomTypeServiceImpl : BaseServiceImpl<RoomType>(), RoomTypeService {

    @Autowired
    lateinit var roomTypeRepository: RoomTypeRepository

    override fun getRepository(): JpaRepository<RoomType, Long> {
        return roomTypeRepository
    }

    override fun getSpecificationExecutor(): JpaSpecificationExecutor<RoomType> {
        return roomTypeRepository
    }

    override fun update(id: Long, roomTypeCustom: RoomTypeCustom): RoomType {
        val r = roomTypeRepository.findByIdAndStatusTrue(id)
            .orElseThrow { ResourceNotFoundException("Room Type id: $id not founded") }
        r.name = roomTypeCustom.name
        r.bedType = roomTypeCustom.bedType
        r.price = roomTypeCustom.price
        r.mainDescription = roomTypeCustom.mainDescription
        r.subDescription = roomTypeCustom.subDescription
        r.roomSize = roomTypeCustom.roomSize
        r.url = roomTypeCustom.url
        r.offering = roomTypeCustom.offering
        r.floor = roomTypeCustom.floor
        r.capacity = roomTypeCustom.capacity
        return roomTypeRepository.save(r)
    }

    override fun delete(id: Long): RoomType {
        val r = roomTypeRepository.findByIdAndStatusTrue(id).orElseThrow {
            ResourceNotFoundException("Room type id: $id not found")
        }
        r.status = false
        return roomTypeRepository.save(r)
    }

    override fun getById(id: Long): RoomType {
        return roomTypeRepository.findByIdAndStatusTrue(id).orElseThrow {
            ResourceNotFoundException("Room type id: $id not found")
        }
    }

    @Transactional
    override fun addNew(roomTypeCustom: RoomTypeCustom): RoomType {
        val roomType = RoomType()
        roomType.name = roomTypeCustom.name
        roomType.bedType = roomTypeCustom.bedType
        roomType.price = roomTypeCustom.price
        roomType.mainDescription = roomTypeCustom.mainDescription
        roomType.subDescription = roomTypeCustom.subDescription
        roomType.roomSize = roomTypeCustom.roomSize
        roomType.url = roomTypeCustom.url
        roomType.floor = roomTypeCustom.floor
        roomType.capacity = roomTypeCustom.capacity
        roomType.offering = roomTypeCustom.offering
        return roomTypeRepository.save(roomType)
    }
}