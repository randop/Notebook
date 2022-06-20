package com.randolphledesma

fun main(args: Array<String>) {
    var name: String? = null
    name = "Randolph Ledesma"
    name?.let {
        println("variable name = $it is not null")
    }
    println("Hello! ${name!!.toUpperCase()}")
}