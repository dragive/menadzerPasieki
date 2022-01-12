package pl.pasiekaradosna.menadzerpasieki.classes.main.apiary

import java.util.*
import kotlin.collections.HashMap

enum class Apiary(val test:String) {
    STATIONARY("Stationary"),
    MOVING("Moving");

    lateinit var hivesList: List<Hive>
    var customProperties = EnumMap(HashMap<CustomProperty,Any>())


}
