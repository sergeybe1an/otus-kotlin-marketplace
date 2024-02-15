package ru.otus.otuskotlin.coroutines.homework.hard

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.test.Test

class HWHard {
    @Test
    fun hardHw(): Unit = runBlocking(Dispatchers.IO) {
        val dictionaryApi = DictionaryApi()
        val words = FileReader.readFile().split(" ", "\n").toSet()

        val dictionaries = findWords(dictionaryApi, words, Locale.EN)

        dictionaries.map { dictionaryDeff ->
            val dictionary = dictionaryDeff.await()
            print("For word ${dictionary?.word} i found examples: ")
            println(
                dictionary?.meanings
                    ?.mapNotNull { definition ->
                        val r = definition.definitions
                            .mapNotNull { it.example.takeIf { it?.isNotBlank() == true } }
                            .takeIf { it.isNotEmpty() }
                        r
                    }
                    .takeIf { it?.isNotEmpty() ?: false }
            )
        }
    }

    private fun findWords(
        dictionaryApi: DictionaryApi,
        words: Set<String>,
        @Suppress("SameParameterValue") locale: Locale
    ) = runBlocking(Dispatchers.IO) {
        // make some suspensions and async
        words.map {
            Thread.sleep(50L)
            async { dictionaryApi.findWord(locale, it) }
        }
    }

    object FileReader {
        fun readFile(): String =
            File(
                this::class.java.classLoader.getResource("words.txt")?.toURI()
                    ?: throw RuntimeException("Can't read file")
            ).readText()
    }
}
