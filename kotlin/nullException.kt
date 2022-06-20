fun main(args: Array<String>) {
    var name: String? = null
    name = "Randolph Ledesma"
    name?.let {
        println("variable name = $it is not null")
    }

    /**
    Will throw:
    Exception in thread "main" kotlin.KotlinNullPointerException at AppKt.main(App.kt:12)
    **/
    println("Hello! ${name!!.toUpperCase()}")
}