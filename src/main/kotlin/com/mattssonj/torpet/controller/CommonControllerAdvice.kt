package com.mattssonj.torpet.controller

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
    fun handleIllegalArguments(exception: IllegalArgumentException) =
        Error(HttpStatus.BAD_REQUEST.value(), exception.message ?: "")

    @ExceptionHandler(UsernameAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleUsernameAlreadyExists(exception: UsernameAlreadyExistsException) =
        Error(HttpStatus.CONFLICT.value(), exception.message!!)

    @ExceptionHandler(NoDataFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNoDataFound(exception: NoDataFoundException) =
        Error(HttpStatus.NOT_FOUND.value(), exception.message ?: "")

    @ExceptionHandler(ForbiddenOperationException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleForbiddenOperation(exception: ForbiddenOperationException) =
        Error(HttpStatus.FORBIDDEN.value(), exception.message ?: "")

}

class UsernameAlreadyExistsException(username: String) : RuntimeException("A User with username $username already exists")
class NoDataFoundException(msg: String): RuntimeException(msg)
class ForbiddenOperationException(msg: String): RuntimeException(msg)

data class Error(val statusCode: Int, val message: String, val timestamp: LocalDate = LocalDate.now())