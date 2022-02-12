package pl.pasiekaradosna.menadzerpasieki.classes.main.apiary

class Hives() : Item() {

    var systemName: String? = null

    constructor(systemName: String?) : this() {
        this.systemName = systemName
    }

    val x = Any::class.java


}