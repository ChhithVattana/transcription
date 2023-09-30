package com.example.project.service

import com.example.project.model.TranscriptionNote
import org.springframework.data.domain.Page

interface TranscriptionNoteService : BaseService<TranscriptionNote> {
    fun newNote(transcriptionNote: TranscriptionNote): TranscriptionNote
    fun getById(id: Long): TranscriptionNote
    fun update(id: Long, transcriptionNote: TranscriptionNote): TranscriptionNote
    fun delete(id: Long): TranscriptionNote
    fun searchNote(page: Int, size: Int, q: String?): Page<TranscriptionNote>
}