package com.example.project.service.serviceImpl

import com.example.project.exception.ResourceNotFoundException
import com.example.project.model.Images
import com.example.project.model.customModel.ImagesCustom
import com.example.project.repository.ImagesRepository
import com.example.project.repository.RoomTypeRepository
import com.example.project.service.ImagesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service

@Service
class ImagesServiceImpl : BaseServiceImpl<Images>(), ImagesService {

    @Autowired
    lateinit var imagesRepository: ImagesRepository

    @Autowired
    lateinit var roomTypeRepository: RoomTypeRepository

    override fun getRepository(): JpaRepository<Images, Long> {
        return imagesRepository
    }

    override fun getSpecificationExecutor(): JpaSpecificationExecutor<Images> {
        return imagesRepository
    }

    override fun addNew(imagesCustom: ImagesCustom): Images {
        val images = Images()
        val r = roomTypeRepository.findAllById(imagesCustom.roomTypeId)
        images.url = imagesCustom.url
        images.roomTypeId = r
        return imagesRepository.save(images)
    }

    override fun update(id: Long, imagesCustom: ImagesCustom): Images {
        val r = imagesRepository.findById(id).orElse(null)
            ?: throw ResourceNotFoundException("Image id: $id not founded")
        r.url = imagesCustom.url
        r.roomTypeId = roomTypeRepository.findAllById(imagesCustom.roomTypeId)
        return imagesRepository.save(r)
    }

    override fun delete(id: Long): Images {
        val r = imagesRepository.findById(id).orElse(null)
            ?: throw ResourceNotFoundException("Image id: $id not founded")
        r.status = false
        return imagesRepository.save(r)
    }

    override fun getAllByRoomTypeId(id: Long): MutableList<Images> {
        val r = imagesRepository.findAll()
        val tmp: MutableList<Images> = mutableListOf()
        r.forEach {
            if (it.roomTypeId?.id == id) {
                tmp.add(it)
            }
        }
        return tmp
    }
}