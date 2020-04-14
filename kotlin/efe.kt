/**
    Extension Function Expressions
 */

fun main(args: Array<String>) {
    db.inTransaction {
        db.delete("users", "first_name = ?", arrayOf("Ran"))
    }
}

fun SQLiteDatabase.inTransaction(func: () -> Unit) {
    beginTransaction()
    try {
        func()
        setTransactionSuccessful()
    } finally {
        endTransaction()
    }
}