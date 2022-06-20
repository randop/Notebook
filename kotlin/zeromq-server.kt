import org.zeromq.ZMQ
import org.zeromq.ZMQ.Socket
import org.zeromq.ZContext

fun main(args: Array<String>) {
    val context = ZContext()

    try {        
        val socket = context.createSocket(ZMQ.REP)
        socket.bind("tcp://*:8080")
        println("Listening at tcp://*:8080")

        while (!Thread.currentThread().isInterrupted()) {            
            val reply = socket.recv(0)

            println("Received: [${String(reply, ZMQ.CHARSET)}]")
            
            //val response = "Hello, world!";
            val response = String(reply, ZMQ.CHARSET)
            socket.send(response.toByteArray(ZMQ.CHARSET), 0)
        }
    } catch (e: RuntimeException) {
        println(e)
    }
}