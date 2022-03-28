package pl.pasiekaradosna.menadzerpasieki.classes.main.apiaryOLD

enum class Apiary(val test: String) {
    STATIONARY("Stationary"),
    MOVING("Moving");

    lateinit var hivesList: List<Hives>
}
