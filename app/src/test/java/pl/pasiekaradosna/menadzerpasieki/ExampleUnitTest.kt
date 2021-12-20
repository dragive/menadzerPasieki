package pl.pasiekaradosna.menadzerpasieki

import org.junit.Test

import org.junit.Assert.*
import pl.pasiekaradosna.menadzerpasieki.classes.debug.constructorTest.ConstructorTest

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun constructor(){
        val constructorTest = ConstructorTest()

        constructorTest.data = "a"
        assertEquals("a",constructorTest.data)

    }
}