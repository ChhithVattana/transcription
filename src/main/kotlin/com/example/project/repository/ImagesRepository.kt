package com.example.project.repository

import com.example.project.model.Images
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ImagesRepository : JpaRepository<Images, Long>, JpaSpecificationExecutor<Images> {
    fun findByIdAndStatusTrue(id: Long): Optional<Images>
}