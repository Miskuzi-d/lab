package ru.cobalt42.lab.dto

import ru.cobalt42.lab.model.LabConclusionObject
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeConclusionType
import ru.cobalt42.lab.model.Signers
import ru.cobalt42.lab.model.jointtubelinepart.TargetTypeClass

data class LabConclusionResponse (
    val uid: String,
    val name: String,
    val target: TargetTypeClass,
    val type: JointTubeConclusionType,
    val data: String,
    val signers: Signers,
    val isGood: Boolean,
    val conclusionObject: LabConclusionObject,
    val processUnit: String,
    val technologicalDocument: String,
    val measuringInstruments: String
    )
