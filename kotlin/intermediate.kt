package com.randolphledesma

import kotlinx.coroutines.*
import kotlin.properties.Delegates.observable
import kotlin.properties.Delegates.vetoable
import kotlin.reflect.KProperty

class User(val map: MutableMap<String, Any>) {
    private var name: String by map
    private var programmer: Boolean by map

    override fun toString(): String = "Name: $name, Programmer: $programmer"
}

fun getIndices(count: Int): List<Int> {
    require(count >= 0) { "Count must be non-negative, was $count" }
    // ...
    return List(count) { it + 1 }
}

suspend fun getIndicesNew(count: Int): List<Int> {
    require(count >= 0) { "Count must be non-negative, was $count" }
    return List(count) { it + 1 }
}

fun main(args: Array<String>) {

    GlobalScope.launch {
        println(getIndicesNew(10))
    }

    val map = mutableMapOf(
        "name" to "Randolph",
        "programmer" to true
    )

    map.put("name", "Ledesma")

    val user = User(map)
    println(user)

    var list: MutableList<Int> by observable(mutableListOf()) {
        _, old, new ->
        println("List changed from $old to $new")
    }

    var newList: List<String> by vetoable(emptyList()) {
        _, old, new ->
        if (new.size < old.size) {
            println("invalid size")
            return@vetoable false
        }
        println("good size")
        true
    }

    newList = listOf("A", "B", "C")
    println(newList)
    newList = listOf("A")
    println(newList)

    list.add(1)
    list = mutableListOf(2, 3)

    val someProperty by object {
        operator fun getValue(thisRef: Any?, property: KProperty<*>) = "Something"
    }
    println(someProperty.decapitalize())
    Thread.sleep(1_000)
}