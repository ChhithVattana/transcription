package com.example.project.controller

import com.example.project.model.customModel.ImagesCustom
import com.example.project.service.ImagesService
import com.example.project.utils.AppConstant
import com.example.project.utils.ResponseObjectMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstant.MAIN_ENDPOINT + "/images")
class ImagesController {

    @Autowired
    lateinit var imagesService: ImagesService

    @Autowired
    lateinit var responseObjectMap: ResponseObjectMap

    @GetMapping
    fun getAllImage(@RequestParam(required = false) page: Int, size: Int, q: String): MutableMap<String, Any> {
        val r = imagesService.getByPage(page, size, q)
        return responseObjectMap.respondObject(r.content, r.totalElements)
    }

    @GetMapping("/id")
    fun getAllByRoomTypeId(@RequestParam id: Long): MutableMap<String, Any> {
        val r = imagesService.getAllByRoomTypeId(id)
        return responseObjectMap.respondObject(r)
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_INSERT')")
    fun addNew(@RequestBody imagesCustom: ImagesCustom): MutableMap<String, Any> {
        val r = imagesService.addNew(imagesCustom)
        return responseObjectMap.respondObject(r)
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_UPDATE')")
    fun update(@PathVariable id: Long, @RequestBody imagesCustom: ImagesCustom): MutableMap<String, Any> {
        val r = imagesService.update(id, imagesCustom)
        return responseObjectMap.respondObject(r)
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_DELETE')")
    fun delete(@RequestParam id: Long): MutableMap<String, Any> {
        val r = imagesService.delete(id)
        return responseObjectMap.respondObject(r)
    }
}