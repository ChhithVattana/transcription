package com.example.project.service

import org.springframework.data.domain.Page

interface BaseService<T> {
    fun addNew(t: T): T
    fun getByPage(page: Int, size: Int, q: String?): Page<T>
}