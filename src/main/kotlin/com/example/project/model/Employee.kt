package com.example.project.model

import com.example.project.model.baseEntity.BaseEntity
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "tbl_employee")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    var name: String? = "",
    var gender: Boolean? = true,
    @JsonFormat(pattern = "yyyy-MM-dd")
    var dob: LocalDate? = null,
    var phone: String? = "",
    var email: String? = "",
    var salary: Double? = 0.0,
    @JsonFormat(pattern = "yyyy-MM-dd")
    var dateIn: LocalDate? = null,
    var position: String? = ""
) : BaseEntity()
