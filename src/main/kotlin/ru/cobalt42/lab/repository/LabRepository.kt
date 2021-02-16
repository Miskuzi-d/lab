package ru.cobalt42.lab.repository

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import ru.cobalt42.lab.model.LabConclusion

interface LabRepository : MongoRepository<LabConclusion, ObjectId> {

    fun findByUid(uid: String): LabConclusion

    fun deleteByUid(uid: String)

}
