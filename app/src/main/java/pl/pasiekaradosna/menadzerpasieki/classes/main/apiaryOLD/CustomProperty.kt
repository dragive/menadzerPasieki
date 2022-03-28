package pl.pasiekaradosna.menadzerpasieki.classes.main.apiaryOLD

import kotlin.reflect.KClass

//TODO wykorzystanie klasy
enum class CustomProperty(value: Int, type: KClass<*>) {
    HEIGHT(0, Number::class),
    WIDTH(0, Any::class),
    DEPTH(0, Any::class),
    SIZE(0, Any::class),
    AMOUNT(0, Any::class),
    MOOD(0, Any::class),
    WEATHER_SUN(0, Any::class),
    WEATHER_TEMP(0, Any::class),
    WEATHER_WIND(0, Any::class),
    BEE_NOISE(0, Any::class)


}
