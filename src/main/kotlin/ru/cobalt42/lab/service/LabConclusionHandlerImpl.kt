package ru.cobalt42.lab.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.cobalt42.lab.dto.LabConclusionDTO
import ru.cobalt42.lab.dto.PaginatedResponse
import ru.cobalt42.lab.dto.mapper.LabConclusionMapper
import ru.cobalt42.lab.model.TubeLabConclusionPath
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeOthersDefect
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeZone
import ru.cobalt42.lab.repository.LabRepository
import ru.cobalt42.lab.service.innerservices.RecordCompiler
import ru.cobalt42.lab.service.innerservices.DefectValidatorService
import java.util.*


@Service
class LabConclusionHandlerImpl : LabConclusionHandler {

    @Autowired
    private lateinit var validator: DefectValidatorService

    @Autowired
    private lateinit var mapper: LabConclusionMapper

    @Autowired
    private lateinit var recordCompiler: RecordCompiler

    @Autowired
    private lateinit var labRepository: LabRepository

    override fun processLabConclusion(tubeLabConclusionPath: TubeLabConclusionPath): LabConclusionDTO {
        tubeLabConclusionPath.uid = initializeUidIfBlank()
        processConclusionByObjectType(tubeLabConclusionPath)
        labRepository.save(tubeLabConclusionPath)

        return mapper.fromModelToDTO(tubeLabConclusionPath)
    }

    override fun updateLabConclusion(uid: String, tubeLabConclusionPath: TubeLabConclusionPath): LabConclusionDTO {
        val old: TubeLabConclusionPath = labRepository.findByUid(uid)
        tubeLabConclusionPath._id = old._id
        return processLabConclusion(tubeLabConclusionPath)
    }

    private fun processConclusionByObjectType(tubeLabConclusionPath: TubeLabConclusionPath) {

        var result = true

        when (tubeLabConclusionPath.target.uname) {
            "РК" -> {
                for (zone: JointTubeZone in tubeLabConclusionPath.zones!!) {
                    if (!validator.DefectsValidate(tubeLabConclusionPath.wallThickness.toDouble(), zone)) result = false
                    zone.conclusionCode = recordCompiler.compile(zone)

                }
            }
            "ВИК" -> if (!validator.DefectsValidate(tubeLabConclusionPath.wallThickness.toDouble(), tubeLabConclusionPath.defectList!!)) result = false
        }
        tubeLabConclusionPath.isGood = result
    }

    private fun initializeUidIfBlank(): String = UUID.randomUUID().toString()

    override fun findByUid(uid: String): LabConclusionDTO {
        return mapper.fromModelToDTO(labRepository.findByUid(uid))
    }

    override fun findAll(): PaginatedResponse {
        return mapper.createListConclusionResponse(labRepository.findAll())
    }

    override fun deleteByUid(uid: String): String {
        labRepository.deleteByUid(uid)
        return "Successful delete Lab Conclusion with uid $uid"
    }

    override fun deleteAll() {
        labRepository.deleteAll()
    }


}