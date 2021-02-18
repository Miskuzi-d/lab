package ru.cobalt42.lab.service.innerservices

import ru.cobalt42.lab.model.jointtubelinepart.JointTubeOthersDefect
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeZone

interface DefectValidatorService {

    // Обноружение дифектов РК
    fun DefectsValidate(wallThickness: Double, jointTubeZone: JointTubeZone): Boolean

    // Обноружение дифектов ВИК
    fun DefectsValidate(wallThickness: Double, jointTubeOthersDefectList: List<JointTubeOthersDefect>): Boolean
}