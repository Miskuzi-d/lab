package ru.cobalt42.lab.service

import ru.cobalt42.lab.dto.LabConclusionDTO
import ru.cobalt42.lab.dto.PaginatedResponse
import ru.cobalt42.lab.model.TubeLabConclusionPath

interface LabConclusionHandler {

    fun processLabConclusion(tubeLabConclusionPath: TubeLabConclusionPath) : LabConclusionDTO

    fun updateLabConclusion(uid: String, tubeLabConclusionPath: TubeLabConclusionPath) : LabConclusionDTO

    fun findByUid(uid: String): LabConclusionDTO

    fun findAll(): PaginatedResponse

    fun deleteByUid(uid: String): String

    fun deleteAll()

}