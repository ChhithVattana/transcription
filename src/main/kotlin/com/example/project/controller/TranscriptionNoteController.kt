package com.example.project.controller

import com.example.project.model.TranscriptionNote
import com.example.project.service.TranscriptionNoteService
import com.example.project.utils.AppConstant

import com.example.project.utils.ResponseObjectMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstant.MAIN_ENDPOINT + "/transcription-note")
class TranscriptionNoteController {
    @Autowired
    lateinit var transcriptionNoteService: TranscriptionNoteService

    val response = ResponseObjectMap()

    @GetMapping
    fun getAllList(@RequestParam(required = false) page: Int, size: Int, q: String): MutableMap<String, Any> {
        val r = transcriptionNoteService.searchNote(page, size, q)
        return response.respondObject(r.content, r.totalElements)
    }

    @GetMapping("/id")
    fun getById(@RequestParam id: Long): MutableMap<String, Any> =
        response.respondObject(transcriptionNoteService.getById(id))

    @PostMapping
    fun newNote(@RequestBody transcriptionNote: TranscriptionNote): MutableMap<String, Any> {
        return response.respondObject(transcriptionNoteService.newNote(transcriptionNote))
    }

    @PutMapping("/{id}/update")
    fun update(
        @PathVariable id: Long,
        @RequestBody transcriptionNote: TranscriptionNote
    ): MutableMap<String, Any> = response.respondObject(transcriptionNoteService.update(id, transcriptionNote))

    @DeleteMapping("/delete")
    fun delete(@RequestParam id: Long): MutableMap<String, Any> =
        response.respondObject(transcriptionNoteService.delete(id))
}