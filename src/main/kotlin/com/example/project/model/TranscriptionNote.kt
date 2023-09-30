package com.example.project.model

import com.example.project.model.baseEntity.BaseEntity
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "tbl_transcription_note")
data class TranscriptionNote(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @Column(name = "title")
    var title: String? = "",
    @Column(name = "note", columnDefinition = "text")
    var note: String? = "",
    @Column(name = "last_saved")
    @JsonFormat(pattern = "dd-MM-yyy")
    var lastSaved: LocalDate? = null,
    @Column(name = "duration")
    var duration: Double? = 0.0,
) : BaseEntity()
