package pl.pasiekaradosna.menadzerpasieki.classes.main.apiaryOLD

import java.util.*

open class Item() {
    var properties: Map<CustomProperty, Int> = EnumMap(CustomProperty::class.java)
}