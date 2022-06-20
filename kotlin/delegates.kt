fun main(args: Array<String>) {

    //Example of read-only property delegate
    val someProperty by object {
        operator fun getValue(thisRef: Any?, property: KProperty<*>) = "Something"
    }
}