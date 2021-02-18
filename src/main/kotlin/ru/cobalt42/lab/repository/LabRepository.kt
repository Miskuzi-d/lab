package ru.cobalt42.lab.repository

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import ru.cobalt42.lab.model.TubeLabConclusionPath

interface LabRepository : MongoRepository<TubeLabConclusionPath, ObjectId> {

    fun findByUid(uid: String): TubeLabConclusionPath

    fun deleteByUid(uid: String)

}
