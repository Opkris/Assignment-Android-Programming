package no.kristiania.assignment_noforeignland.models

data class Feature(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)