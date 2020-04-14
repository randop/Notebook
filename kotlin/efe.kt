/**
    Extension Function Expressions
 */

fun main(args: Array<String>) {
    db.inTransaction {
        delete("users", "first_name = ?", arrayOf("Ran"))
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