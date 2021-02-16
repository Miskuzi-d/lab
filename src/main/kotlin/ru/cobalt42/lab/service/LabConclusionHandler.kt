package ru.cobalt42.lab.service

import ru.cobalt42.lab.dto.LabConclusionResponse
import ru.cobalt42.lab.dto.ListConclusionResponse
import ru.cobalt42.lab.model.LabConclusion

interface LabConclusionHandler {

    fun processLabConclusion(labConclusion: LabConclusion) : LabConclusionResponse

    fun updateLabConclusion(labConclusion: LabConclusion) : LabConclusionResponse

    fun findByUid(uid: String): LabConclusionResponse

    fun findAll(): ListConclusionResponse

    fun deleteByUid(uid: String): String

    fun deleteAll()

}