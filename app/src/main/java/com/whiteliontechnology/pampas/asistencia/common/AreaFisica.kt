package com.whiteliontechnology.pampas.asistencia.common

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required


open class Area : RealmObject() {
    @PrimaryKey
    var CodAre: String = ""

    var CodTra: String = ""
    var DesAre: String = ""
    var Latitud: String = ""
    var Longitud: String = ""
    var Radio: String = ""
}
