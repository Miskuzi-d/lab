package ru.cobalt42.lab.mapper

import org.springframework.stereotype.Component
import ru.cobalt42.lab.dto.LabConclusionResponse
import ru.cobalt42.lab.dto.ListConclusionResponse
import ru.cobalt42.lab.model.LabConclusion
import kotlin.streams.toList

@Component
class LabConclusionMapper {

    fun fromModelToDTO(labConclusion: LabConclusion) : LabConclusionResponse {
        return LabConclusionResponse(
            labConclusion.uid!!,
            labConclusion.name,
            labConclusion.type,
            labConclusion.data,
            labConclusion.signers,
            labConclusion.isGood,
            labConclusion.conclusionObject,
            labConclusion.processUnit,
            labConclusion.technologicalDocument,
            labConclusion.measuringInstruments
        )
    }

    fun fromListModelToListDTO(conclusions: List<LabConclusion>): List<LabConclusionResponse> =
        conclusions.stream().map(this::fromModelToDTO).toList()

    fun createListConclusionResponse(conclusions: List<LabConclusion>): ListConclusionResponse {
        val result = fromListModelToListDTO(conclusions)
        return ListConclusionResponse(result.size, result)
    }
}