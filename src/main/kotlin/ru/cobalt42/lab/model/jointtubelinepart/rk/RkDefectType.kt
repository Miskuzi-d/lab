package ru.cobalt42.lab.model.jointtubelinepart.rk

enum class RkDefectType(val codeRus: String) {
    PORES_INCLUSION_SPHERICAL("П"),
    PORES_INCLUSION_LONG("П"),
    SLAG_SPHERICAL("Ш"),
    SLAG_LONG("Ш"),
    TUNGSTEN_SPHERICAL("В"),
    TUNGSTEN_LONG("В"),
    PENETRATION_LACK("Н"),
    CRACK("Т"),
    OXIDE_INCLUSION("О")

    
}