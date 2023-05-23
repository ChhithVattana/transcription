package com.example.project.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.CONFLICT)
class DuplicateCustomerException(message: String?) : RuntimeException(message)