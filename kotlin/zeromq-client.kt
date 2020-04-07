import org.zeromq.ZMQ
import org.zeromq.SocketType
import org.zeromq.ZMQ.Socket
import org.zeromq.ZContext
import com.beust.klaxon.*
import java.io.StringReader

const val MY_DOCKER_IP = "192.168.186.192"
const val ZMQ_PORT = "8080"

fun main(args: Array<String>) {
    val context = ZContext()    

    try {        
        val socket = context.createSocket(SocketType.REQ)
        socket.connect("tcp://$MY_DOCKER_IP:$ZMQ_PORT")
        println("Connecting at tcp://*:$ZMQ_PORT")

        //[{"action":"print","index":1},{"action":"print","index":2},{"action":"print","index":3}]
        val jsonRequest = json {
            array(listOf(1,2,3).map {
                obj(
                    "action" to "print",
                    "index" to it
                )
            })
        }

        val request = jsonRequest.toJsonString()
        println(request)
        
        for (num in 1..10) {            
            println("Sending request #$num")
            socket.send(request.toByteArray(ZMQ.CHARSET), 0)

            val reply = socket.recv(0)
            val jsonReply = String(reply, ZMQ.CHARSET)
            JsonReader(StringReader(jsonReply)).use { reader ->                
                reader.beginArray {
                    while (reader.hasNext()) {                                              
                        val payload = reader.nextObject()
                        try {
                            payload.string("action")?.let {
                                println(it)
                            }
                        } catch (e: RuntimeException) {
                            //void
                        }
                    }
                }
            }            

            println("Received $jsonReply")
        }
    } catch (e: RuntimeException) {
        println(e)
    }
}