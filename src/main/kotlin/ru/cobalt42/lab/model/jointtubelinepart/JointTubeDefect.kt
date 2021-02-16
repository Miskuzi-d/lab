package ru.cobalt42.lab.model.jointtubelinepart

import ru.cobalt42.lab.model.PermitStatus
import ru.cobalt42.lab.model.jointtubelinepart.rk.RkDefectClass
import ru.cobalt42.lab.model.jointtubelinepart.rk.RkDefectType

data class JointTubeDefect (
    val defectType: RkDefectType,
    val defectClass: RkDefectClass,
    val width : Double = 0.0,
    val length: Double = 0.0,
    val isCluster: Boolean,
    val snapshotParameters: String,
    val descriptionDefectedFlows: String,
    val comment: String,
    var isGood: Boolean,
    var widthPermit: PermitStatus = PermitStatus.NOT_VERIFIED,
    var lengthPermit: PermitStatus = PermitStatus.NOT_VERIFIED,
    var sumLengthPermit: PermitStatus = PermitStatus.NOT_VERIFIED

)