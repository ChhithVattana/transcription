package com.example.project.utils

class ResponseObjectMap {
    private val responseObject = ResponseObject()

    fun respondObject(obj: Any?, totalElements: Long): MutableMap<String, Any> {
        val response: MutableMap<String, Any> = HashMap()
        if (obj != null) {
            response["result"] = obj
            response["length"] = totalElements
            response["response"] = responseObject.success()
        } else {
            response["response"] = responseObject.error()
        }
        return response
    }

    fun respondObject(obj: Any?): MutableMap<String, Any> {
        val response: MutableMap<String, Any> = HashMap()
        if (obj != null) {
            response["result"] = obj
            response["response"] = responseObject.success()
        } else {
            response["response"] = responseObject.error()
        }
        return response
    }
}