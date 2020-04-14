/**
    Extension Function Expressions
 */

fun main(args: Array<String>) {
    db.inTransaction {
        delete("users", "first_name = ?", arrayOf("Ran"))
    }

    preferences.edit {
        putString("foo", "bar")
        putString("fizz", "buzz")
        remove("username")
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