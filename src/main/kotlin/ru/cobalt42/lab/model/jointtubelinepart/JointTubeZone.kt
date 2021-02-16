package ru.cobalt42.lab.model.jointtubelinepart

class JointTubeZone (
    val name: String,
    val defectList: List<JointTubeDefect>,
    var sumLength: Double = 0.0,
    var conclusionCode: String = ""
)