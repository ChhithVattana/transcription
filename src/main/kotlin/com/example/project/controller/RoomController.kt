package com.example.project.controller

import com.example.project.model.customModel.RoomCustom
import com.example.project.service.RoomService
import com.example.project.utils.AppConstant
import com.example.project.utils.ResponseObjectMap
import org.springframework.beans.factory.annotation.Autowired
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

    @GetMapping("/getById")
    fun getById(@RequestParam id: Long): MutableMap<String, Any> {
        val r = roomService.getRoomById(id)
        return responseObjectMap.respondObject(r)
    }

    @GetMapping("/getCustom")
    fun getAllCustomRoom(): MutableMap<String, Any> {
        val r = roomService.getCustomRoom()
        return responseObjectMap.respondObject(r)
    }

    @PostMapping
    fun addNewRoom(@RequestBody roomCustom: RoomCustom): MutableMap<String, Any> {
        val r = roomService.addNew(roomCustom)
        return responseObjectMap.respondObject(r)
    }

    @PutMapping("/{id}/update")
    fun updateRoom(@PathVariable id: Long, @RequestBody roomCustom: RoomCustom): MutableMap<String, Any> {
        val r = roomService.update(id, roomCustom)
        return responseObjectMap.respondObject(r)
    }

    @DeleteMapping
    fun deleteRoom(@RequestParam id: Long): MutableMap<String, Any> {
        val r = roomService.delete(id)
        return responseObjectMap.respondObject(r)
    }
}