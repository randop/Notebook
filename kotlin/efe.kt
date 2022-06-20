/**
    Extension Function Expressions
 */

fun main(args: Array<String>) {
    db.inTransaction {
        delete("users", "first_name = ?", arrayOf("Ran"))
    }

    preferences.edit {
        set("foo" to "bar")
        set("fizz" to "buzz")
        remove("username")
    }

    val n = notification(context) {
        setContentTitle("Hi")
        setSubText("Hello")
    }
}

inline fun SQLiteDatabase.inTransaction(func: SQLiteDatabase.() -> Unit) {
    beginTransaction()
    try {
        func()
        setTransactionSuccessful()
    } finally {
        endTransaction()
    }
}

inline fun SharedPreferences.edit(func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    editor.apply()
}

fun SharedPreferences.Editor.set(pair: Pair<String, String>) = putString(pair.first, pair.second)

inline fun notification(context: Context, func: Notification.Builder.() -> Unit): Notification {
	val builder = Notification.Builder(context)
	builder.func()
	return builder.build()
}