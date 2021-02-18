package ru.cobalt42.lab.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.cobalt42.lab.dto.LabConclusionDTO
import ru.cobalt42.lab.dto.PaginatedResponse
import ru.cobalt42.lab.model.TubeLabConclusionPath
import ru.cobalt42.lab.service.LabConclusionHandler


@CrossOrigin
@RestController
@RequestMapping("/api/lab")
class RestLabConclusionController {

    @Autowired
    private lateinit var labConclusionHandler: LabConclusionHandler

    @PostMapping("/conclusion")
    fun processLabConclusion(@RequestBody tubeLabConclusionPath: TubeLabConclusionPath): LabConclusionDTO =
        labConclusionHandler.processLabConclusion(tubeLabConclusionPath)

    @PostMapping("/conclusion/{uid}")
    fun updateLabConclusion(@PathVariable ("uid") uid: String,@RequestBody tubeLabConclusionPath: TubeLabConclusionPath): LabConclusionDTO =
        labConclusionHandler.updateLabConclusion(uid, tubeLabConclusionPath)

    @GetMapping("/conclusion/{uid}")
    fun getLabConclusionByUid(@PathVariable uid: String) : LabConclusionDTO = labConclusionHandler.findByUid(uid)

    @GetMapping("/conclusion")
    fun getAllLabConclusions() : PaginatedResponse = labConclusionHandler.findAll()

    @DeleteMapping("/conclusion/{uid}")
    fun deleteLabConclusionByUid(@PathVariable uid: String) : String = labConclusionHandler.deleteByUid(uid)

}