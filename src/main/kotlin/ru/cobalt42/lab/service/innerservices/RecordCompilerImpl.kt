package ru.cobalt42.lab.service.innerservices

import org.springframework.stereotype.Service
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeDefect
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeZone
import kotlin.text.StringBuilder

@Service
class RecordCompilerImpl : RecordCompiler {

    override fun compile(joinTubeZone: JointTubeZone): String {
        val defectList = joinTubeZone.defectList
        val map = mutableMapOf<String, Int>()
        val result = StringBuilder("")

        fillMapOfDefectCodes(defectList, map)
        appendCodesAndCounts(map, result)
        appendSumLength(defectList, result, joinTubeZone)

        return result.toString()
    }

    private fun fillMapOfDefectCodes(defectList: List<JointTubeDefect>,
                                    map: MutableMap<String, Int>) {
        for (defect: JointTubeDefect in defectList) {
            val record = createRecord(defect)
            if (map.containsKey(record)) {
                map[record] = map.getValue(record).inc()
            } else {
                map[record] = 1
            }
        }
    }

    private fun appendCodesAndCounts(map: MutableMap<String, Int>,
                                     result: StringBuilder) {
        for (record: String in map.keys) {
            if (map.getValue(record) > 1) {
                result.append(map[record]).append(record)

            } else {
                result.append(record)
            }
            result.append("; ")
        }
    }

    private fun appendSumLength(defectList: List<JointTubeDefect>,
                                result: StringBuilder,
                                joinTubeZone: JointTubeZone) {
        if (defectList.size > 1) {
            result.append("Σ ").append(joinTubeZone.sumLength)
        } else {
            result.deleteRange(result.length - 2, result.length - 1)
        }
    }

    private fun createRecord(defect: JointTubeDefect): String {
        var result = defect.defectType.codeRus

        if (defect.length > 0 && defect.width > 0) {
            result = result + defect.length + "x" + defect.width
        } else if (defect.length > 0) {
            result += defect.length
        } else if (defect.width > 0) {
            result += defect.width
        } else {
            result = ""
        }
        return result
    }
}