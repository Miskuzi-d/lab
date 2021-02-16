package ru.cobalt42.lab.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.cobalt42.lab.dto.LabConclusionResponse
import ru.cobalt42.lab.dto.ListConclusionResponse
import ru.cobalt42.lab.model.LabConclusion
import ru.cobalt42.lab.service.LabConclusionHandler


@CrossOrigin
@RestController
@RequestMapping("/api/lab")
class RestLabConclusionController {

    @Autowired
    private lateinit var labConclusionHandler: LabConclusionHandler

    @PostMapping("/conclusion")
    fun processLabConclusion(@RequestBody labRequest: LabConclusion): LabConclusionResponse =
        labConclusionHandler.processLabConclusion(labRequest)

    @PostMapping("/conclusion/{uid}")
    fun updateLabConclusion(@RequestBody labRequest: LabConclusion): LabConclusionResponse =
        labConclusionHandler.updateLabConclusion(labRequest)

    @GetMapping("/conclusion/{uid}")
    fun getLabConclusionByUid(@PathVariable uid: String) : LabConclusionResponse = labConclusionHandler.findByUid(uid)

    @GetMapping("/conclusion")
    fun getAllLabConclusions() : ListConclusionResponse = labConclusionHandler.findAll()

    @DeleteMapping("/conclusion/{uid}")
    fun deleteLabConclusionByUid(@PathVariable uid: String) : String = labConclusionHandler.deleteByUid(uid)

}