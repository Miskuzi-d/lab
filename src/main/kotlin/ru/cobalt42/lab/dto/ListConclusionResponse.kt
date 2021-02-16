package ru.cobalt42.lab.dto

data class ListConclusionResponse (
    val total: Int,
    val result: List<LabConclusionResponse>

    )