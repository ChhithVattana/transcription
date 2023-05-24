package com.example.project.controller

import com.example.project.model.customModel.RoomCustom
import com.example.project.service.RoomService
import com.example.project.utils.AppConstant
import com.example.project.utils.ResponseObjectMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstant.MAIN_ENDPOINT + "/room")
class RoomController {

    @Autowired
    lateinit var roomService: RoomService

    val response = ResponseObjectMap()

    @GetMapping
    fun getAllRoom(@RequestParam(required = false) page: Int, size: Int, q: String): MutableMap<String, Any> {
        val r = roomService.getByPage(page, size, q)
        return response.respondObject(r.content, r.totalElements)
    }

    @GetMapping("/id")
    fun getById(@RequestParam id: Long): MutableMap<String, Any> =
        response.respondObject(roomService.getRoomById(id))

    @GetMapping("/get-custom")
    fun getAllCustomRoom(@RequestParam page: Int, size: Int): MutableMap<String, Any> {
        val r = roomService.getCustomRoom(page, size)
        return response.respondObject(r.content, r.totalElements)
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_INSERT')")
    fun addNewRoom(@RequestBody roomCustom: RoomCustom): MutableMap<String, Any> =
        response.respondObject(roomService.addNew(roomCustom))

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_UPDATE')")
    fun updateRoom(@PathVariable id: Long, @RequestBody roomCustom: RoomCustom): MutableMap<String, Any> =
        response.respondObject(roomService.update(id, roomCustom))

    @DeleteMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_DELETE')")
    fun deleteRoom(@RequestParam id: Long): MutableMap<String, Any> =
        response.respondObject(roomService.delete(id))
}