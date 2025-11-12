package kokoro.multiplatform.sasanqua.core.record.route

import io.ktor.http.ContentDisposition
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.server.request.receive
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.server.routing.*
import kokoro.multiplatform.sasanqua.core.record.service.RecordService
import kokoro.multiplatform.sasanqua.core.record.service.TypeService
import kokoro.multiplatform.sasanqua.model.RecordModel
import kokoro.multiplatform.sasanqua.model.TypeModel
import kokoro.multiplatform.sasanqua.network.wrapper.JsonWrapper
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.ext.inject
import java.io.File
import java.util.UUID

fun Routing.recordRouting(database: Database) {
    val typeService by inject<TypeService>()
    val recordService by inject<RecordService>()

    route("record"){

        post("/addType") {
            val type = call.receive<TypeModel>()
            typeService.create(type.type)
            call.respond(JsonWrapper.success("创建成功"))
        }
        get("/getType/{id}"){
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val user = typeService.read(id)
            if (user != null) {
                call.respond(JsonWrapper.success("成功",user))
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get("/getTypes"){
            val types = typeService.readAll()
            call.respond(JsonWrapper.success("成功",types))
        }

        post("/addRecord"){
            val inputRecord = call.receive<RecordModel>()
            recordService.create(inputRecord)
            call.respond(JsonWrapper.success("创建成功"))
        }

        get("/getRecords"){
            val records = recordService.readAll()
            call.respond(JsonWrapper.success("成功",records))
        }

        get("/getFile"){
            call.response.header(HttpHeaders.ContentDisposition, "attachment; filename=\"MVI.md\"")
            call.response.header(HttpHeaders.ContentDisposition, ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, "MVI.md").toString())
            call.respond(File("./MVI.md"))
        }

        // 文件上传端点
        post("/upload") {
            val multipart = call.receiveMultipart()
            val uploadDir = File("uploads")
            if (!uploadDir.exists()) uploadDir.mkdirs()

            var fileName = ""
            var fileSize = 0L

            multipart.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        // 生成唯一文件名
                        val originalFileName = part.originalFileName ?: "unknown"
                        val fileExtension = originalFileName.substringAfterLast(".", "")
                        fileName = "${UUID.randomUUID()}.$fileExtension"
                        val file = File(uploadDir, fileName)

                        part.streamProvider().use { input ->
                            file.outputStream().buffered().use { output ->
                                input.copyTo(output)
                            }
                        }
                        fileSize = file.length()
                    }
                    else -> {}
                }
                part.dispose()
            }

            call.respond(mapOf(
                "message" to "File uploaded successfully",
                "fileName" to fileName,
                "fileSize" to fileSize,
                "downloadUrl" to "http://localhost:8080/download/$fileName"
            ))
        }

        // 文件下载端点
        get("/download/{fileName}") {
            val fileName = call.parameters["fileName"] ?: throw IllegalArgumentException("Missing fileName")
            val file = File("uploads", fileName)

            if (!file.exists()) {
                call.respond(HttpStatusCode.NotFound, "File not found")
                return@get
            }

            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, fileName)
                    .toString()
            )

            call.respondFile(file)
        }

        get("/read/{fileName}") {
            val fileName = call.parameters["fileName"] ?: throw IllegalArgumentException("Missing fileName")
            val file = File("uploads", fileName)

            if (!file.exists()) {
                call.respond(HttpStatusCode.NotFound, "File not found")
                return@get
            }

            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, fileName)
                    .toString()
            )

            call.respond(file.readText(Charsets.UTF_8))
        }
    }
}