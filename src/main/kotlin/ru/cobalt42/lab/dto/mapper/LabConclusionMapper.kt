package ru.cobalt42.lab.dto.mapper

import org.springframework.stereotype.Component
import ru.cobalt42.lab.dto.LabConclusionDTO
import ru.cobalt42.lab.dto.PaginatedResponse
import ru.cobalt42.lab.model.TubeLabConclusionPath
import kotlin.streams.toList

@Component
class LabConclusionMapper {

    fun fromModelToDTO(tubeLabConclusionPath: TubeLabConclusionPath) : LabConclusionDTO {
           return LabConclusionDTO(
                tubeLabConclusionPath.uid!!,
                tubeLabConclusionPath.name,
                tubeLabConclusionPath.target,
                tubeLabConclusionPath.type,
                tubeLabConclusionPath.data,
                tubeLabConclusionPath.signers,
                tubeLabConclusionPath.isGood,
                tubeLabConclusionPath.wallThickness,
                tubeLabConclusionPath.diameter,
                tubeLabConclusionPath.controlConditions,
                tubeLabConclusionPath.controlMode,
                tubeLabConclusionPath.comment,
                tubeLabConclusionPath.defectList,
                tubeLabConclusionPath.zones,
                tubeLabConclusionPath.processUnit,
                tubeLabConclusionPath.technologicalDocument,
                tubeLabConclusionPath.measuringInstruments
            )
    }

    fun fromListModelToListDTO(conclusionTubes: List<TubeLabConclusionPath>): List<LabConclusionDTO> =
        conclusionTubes.stream().map(this::fromModelToDTO).toList()

    fun createListConclusionResponse(conclusionTubes: List<TubeLabConclusionPath>): PaginatedResponse {
        val result = fromListModelToListDTO(conclusionTubes)
        return PaginatedResponse(result.size, result)
    }
}