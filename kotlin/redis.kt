package demo

import redis.clients.jedis.Jedis
import java.util.UUID

fun main(args: Array<String>) {    
    try {        
        val jedis = Jedis("192.168.186.192")
        jedis.set("foo", "bar")
        val value = jedis.get("foo")
        println(value)

        try {
            val lockID = acquireLockWithTimeout(jedis, "test", 1000, 3000)
            println(lockID)

            val lockID2 = acquireLockWithTimeout(jedis, "test", 1000, 3000)
            println(lockID2)
        }
        catch(e: RuntimeException) {
            println(e)
        }

    } catch (e: RuntimeException) {
        println(e)
    }
}

fun acquireLockWithTimeout(conn: Jedis, lockName: String, acquireTimeout: Long, lockTimeout: Long): String? {
    val identifier = UUID.randomUUID().toString()
    val lockKey = "lock:$lockName"
    val lockExpire = (lockTimeout / 1000).toInt()

    val end = System.currentTimeMillis() + acquireTimeout
    while (System.currentTimeMillis() < end) {
        if (conn.setnx(lockKey, identifier) == 1L){
            conn.expire(lockKey, lockExpire)
            return identifier
        }
        if (conn.ttl(lockKey) == -1L) {
            conn.expire(lockKey, lockExpire)
        }

        try {
            Thread.sleep(1)
        } catch(ie: InterruptedException){
            Thread.currentThread().interrupt();
        }
    }

    // null indicates that the lock was not acquired
    return null
}

fun releaseLock(conn: Jedis, lockName: String, identifier: String): Boolean {
    val lockKey = "lock:$lockName"

    while (true){
        conn.watch(lockKey);
        if (identifier.equals(conn.get(lockKey))){
            val trans = conn.multi()
            trans.del(lockKey)
            val results = trans.exec()
            if (results == null){
                continue
            }
            return true
        }

        conn.unwatch();
        break
    }

    return false;
}