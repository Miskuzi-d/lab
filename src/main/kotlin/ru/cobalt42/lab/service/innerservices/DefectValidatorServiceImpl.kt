package ru.cobalt42.lab.service.innerservices

import org.springframework.stereotype.Service
import ru.cobalt42.lab.model.PermitStatus
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeOthersDefect
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeRkDefect
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeZone
import ru.cobalt42.lab.model.jointtubelinepart.rk.ValueLimit
import ru.cobalt42.lab.model.jointtubelinepart.wik.WIKDefectType.*
import ru.cobalt42.lab.model.jointtubelinepart.wik.WIKTubeType
import ru.cobalt42.lab.model.jointtubelinepart.wik.WIKTubeType.*

@Service
class DefectValidatorServiceImpl : DefectValidatorService {

    var approved = true

    // Обноружение дифектов РК
    override fun DefectsValidate(wallThickness: Double, jointTubeZone: JointTubeZone): Boolean {
        if (wallThickness <= 0.0) {
            throw IllegalArgumentException("Weld Thickness can not be zero or negative")
        }
        jointTubeZone.sumLength = findSumLength(jointTubeZone.rkDefectList)
        checkDefectForLimits(jointTubeZone.rkDefectList, wallThickness, jointTubeZone)
        return approved
    }

    private fun checkDefectForLimits(
        defectsList: List<JointTubeRkDefect>,
        wallThickness: Double,
        jointTubeZone: JointTubeZone
    ) {
        defectsList.forEach { defect ->
            val mapLimits = defect.defectClass.limitMap
            val currLimits: Double = mapLimits.keys.toSortedSet()
                .find { it > wallThickness } ?: throw IllegalArgumentException(
                "Can't find this weld thickness in GOST tables"
            )
            mapLimits[currLimits]?.let { validateLimits(it, defect, jointTubeZone.sumLength) }
        }
    }

    private fun findSumLength(defectsList: List<JointTubeRkDefect>): Double {
        var sumLength = 0.0
        defectsList.forEach { sumLength += it.length }
        return sumLength
    }

    private fun validateLimits(
        valueLimit: ValueLimit,
        rkDefect: JointTubeRkDefect,
        sumLength: Double
    ) {
        rkDefect.isGood = true
        approved = true
        var limitLength = valueLimit.length
        var limitWidth = valueLimit.width
        var limitSumLength = valueLimit.sumLength

        if (rkDefect.isCluster) {
            limitLength *= 1.5
            limitWidth *= 1.5
            limitSumLength *= 1.5
        }

        if (rkDefect.length > limitLength) {
            rkDefect.lengthPermit = PermitStatus.VERIFIED_NOT_PASS
            rkDefect.isGood = false
            approved = false
        } else {
            rkDefect.lengthPermit = PermitStatus.VERIFIED_CONFIRMED
        }

        if (rkDefect.width > limitWidth) {
            rkDefect.widthPermit = PermitStatus.VERIFIED_NOT_PASS
            rkDefect.isGood = false
            approved = false
        } else {
            rkDefect.widthPermit = PermitStatus.VERIFIED_CONFIRMED
        }

        if (sumLength > limitSumLength) {
            rkDefect.sumLengthPermit = PermitStatus.VERIFIED_NOT_PASS
            rkDefect.isGood = false
            approved = false
        } else {
            rkDefect.sumLengthPermit = PermitStatus.VERIFIED_CONFIRMED
        }
    }

    // Обноружение дифектов ВИК
    override fun DefectsValidate(
        wallThickness: Double,
        jointTubeOthersDefectList: List<JointTubeOthersDefect>
    ): Boolean {
        if (wallThickness <= 0.0) {
            throw IllegalArgumentException("Weld Thickness can not be zero or negative")
        }

        val sumLength = grtSumLengthDefects(jointTubeOthersDefectList)
        var result = true

        for (defect: JointTubeOthersDefect in jointTubeOthersDefectList) {
            when (defect.defectType) {
                PORES_AND_INCLUSIONS -> defect.isGood = false
                NON_FUSIONS -> defect.isGood = false
                CRACKS -> defect.isGood = false
//                UNDERCUTS -> defect.isGood = checkUndercutsDefect(wallThickness, defect, sumLength)
                EDGE_OFFSETS -> defect.isGood =
                    checkEdgeOffsetsDefect(
                        wallThickness,
                        defect.depth.toDouble(),
                        defect.tubeType
                    )
            }
            if(!defect.isGood) result = false
        }
        return result
    }

    private fun checkEdgeOffsetsDefect(wallThickness: Double, depth: Double, tubeType: WIKTubeType): Boolean =
        when (tubeType) {
            SIGNIFICANT -> true
            ELECTROWELDED -> wallThickness >= 10 && depth <= 3.0 ||
                    wallThickness < 10 && depth >= (0.25 * wallThickness) && depth <= 2.0
        }

//    private fun checkUndercutsDefect(wallThickness: Double, defect: JointTubeOthersDefect, sumLength: Int): Boolean {
//        if(defect.depth.toDouble() < (0.1 * wallThickness) && (0.1 * wallThickness) <= 0.5 && defect.length.toDouble() <= 100 && sumLength <= 150) false
//
//    }

    private fun grtSumLengthDefects(jointTubeOthersDefectList: List<JointTubeOthersDefect>) =
        jointTubeOthersDefectList.sumBy { it.length.toInt() }

}
//
//  h<0,1S, но ≤ 0,5 мм
//
//  l ≤ 100 мм ∑300 ≤ 150 мм