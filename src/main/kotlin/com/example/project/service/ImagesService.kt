package com.example.project.service

import com.example.project.model.Images
import com.example.project.model.customModel.ImagesCustom

interface ImagesService : BaseService<Images> {
    fun addNew(imagesCustom: ImagesCustom): Images
    fun update(id: Long, imagesCustom: ImagesCustom): Images
    fun delete(id: Long): Images
    fun getAllByRoomTypeId(id: Long): MutableList<Images>
}