package ru.cobalt42.lab.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeZone

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "className")
@JsonSubTypes(
    JsonSubTypes.Type(value = LabConclusionObject.JointTubeLinePart::class, name = "JointTubeLinePart"))
sealed class LabConclusionObject {

    data class JointTubeLinePart(
        val wallThickness: Double,
        val diameter: Double,
        val zones: List<JointTubeZone>,
        var isGood: Boolean = false,

    ) : LabConclusionObject()

    // place for another objects for analysis
}