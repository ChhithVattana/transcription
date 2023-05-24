package com.example.project.controller

import com.example.project.model.customModel.RoomTypeCustom
import com.example.project.service.RoomTypeService
import com.example.project.utils.AppConstant
import com.example.project.utils.ResponseObjectMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstant.MAIN_ENDPOINT + "/room-type")
class RoomTypeController {

    @Autowired
    lateinit var roomTypeService: RoomTypeService

    val response = ResponseObjectMap()

    @GetMapping
    fun getAllRoomType(@RequestParam(required = false) page: Int, size: Int, q: String): MutableMap<String, Any> {
        val r = roomTypeService.getByPage(page, size, q)
        return response.respondObject(r.content, r.totalElements)
    }

    @GetMapping("/id")
    fun getByID(@RequestParam id: Long): MutableMap<String, Any> =
        response.respondObject(roomTypeService.getById(id))

    @PostMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_INSERT')")
    fun postRoomType(@RequestBody roomTypeCustom: RoomTypeCustom): MutableMap<String, Any> =
        response.respondObject(roomTypeService.addNew(roomTypeCustom))

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_UPDATE')")
    fun update(@PathVariable id: Long, @RequestBody roomTypeCustom: RoomTypeCustom): MutableMap<String, Any> =
        response.respondObject(roomTypeService.update(id, roomTypeCustom))

    @DeleteMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_DELETE')")
    fun delete(@RequestParam id: Long): MutableMap<String, Any> =
        response.respondObject(roomTypeService.delete(id))
}