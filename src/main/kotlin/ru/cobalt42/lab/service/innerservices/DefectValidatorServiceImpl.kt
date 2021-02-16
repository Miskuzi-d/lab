package ru.cobalt42.lab.service.innerservices

import org.springframework.stereotype.Service
import ru.cobalt42.lab.model.PermitStatus
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeDefect
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeZone
import ru.cobalt42.lab.model.jointtubelinepart.rk.ValueLimit

@Service
class DefectValidatorServiceImpl : DefectValidatorService{

    var approved = true

    override fun defectsValidate(wallThickness: Double,
                                 jointTubeZone: JointTubeZone): Boolean {
        if (wallThickness <= 0.0) {
            throw IllegalArgumentException("Weld Thickness can not be zero or negative")
        }
        jointTubeZone.sumLength = findSumLength(jointTubeZone.defectList)
        checkDefectForLimits(jointTubeZone.defectList, wallThickness, jointTubeZone)
        return approved
    }

    private fun checkDefectForLimits(defectsList: List<JointTubeDefect>,
                                     wallThickness: Double,
                                     jointTubeZone: JointTubeZone) {
        defectsList.forEach { defect ->
            val mapLimits = defect.defectClass.limitMap
            val currLimits: Double = mapLimits.keys.toSortedSet()
                .find { it > wallThickness } ?: throw IllegalArgumentException(
                "Can't find this weld thickness in GOST tables"
            )
            mapLimits[currLimits]?.let { validateLimits(it, defect, jointTubeZone.sumLength) }
        }
    }

    private fun findSumLength(defectsList: List<JointTubeDefect>): Double {
        var sumLength = 0.0
        defectsList.forEach { sumLength += it.length }
        return sumLength
    }

    private fun validateLimits(valueLimit: ValueLimit,
                               defect: JointTubeDefect,
                               sumLength: Double) {
        defect.isGood = true
        approved = true
        var limitLength = valueLimit.length
        var limitWidth = valueLimit.width
        var limitSumLength = valueLimit.sumLength

        if(defect.isCluster) {
            limitLength *= 1.5
            limitWidth *= 1.5
            limitSumLength *= 1.5
        }

        if (defect.length > limitLength) {
            defect.lengthPermit = PermitStatus.VERIFIED_NOT_PASS
            defect.isGood = false
            approved = false
        } else {
            defect.lengthPermit = PermitStatus.VERIFIED_CONFIRMED
        }

        if (defect.width > limitWidth) {
            defect.widthPermit = PermitStatus.VERIFIED_NOT_PASS
            defect.isGood = false
            approved = false
        } else {
            defect.widthPermit = PermitStatus.VERIFIED_CONFIRMED
        }

        if (sumLength > limitSumLength) {
            defect.sumLengthPermit = PermitStatus.VERIFIED_NOT_PASS
            defect.isGood = false
            approved = false
        } else {
            defect.sumLengthPermit = PermitStatus.VERIFIED_CONFIRMED
        }
    }

}