package ru.cobalt42.lab.service.innerservices

import ru.cobalt42.lab.model.jointtubelinepart.JointTubeZone

interface RecordCompiler {

    fun compile(joinTubeZone: JointTubeZone): String
}