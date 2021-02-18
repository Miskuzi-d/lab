package ru.cobalt42.lab.dto

data class PaginatedResponse (
    val total: Int,
    val result: List<LabConclusionDTO>
    )