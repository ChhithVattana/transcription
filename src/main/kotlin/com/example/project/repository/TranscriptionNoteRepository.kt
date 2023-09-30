package com.example.project.repository

import com.example.project.model.TranscriptionNote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TranscriptionNoteRepository : JpaRepository<TranscriptionNote, Long>,
    JpaSpecificationExecutor<TranscriptionNote> {
        fun findByIdAndStatusTrue(id: Long): Optional<TranscriptionNote>
}