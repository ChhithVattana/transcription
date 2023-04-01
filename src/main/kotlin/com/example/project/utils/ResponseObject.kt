package com.example.project.utils

class ResponseObject {

    fun success(): Response = Response(200, "Success")

    fun error(): Response = Response(404, "Not Found")
}