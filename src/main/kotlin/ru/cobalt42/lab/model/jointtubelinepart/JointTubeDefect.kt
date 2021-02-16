package ru.cobalt42.lab.model.jointtubelinepart

import com.fasterxml.jackson.annotation.JsonIgnore
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
    @JsonIgnore
    var isGood: Boolean = false,
    @JsonIgnore
    var widthPermit: PermitStatus = PermitStatus.NOT_VERIFIED,
    @JsonIgnore
    var lengthPermit: PermitStatus = PermitStatus.NOT_VERIFIED,
    @JsonIgnore
    var sumLengthPermit: PermitStatus = PermitStatus.NOT_VERIFIED

)