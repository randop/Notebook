package demo

import redis.clients.jedis.Jedis

fun main(args: Array<String>) {    
    try {        
        val jedis = Jedis("192.168.186.192")
        jedis.set("foo", "bar")
        val value = jedis.get("foo")
        println(value)
    } catch (e: RuntimeException) {
        println(e)
    }
}