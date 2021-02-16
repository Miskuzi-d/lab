package ru.cobalt42.lab.exception

import org.springframework.http.HttpStatus

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.Exception

@RestControllerAdvice
class LabConclusionExceptionHandler {

    @ExceptionHandler
    fun handleException(otherException: Exception): ResponseEntity<LabConclusionErrorResponse?>? {
        val errorResponse = LabConclusionErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            otherException.message,
            System.currentTimeMillis()
        )
        return ResponseEntity<LabConclusionErrorResponse?>(errorResponse, HttpStatus.BAD_REQUEST)
    }


}