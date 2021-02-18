package ru.cobalt42.lab.model.jointtubelinepart

import com.fasterxml.jackson.annotation.JsonIgnore
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.IgnoreForBinding
import ru.cobalt42.lab.model.jointtubelinepart.wik.WIKDefectType
import ru.cobalt42.lab.model.jointtubelinepart.wik.WIKTubeType

data class JointTubeOthersDefect(
    val tubeType: WIKTubeType,
    val defectType: WIKDefectType,
    val depth: String,
    val length: String,
    val descriptionDefectedFlows: String,
    @JsonIgnore
    var isGood: Boolean = true
)