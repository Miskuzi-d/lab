package ru.cobalt42.lab.service.innerservices

import ru.cobalt42.lab.model.LabConclusionObject
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeZone

interface DefectValidatorService {
    fun defectsValidate(wallThickness: Double,
                        jointTubeZone: JointTubeZone
    ): Boolean
}