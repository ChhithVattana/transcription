package com.example.project.service.serviceImpl

import com.example.project.exception.ResourceNotFoundException
import com.example.project.model.TranscriptionNote
import com.example.project.repository.TranscriptionNoteRepository
import com.example.project.service.TranscriptionNoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class TranscriptionServiceImpl : BaseServiceImpl<TranscriptionNote>(), TranscriptionNoteService {

    @Autowired
    lateinit var transcriptionNoteRepository: TranscriptionNoteRepository

    override fun getRepository(): JpaRepository<TranscriptionNote, Long> {
        return transcriptionNoteRepository
    }

    override fun getSpecificationExecutor(): JpaSpecificationExecutor<TranscriptionNote> {
        return transcriptionNoteRepository
    }

    override fun newNote(transcriptionNote: TranscriptionNote): TranscriptionNote {
        transcriptionNote.title = ""
        transcriptionNote.note = ""
        transcriptionNote.duration = 30.00
        transcriptionNote.lastSaved = LocalDate.now()
        return transcriptionNoteRepository.save(transcriptionNote)
    }

    override fun getById(id: Long): TranscriptionNote {
        return transcriptionNoteRepository.findByIdAndStatusTrue(id).orElseThrow {
            ResourceNotFoundException("note id: $id not found")
        }
    }

    override fun update(id: Long, transcriptionNote: TranscriptionNote): TranscriptionNote {
        val r = transcriptionNoteRepository.findByIdAndStatusTrue(id)
            .orElseThrow { ResourceNotFoundException("note id: $id not found") }
        r.title = transcriptionNote.title
        r.note = transcriptionNote.note
        r.duration = transcriptionNote.duration
        return transcriptionNoteRepository.save(r)
    }

    override fun delete(id: Long): TranscriptionNote {
        val r = transcriptionNoteRepository.findByIdAndStatusTrue(id)
            .orElseThrow { ResourceNotFoundException("note id: $id not found") }
        r.status = false
        return transcriptionNoteRepository.save(r)
    }

    override fun searchNote(page: Int, size: Int, q: String?): Page<TranscriptionNote> {
        return getSpecificationExecutor().findAll({ root, query, cb ->
            val predicates = ArrayList<javax.persistence.criteria.Predicate>()
            q?.let {
                val title =
                    cb.like(cb.upper(root.get("title")), "%${q.uppercase()}%")
                predicates.add(cb.or(title))
            }
            predicates.add(cb.equal(root.get<String>("status"), true))
            query.orderBy(cb.desc(root.get<String>("id")))
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page, size))
    }
}