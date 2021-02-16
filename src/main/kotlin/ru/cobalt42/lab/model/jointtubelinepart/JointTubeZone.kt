package ru.cobalt42.lab.model.jointtubelinepart

import com.fasterxml.jackson.annotation.JsonIgnore

class JointTubeZone (
    val name: String,
    val defectList: List<JointTubeDefect>,
    @JsonIgnore
    var sumLength: Double = 0.0,
    @JsonIgnore
    var conclusionCode: String = ""
)