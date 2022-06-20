package com.randolphledesma

import java.util.*

fun main(args: Array<String>) {
    println("Hello World!")

    //Single line comment

    /* Multi line
        comment
     */

    val number: Int = 0 //Immutable integer variable

    var name: String = "" //Mutable string variable
    name = "Randolph Ledesma"

    /*
    Arithmetic operations
     */
    println(1 + 1)
    println(3 - 2)
    println(3 * 4)
    println(12 / 6)
    println(22.0 / 7.0)
    println(1 + 1_000_000)

    /* Remainder operations */
    println(28 % 10)
    println("%.0f".format(19.0 % 9.0))

    /* Shift operations */
    println(1 shl 3)
    println(32 shr 2)

    /* Comparison operations */
    println(3 < 4);
    println(3 == 4);
    println(4 >= 3);
    println(4 != 3);

    /* Range operator */
    for (i in 1..14 step 3) {
        println(i)
    }

    /*
    The double colon operator (::) is used to create a class or a function reference.
     */
    val c = String::class
    c.supertypes.forEach { e -> println(e) }
    val words = listOf("Bible", "alpha", "omega")
    println(words.map(String::length))

    /* Lambda operator */
    val someWords = arrayOf("kind", "massive", "atom", "car", "blue")
    Arrays.sort(someWords) { s1: String, s2: String -> s1.compareTo(s2) }
    println(Arrays.toString(someWords))

    /* String concatenations */
    println("Return " + "of " + "Jesus.")
    println("Return".plus(" of").plus(" Jesus."))

    //String template
    println("Hello $name")

    //Multi-line strings
    val verse = """
        But he answered and said, It is written, Man shall not live by bread alone, 
        but by every word that proceedeth out of the mouth of God.
        - Matthew 4:4
    """.trimIndent()
    println(verse)

    //Multi-line strings
    val bigString = """
        |You can have a string
        |that contains multiple
        |lines
        |by
        |doing this.
        """.trimMargin()
    println(bigString)

    /* Pairs */
    val coordinates: Pair<Int, Int> = Pair(2, 3)
    val coordinatesInferred = Pair(2, 3) //inferred
    val coordinatesWithTo = 2 to 3 //concise
    println("Coordinates: ${coordinates.first},${coordinates.second}")

    /* Triple */
    val coordinates3D = Triple(2, 3, 1)
    println("3D Coordinates: ${coordinates3D.first},${coordinates3D.second},${coordinates3D.third}")

    /***
    Order of operations:
    +------------+-----------------+------------------------+
    | Precedence |      Title      |        Symbols         |
    +------------+-----------------+------------------------+
    | Highest    | Postfix         | ++, --, ., ?., ?       |
    |            | Prefix          | -, +, ++, --, !, label |
    |            | Type RHS        | :, as, as?             |
    |            | Multiplicative  | *, /, %                |
    |            | Additive        | +, -                   |
    |            | Range           | ..                     |
    |            | Infix function  | simpleIdentifier       |
    |            | Elvis           | ?:                     |
    |            | Named checks    | in, !in, is, !is       |
    |            | Comparison      | <, >, <=, >=           |
    |            | Equality        | ==, !==                |
    |            | Conjunction     | &&                     |
    |            | Disjunction     | ||                     |
    |            | Spread operator | *                      |
    | Lowest     | Assignment      | =, +=, -=, *=, /=, %=  |
    +------------+-----------------+------------------------+

    Integer types:
    +-------+------+----------------------+---------------------+
    | Type  | Bits |      Min value       |      Max value      |
    +-------+------+----------------------+---------------------+
    | Long  |   64 | -9223372036854775808 | 9223372036854775807 |
    | Int   |   32 |          -2147483648 |          2147483647 |
    | Short |   16 |               -32768 |               32767 |
    | Byte  |    8 |                 -128 |                 127 |
    +-------+------+----------------------+---------------------+

    Floating-point and other types
    +---------+------+----------------------------------------------------------------------------------------------------------------------------------------------------+
    |  Type   | Bits |                                                                       Notes                                                                        |
    +---------+------+----------------------------------------------------------------------------------------------------------------------------------------------------+
    | Double  |   64 | 16-17 significant digits (same as float in Python)                                                                                                 |
    | Float   |   32 | 6-7 significant digits                                                                                                                             |
    | Char    |   16 | UTF-16 code unit (see the section on strings - in most cases, this is one Unicode character, but it might be just one half of a Unicode character) |
    | Boolean |    8 | true or false                                                                                                                                      |
    +---------+------+----------------------------------------------------------------------------------------------------------------------------------------------------+
     ***/
}