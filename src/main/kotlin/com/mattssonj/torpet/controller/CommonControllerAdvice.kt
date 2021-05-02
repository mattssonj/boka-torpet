package com.mattssonj.torpet.controller

import com.mattssonj.torpet.business.UsernameAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.LocalDate

@ControllerAdvice
@ResponseBody
class CommonControllerAdvice {

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArguments(exception: IllegalArgumentException) = Error(HttpStatus.BAD_REQUEST.value(), exception.message ?: "")

    @ExceptionHandler(UsernameAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleUsernameAlreadyExists(exception: UsernameAlreadyExistsException) = Error(HttpStatus.CONFLICT.value(), exception.message!!)

}

data class Error(val statusCode: Int, val message: String, val timestamp: LocalDate = LocalDate.now())