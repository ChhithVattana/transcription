package com.example.project.controller

import com.example.project.model.customModel.RoomTypeCustom
import com.example.project.service.RoomTypeService
import com.example.project.utils.AppConstant
import com.example.project.utils.ResponseObjectMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstant.MAIN_ENDPOINT + "/roomType")
class RoomTypeController {

    @Autowired
    lateinit var roomTypeService: RoomTypeService

    @Autowired
    lateinit var responseObjectMap: ResponseObjectMap

    @GetMapping
    fun getAllRoomType(@RequestParam(required = false) page: Int, size: Int, q: String): MutableMap<String, Any> {
        val r = roomTypeService.getByPage(page, size, q)
        return responseObjectMap.respondObject(r.content, r.totalElements)
    }

    @GetMapping("/getById")
    fun getByID(@RequestParam id: Long): MutableMap<String, Any> {
        val r = roomTypeService.getById(id)
        return responseObjectMap.respondObject(r)
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_INSERT')")
    fun postRoomType(@RequestBody roomTypeCustom: RoomTypeCustom): MutableMap<String, Any> {
        val r = roomTypeService.addNew(roomTypeCustom)
        return responseObjectMap.respondObject(r)
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_UPDATE')")
    fun update(@PathVariable id: Long, @RequestBody roomTypeCustom: RoomTypeCustom): MutableMap<String, Any> {
        val r = roomTypeService.update(id, roomTypeCustom)
        return responseObjectMap.respondObject(r)
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_DELETE')")
    fun delete(@RequestParam id: Long): MutableMap<String, Any> {
        val r = roomTypeService.delete(id)
        return responseObjectMap.respondObject(r)
    }
}