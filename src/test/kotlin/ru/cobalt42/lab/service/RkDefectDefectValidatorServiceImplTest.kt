package ru.cobalt42.lab.service

import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.cobalt42.lab.model.*
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeConclusionType
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeDefect
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeZone
import ru.cobalt42.lab.model.jointtubelinepart.rk.RkDefectClass
import ru.cobalt42.lab.model.jointtubelinepart.rk.RkDefectType
import ru.cobalt42.lab.model.jointtubelinepart.TargetTypeClass
import ru.cobalt42.lab.service.innerservices.DefectValidatorService
import ru.cobalt42.lab.service.innerservices.DefectValidatorServiceImpl

@SpringBootTest
internal class RkDefectDefectValidatorServiceImplTest {

    private var validator: DefectValidatorService = DefectValidatorServiceImpl()

    private val thicknessLow = 0.2
    private val thicknessMid = 36.2
    private val thicknessExceeds = 250.0
    private val thicknessZero = 0.0
    private val thicknessNegative = -5.0
    private val thicknessUpBound = 199.9

    private val defectInBounds = JointTubeDefect(RkDefectType.OXIDE_INCLUSION,
                                    RkDefectClass.CLASS1,0.1, 0.19, false, "", "", "", isGood = false)
    private val defectLengthExceeds = JointTubeDefect(RkDefectType.PENETRATION_LACK,
                                    RkDefectClass.CLASS1,1.1, 1.6, false,"", "", "", isGood = false)
    private val defectWidthExceeds = JointTubeDefect(RkDefectType.SLAG_LONG,
                                    RkDefectClass.CLASS2,1.1, 0.4, false,"", "", "", isGood = false)
    private val defect4 = JointTubeDefect(RkDefectType.TUNGSTEN_LONG,
                                    RkDefectClass.CLASS7,1.1, 1.4, false,"", "", "", isGood = false)
    private val defect5 = JointTubeDefect(RkDefectType.PORES_INCLUSION_SPHERICAL,
                                    RkDefectClass.CLASS7,5.0, 20.0, false,"", "", "", isGood = false)
    private val defect6 = JointTubeDefect(RkDefectType.PORES_INCLUSION_SPHERICAL,
                                    RkDefectClass.CLASS7,4.1, 20.1, false,"", "", "", isGood = false)
    private val defect7 = JointTubeDefect(RkDefectType.PORES_INCLUSION_SPHERICAL,
                                    RkDefectClass.CLASS7,5.1, 19.4, false,"", "", "", isGood = false)
    private val defect8 = JointTubeDefect(RkDefectType.PORES_INCLUSION_SPHERICAL,
                                    RkDefectClass.CLASS7,5.1, 20.00000001, false,"", "", "", isGood = false)
    private val defect9 = JointTubeDefect(RkDefectType.PORES_INCLUSION_SPHERICAL,
                                    RkDefectClass.CLASS7,3.1, 19.4, false,"", "", "", isGood = false)

    private val defectsLowBoundary = listOf(defectInBounds)
    private val defectsLengthOutOfBounds = listOf(defectLengthExceeds)
    private val defectsWidthOutOfBounds = listOf(defectWidthExceeds)
    private val thicknessOutOfBounds = listOf(defect4)
    private val sumLengthOutOfBounds = listOf(defect5, defect6, defect7, defect8, defect9)

//    fun createLabConclusion(thickness: Double, defectList: List<JointTubeDefect>): LabConclusion {
//        val type = JointTubeConclusionType("RK", 1)
//        val zones = listOf(JointTubeZone("0-200", defectList))
//        val conclusionObject = LabConclusionObject.JointTubeLinePart(thickness, 3.0, zones, true)
//
//        return LabConclusion(
//            ObjectId(), "0001", "test name", TargetTypeClass("", ""), JointTubeConclusionType("RK", 1),
//            "2021-12-31", Signer(Laboratory("", "", ""), Construct("", "")),
//            isGood = true, conclusionObject, "", "", ""
//        )
//    }

    @Test
    fun testLowThicknessAllInBounds() {
        assertEquals(PermitStatus.NOT_VERIFIED, defectInBounds.lengthPermit)
        assertEquals(PermitStatus.NOT_VERIFIED, defectInBounds.widthPermit)

//        val conclusion = createLabConclusion(thicknessLow,)
//
//        validator.defectsValidate()

        assertEquals(PermitStatus.VERIFIED_CONFIRMED, defectInBounds.lengthPermit)
        assertEquals(PermitStatus.VERIFIED_CONFIRMED, defectInBounds.widthPermit)
        assertEquals(PermitStatus.VERIFIED_CONFIRMED, defectInBounds.sumLengthPermit)
    }

