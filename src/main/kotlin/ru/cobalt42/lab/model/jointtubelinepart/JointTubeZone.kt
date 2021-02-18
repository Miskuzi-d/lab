package ru.cobalt42.lab.model.jointtubelinepart

import com.fasterxml.jackson.annotation.JsonIgnore

class JointTubeZone (
    val name: String,
    val rkDefectList: List<JointTubeRkDefect>,
    @JsonIgnore
    var sumLength: Double = 0.0,
    @JsonIgnore
    var conclusionCode: String = ""
)