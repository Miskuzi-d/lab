package ru.cobalt42.lab.service.innerservices

import org.springframework.stereotype.Service
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeOthersDefect
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeRkDefect
import ru.cobalt42.lab.model.jointtubelinepart.JointTubeZone
import kotlin.text.StringBuilder

@Service
class RecordCompilerImpl : RecordCompiler {

    override fun compile(anyTubeZone: Any): String {

        val map = mutableMapOf<String, Int>()
        val result = StringBuilder("")

        when(anyTubeZone) {
            is JointTubeZone -> {
                val defectList = anyTubeZone.rkDefectList

                fillMapOfDefectCodes(defectList, map)
                appendCodesAndCounts(map, result)
                appendSumLength(defectList, result, anyTubeZone)
            }
            is JointTubeOthersDefect -> {

            }
        }
        return result.toString()
    }

    private fun fillMapOfDefectCodes(rkDefectList: List<JointTubeRkDefect>,
                                     map: MutableMap<String, Int>) {
        for (rkDefect: JointTubeRkDefect in rkDefectList) {
            val record = createRecord(rkDefect)
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

    private fun appendSumLength(rkDefectList: List<JointTubeRkDefect>,
                                result: StringBuilder,
                                joinTubeZone: JointTubeZone) {
        if (rkDefectList.size > 1) {
            result.append("Σ ").append(joinTubeZone.sumLength)
        } else {
            result.deleteRange(result.length - 2, result.length - 1)
        }
    }

    private fun createRecord(rkDefect: JointTubeRkDefect): String {
        var result = rkDefect.defectType.codeRus

        if (rkDefect.length > 0 && rkDefect.width > 0) {
            result = result + rkDefect.length + "x" + rkDefect.width
        } else if (rkDefect.length > 0) {
            result += rkDefect.length
        } else if (rkDefect.width > 0) {
            result += rkDefect.width
        } else {
            result = ""
        }
        return result
    }
}