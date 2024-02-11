package ru.otus.otuskotlin.coroutines.homework.easy

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class HWEasy {

    @Test
    fun easyHw() = runBlocking(Dispatchers.IO) {
        val numbers = generateNumbers()
        val toFind = 10
        val toFindOther = 1000

        val foundNumbers = listOf(
            async { findNumberInList(toFind, numbers) },
            async { findNumberInList(toFindOther, numbers) },
        )

        foundNumbers.forEach {
            if (it.await() != -1) {
                println("Your number $it found!")
            } else {
                println("Not found number $toFind || $toFindOther")
            }
        }
    }
}
