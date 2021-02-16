@file:Suppress("unused", "unused", "unused", "unused")

package ru.cobalt42.lab.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeConclusionType

@Document
class LabConclusion(

    @Id
    var _id: ObjectId = ObjectId.get(),
    var uid: String?,
    val name: String,
    val type: JointTubeConclusionType,
    val data: String,
    val signers: Signers,
    var isGood: Boolean = true,
    val conclusionObject: LabConclusionObject,
    val processUnit: String,
    val technologicalDocument: String,
    val measuringInstruments: String

)