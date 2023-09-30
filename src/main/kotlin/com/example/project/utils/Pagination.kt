package com.example.project.utils

import org.springframework.stereotype.Component

@Component
class Pagination<T> {
    fun paginate(page: Int, pageSize: Int, dataList: List<T>): List<T> {
        val startIndex = page * pageSize
        val endIndex = startIndex + pageSize
        val totalItems = dataList.size

        if (startIndex >= totalItems) {
            // Page is out of bounds, return an empty list
            return emptyList()
        }

        // Ensure endIndex does not exceed the total items
        val actualEndIndex = minOf(endIndex, totalItems)

        return dataList.subList(startIndex, actualEndIndex)
    }
}