package com.tmelo.missasapp

data class MissasData(
    val paroquias: List<Paroquia>
)

data class Paroquia(
    val nome: String,
    val capelas: List<Capela>
)

data class Capela(
    val nome: String,
    val horarios: List<String>
)
