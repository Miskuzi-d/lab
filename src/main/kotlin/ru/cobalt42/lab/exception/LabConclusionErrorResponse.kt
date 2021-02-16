package ru.cobalt42.lab.exception

import java.lang.Exception

data class LabConclusionErrorResponse(
    val status: Int,
    override val message: String?,
    val timeStamp: Long
    ) : Exception()