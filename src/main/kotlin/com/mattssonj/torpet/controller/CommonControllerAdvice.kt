package com.mattssonj.torpet.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@ControllerAdvice
class CommonControllerAdvice {

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleIllegalArguments(exception: IllegalArgumentException) = Error(404, exception.message ?: "")

}

data class Error(val statusCode: Int, val message: String, val timestamp: LocalDate = LocalDate.now())