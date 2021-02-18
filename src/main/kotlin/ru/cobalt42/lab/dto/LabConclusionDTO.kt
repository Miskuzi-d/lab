package ru.cobalt42.lab.dto

import org.bson.types.ObjectId
import ru.cobalt42.lab.model.LabConclusionTarget
import ru.cobalt42.lab.model.LabConclusionSigners
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeConclusionType
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeOthersDefect
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeZone

data class LabConclusionDTO(

    var uid: String,
    val name: String,
    val target: LabConclusionTarget,
    val type: JointTubeConclusionType,
    val data: String,
    val signers: LabConclusionSigners,
    var isGood: Boolean,
    val wallThickness: String,
    val diameter: String,
    val controlConditions: String?, // ВИК, ПВК, МПД
    val controlMode: String?,  //МПД
    val comment: String?, // ВИК, ПВК, МПД
    val defectList: List<JointTubeOthersDefect>?, //ВИК, ПВК, МПД
    val zones: List<JointTubeZone>?, //РК
    val processUnit: String,
    val technologicalDocument: String,
    val measuringInstruments: String

)
