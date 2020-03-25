/**
Kotlin stdlib provide a set of exention functions:
let, apply, also, with, run, to
*/

fun main(args: Array<String>) {

    val mutableList = mutableListOf(1)
    val letResult = mutableList.let {
        it.add(2)
        listOf("a", "b", "c")
    }
    println(letResult) //Prints: [a, b, c]
    val applyResult = mutableList.apply {
        add(3)
        listOf("a", "b", "c")
    }
    println(applyResult) //Prints: [1, 2, 3]
    val alsoResult = mutableList.also {
        it.add(4)
        listOf("A", "B", "C")
    }
    println(alsoResult) //Prints: [1, 2, 3, 4]
    val runResult = mutableList.run {
        add(5)
        listOf("A", "B", "C")
    }
    println(runResult) //Prints: [A, B, C]
    val withResult = with(mutableList) {
        add(6)
        listOf("A", "B", "C")
    }
    println(withResult) //Prints: [A, B, C]
    println(mutableList) //Prints: [1, 2, 3, 4, 5, 6]

}