package com.example.project.model.baseEntity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.Date
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity {
    @Column(name = "status", nullable = false)
    var status: Boolean = true

    @Column(name = "version")
    @Version
    @JsonIgnore
    var version: Int? = 0

    @JsonIgnore
    @Column(name = "date_created")
    var created: Date? = null

    @JsonIgnore
    @Column(name = "date_updated")
    var updated: Date? = null

    /**
     *
     */
    @PrePersist
    protected fun onCreate() {
        created = Date()
    }

    /**
     *
     */
    @PreUpdate
    protected fun onUpdate() {
        updated = Date()
    }
}