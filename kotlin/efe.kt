/**
    Extension Function Expressions
 */

fun main(args: Array<String>) {
    db.inTransaction {
        delete("users", "first_name = ?", arrayOf("Ran"))
    }
}

fun SQLiteDatabase.inTransaction(func: SQLiteDatabase.() -> Unit) {
    beginTransaction()
    try {
        func()
        setTransactionSuccessful()
    } finally {
        endTransaction()
    }
}