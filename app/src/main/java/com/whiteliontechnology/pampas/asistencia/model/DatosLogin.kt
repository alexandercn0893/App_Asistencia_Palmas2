package com.whiteliontechnology.pampas.asistencia.model

import com.google.gson.annotations.SerializedName


data class LoginData(
    @SerializedName("NumDocIde") val numDocIde: String,
    @SerializedName("CodTra") val codTra: String,
    @SerializedName("Nombres") val nombres: String,
    @SerializedName("CodCia") val codCia: String,
    @SerializedName("NomCia") val nomCia: String,
    @SerializedName("CodigoPin") val codigoPin: Int,
    @SerializedName("Telefono") val telefono: String?,
    @SerializedName("Areas") val areas: List<Area>
)

data class Area(
    @SerializedName("CodTra") val codTra: String,
    @SerializedName("CodAre") val codAre: String,
    @SerializedName("DesAre") val desAre: String,
    @SerializedName("Latitud") val latitud: String,
    @SerializedName("Longitud") val longitud: String,
    @SerializedName("Radio") val radio: String
)
