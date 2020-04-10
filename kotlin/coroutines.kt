import kotlinx.coroutines.*

fun main(args: Array<String>) {
    GlobalScope.launch {
        println("Coroutines are fun :D")
    }
}