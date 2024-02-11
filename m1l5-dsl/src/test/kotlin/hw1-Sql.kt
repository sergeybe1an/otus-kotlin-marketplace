@file:Suppress("unused")

package ru.otus.otuskotlin.m1l5

import ru.otus.otuskotlin.m1l5.dsl.UserDsl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

// Реализуйте dsl для составления sql запроса, чтобы все тесты стали зелеными.
class Hw1Sql {
    private fun checkSQL(expected: String, sql: SqlSelectBuilder) {
        assertEquals(expected, sql.build())
    }

    @Test
    fun `simple select all from table`() {
        val expected = "select * from table"

        val real = query {
            from("table")
        }

        checkSQL(expected, real)
    }

    @Test
    fun `check that select can't be used without table`() {
        assertFailsWith<Exception> {
            query {
                select("col_a")
            }.build()
        }
    }

    @Test
    fun `select certain columns from table`() {
        val expected = "select col_a, col_b from table"

        val real = query {
            select("col_a", "col_b")
            from("table")
        }

        checkSQL(expected, real)
    }

    @Test
    fun `select certain columns from table 1`() {
        val expected = "select col_a, col_b from table"

        val real = query {
            select("col_a", "col_b")
            from("table")
        }

        checkSQL(expected, real)
    }

    /**
     * __eq__ is "equals" function. Must be one of char:
     *  - for strings - "="
     *  - for numbers - "="
     *  - for null - "is"
     */
    @Test
    fun `select with complex where condition with one condition`() {
        val expected = "select * from table where col_a = 'id'"

        val real = query {
            from("table")
            where { "col_a" eq "id" }
        }

        checkSQL(expected, real)
    }

    @Test
    fun `select with complex where condition with one condition1`() {
        val expected = "select * from table where col_a is null"

        val real = query {
            from("table")
            where { "col_a" eq null }
        }

        checkSQL(expected, real)
    }

    @Test
    fun `select with complex where condition with one condition2`() {
        val expected = "select * from table where col_a = 4.5"

        val real = query {
            from("table")
            where { "col_a" eq 4.5 }
        }

        checkSQL(expected, real)
    }

    /**
     * __nonEq__ is "non equals" function. Must be one of chars:
     *  - for strings - "!="
     *  - for numbers - "!="
     *  - for null - "!is"
     */
    @Test
    fun `select with complex where condition with two conditions`() {
        val expected = "select * from table where col_a != 0"

        val real = query {
            from("table")
            where {
                "col_a" nonEq 0
            }
        }

        checkSQL(expected, real)
    }

//    @Test
//    fun `when 'or' conditions are specified then they are respected`() {
//        val expected = "select * from table where (col_a = 4 or col_b !is null)"
//
//        val real = query {
//            from("table")
//            where {
//                or {
//                    "col_a" eq 4
//                    "col_b" nonEq null
//                }
//            }
//        }
//
//        checkSQL(expected, real)
//    }
}

fun query(function: SqlSelectBuilder.() -> Unit): SqlSelectBuilder =
    SqlSelectBuilder().apply(function)

fun SqlSelectBuilder.select(vararg selectors: String) = select(*selectors)
fun SqlSelectBuilder.from(table: String) = from(table)

fun SqlSelectBuilder.where(function: SqlWhereContext.() -> Unit) =
    SqlWhereContext().apply(function)

infix fun String.eq(that: String?): String = if (that == null) "$this is null" else "$this = '$that'"
infix fun String.eq(that: Number): String = "$this = $that"

infix fun String.nonEq(that: String?): String = if (that == null) "$this !is null" else "$this != '$that'"
infix fun String.nonEq(that: Number): String = "$this != $that"





@UserDsl
class SqlSelectBuilder {

    private var select = "select * "
    private var from = ""
    private var where = ""

    fun select(vararg selectors: String) {
        select = "select ${selectors.joinToString(", ")} "
    }
    fun from(table: String) {
        from = "from $table"
    }

    fun where(ctx: () -> String) {
//        val ctx = SqlWhereContext().apply(block)

        where = " where ${ctx.invoke()}"
    }

    fun build(): String =
        if (from == "") throw Exception()
        else "$select$from$where"
}

@UserDsl
class SqlWhereContext {
    private var _where: String = " where "

    fun or(block: SqlWhereContext.() -> String) {
//        val ctx = SqlWhereOrContext().apply(block)

        _where = " where $block"
    }

    fun where(where: String) {
        this._where = " where $where"
    }

    fun build(): String = this._where
}
