package ru.cobalt42.lab.model

enum class PermitStatus(message: String) {
    NOT_VERIFIED("Waiting for verification"),
    VERIFIED_CONFIRMED("In admissible values"),
    VERIFIED_NOT_PASS("Did not pass the GOST admission control")
}