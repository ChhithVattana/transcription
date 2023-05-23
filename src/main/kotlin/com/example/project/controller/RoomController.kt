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

    @Autowired
    lateinit var responseObjectMap: ResponseObjectMap

    @GetMapping
    fun getAllRoom(@RequestParam(required = false) page: Int, size: Int, q: String): MutableMap<String, Any> {
        val r = roomService.getByPage(page, size, q)
        return responseObjectMap.respondObject(r.content, r.totalElements)
    }

    @GetMapping("/id")
    fun getById(@RequestParam id: Long): MutableMap<String, Any> {
        val r = roomService.getRoomById(id)
        return responseObjectMap.respondObject(r)
    }

    @GetMapping("/get-custom")
    fun getAllCustomRoom(@RequestParam page: Int, size: Int): MutableMap<String, Any> {
        val r = roomService.getCustomRoom(page, size)
        return responseObjectMap.respondObject(r.content, r.totalElements)
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_INSERT')")
    fun addNewRoom(@RequestBody roomCustom: RoomCustom): MutableMap<String, Any> {
        val r = roomService.addNew(roomCustom)
        return responseObjectMap.respondObject(r)
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_UPDATE')")
    fun updateRoom(@PathVariable id: Long, @RequestBody roomCustom: RoomCustom): MutableMap<String, Any> {
        val r = roomService.update(id, roomCustom)
        return responseObjectMap.respondObject(r)
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_ADMIN_DELETE')")
    fun deleteRoom(@RequestParam id: Long): MutableMap<String, Any> {
        val r = roomService.delete(id)
        return responseObjectMap.respondObject(r)
    }
}