    @Test
    fun testMidThicknessLengthOutOfBounds() {
        assertEquals(PermitStatus.NOT_VERIFIED, defectLengthExceeds.lengthPermit)
        assertEquals(PermitStatus.NOT_VERIFIED, defectLengthExceeds.widthPermit)

//        validator.defectsValidate(thicknessMid, defectsLengthOutOfBounds)

        assertEquals(PermitStatus.VERIFIED_NOT_PASS, defectLengthExceeds.lengthPermit)
        assertEquals(PermitStatus.VERIFIED_CONFIRMED, defectLengthExceeds.widthPermit)
        assertEquals(PermitStatus.VERIFIED_CONFIRMED, defectLengthExceeds.sumLengthPermit)
    }

    @Test
    fun testLowThicknessWidthOutOfBounds() {
        assertEquals(PermitStatus.NOT_VERIFIED, defectWidthExceeds.lengthPermit)
        assertEquals(PermitStatus.NOT_VERIFIED, defectWidthExceeds.widthPermit)

//        validator.defectsValidate(thicknessLow, defectsWidthOutOfBounds)

        assertEquals(PermitStatus.VERIFIED_CONFIRMED, defectWidthExceeds.lengthPermit)
        assertEquals(PermitStatus.VERIFIED_NOT_PASS, defectWidthExceeds.widthPermit)
        assertEquals(PermitStatus.VERIFIED_CONFIRMED, defectWidthExceeds.sumLengthPermit)
    }

    @Test
    fun testBigThicknessThrownException() {
        assertEquals(PermitStatus.NOT_VERIFIED, defect4.lengthPermit)
        assertEquals(PermitStatus.NOT_VERIFIED, defect4.widthPermit)

//        assertThrows(
//            IllegalArgumentException::class.java
//        ) { validator.defectsValidate(thicknessExceeds, thicknessOutOfBounds) }
    }

    @Test
    fun testNegativeThrownException() {
        assertEquals(PermitStatus.NOT_VERIFIED, defect4.lengthPermit)
        assertEquals(PermitStatus.NOT_VERIFIED, defect4.widthPermit)

//        assertThrows(
//            IllegalArgumentException::class.java
//        ) { validator.defectsValidate(thicknessNegative, thicknessOutOfBounds) }
    }

    @Test
    fun testZeroThicknessThrownException() {
        assertEquals(PermitStatus.NOT_VERIFIED, defect4.lengthPermit)
        assertEquals(PermitStatus.NOT_VERIFIED, defect4.widthPermit)

//        assertThrows(
//            IllegalArgumentException::class.java
//        ) { validator.defectsValidate(thicknessZero, thicknessOutOfBounds) }
    }

    @Test
    fun sumLengthOutOfBounds() {
        assertEquals(PermitStatus.NOT_VERIFIED, defect5.lengthPermit)
        assertEquals(PermitStatus.NOT_VERIFIED, defect5.widthPermit)

        assertEquals(PermitStatus.NOT_VERIFIED, defect9.lengthPermit)
        assertEquals(PermitStatus.NOT_VERIFIED, defect9.widthPermit)

//        validator.defectsValidate(thicknessUpBound, sumLengthOutOfBounds)

        for (defect: JointTubeDefect in sumLengthOutOfBounds) {
            assertEquals(PermitStatus.VERIFIED_NOT_PASS, defect.sumLengthPermit)
        }
    }

    @Test
    fun differentResultsInList() {
        assertEquals(PermitStatus.NOT_VERIFIED, defect5.lengthPermit)
        assertEquals(PermitStatus.NOT_VERIFIED, defect5.widthPermit)
        assertEquals(PermitStatus.NOT_VERIFIED, defect9.lengthPermit)
        assertEquals(PermitStatus.NOT_VERIFIED, defect9.widthPermit)

//        validator.defectsValidate(thicknessUpBound, sumLengthOutOfBounds)

        assertEquals(PermitStatus.VERIFIED_CONFIRMED, defect5.lengthPermit)
        assertEquals(PermitStatus.VERIFIED_CONFIRMED, defect5.widthPermit)

        assertEquals(PermitStatus.VERIFIED_NOT_PASS, defect6.lengthPermit)
        assertEquals(PermitStatus.VERIFIED_CONFIRMED, defect6.widthPermit)

        assertEquals(PermitStatus.VERIFIED_CONFIRMED, defect7.lengthPermit)
        assertEquals(PermitStatus.VERIFIED_NOT_PASS, defect7.widthPermit)

        assertEquals(PermitStatus.VERIFIED_NOT_PASS, defect8.lengthPermit)
        assertEquals(PermitStatus.VERIFIED_NOT_PASS, defect8.widthPermit)


    }


}