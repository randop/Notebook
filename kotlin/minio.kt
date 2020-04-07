import io.minio.MinioClient

fun main(args: Array<String>) {    
    try {        
        val minioClient = MinioClient("http://192.168.186.192:9000", "MINIO_ACCESS_KEY", "MINIO_SECRET_KEY")

        if (minioClient.bucketExists("rfl")) {
            println("bucket exist")
        } else {
            println("bucket does not exist")
        }
    } catch (e: RuntimeException) {
        println(e)
    }
}