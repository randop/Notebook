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

    /***
     * Order of operations
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
     ***/
}