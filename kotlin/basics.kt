package com.randolphledesma

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
}