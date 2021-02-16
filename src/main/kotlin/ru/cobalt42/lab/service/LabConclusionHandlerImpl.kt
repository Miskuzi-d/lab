package ru.cobalt42.lab.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.cobalt42.lab.dto.LabConclusionResponse
import ru.cobalt42.lab.dto.ListConclusionResponse
import ru.cobalt42.lab.mapper.LabConclusionMapper
import ru.cobalt42.lab.model.LabConclusion
import ru.cobalt42.lab.model.LabConclusionObject
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

    override fun processLabConclusion(labConclusion: LabConclusion): LabConclusionResponse {
        labConclusion.uid = initializeUidIfBlank()
        processConclusionByObjectType(labConclusion.conclusionObject)
        labRepository.save(labConclusion)

        return mapper.fromModelToDTO(labConclusion)
    }

    override fun updateLabConclusion(uid: String, labConclusion: LabConclusion): LabConclusionResponse {
        val old: LabConclusion = labRepository.findByUid(uid)
        labConclusion._id = old._id
        return processLabConclusion(labConclusion)
    }

    private fun processConclusionByObjectType(conclusionObject: LabConclusionObject) =
    when (conclusionObject) {
        is LabConclusionObject.JointTubeLinePart -> {
            for (zone: JointTubeZone in conclusionObject.zones) {
                conclusionObject.isGood = validator.defectsValidate(conclusionObject.wallThickness, zone)
                zone.conclusionCode = recordCompiler.compile(zone)
            }
        }
    }

    private fun initializeUidIfBlank(): String = UUID.randomUUID().toString()

    override fun findByUid(uid: String): LabConclusionResponse {
        return mapper.fromModelToDTO(labRepository.findByUid(uid))
    }

    override fun findAll(): ListConclusionResponse {
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