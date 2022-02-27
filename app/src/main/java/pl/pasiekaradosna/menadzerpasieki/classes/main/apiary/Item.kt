package pl.pasiekaradosna.menadzerpasieki.classes.main.apiary

import java.util.*

open class Item() {
    var properties: Map<CustomProperty, Int> = EnumMap(CustomProperty::class.java)
}