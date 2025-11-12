package kokoro.multiplatform.sasanqua.core.topics

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
import kokoro.multiplatform.sasanqua.model.RecordModel
import kokoro.multiplatform.sasanqua.model.TopicModel
import kokoro.multiplatform.sasanqua.model.TypeModel
import kokoro.multiplatform.sasanqua.network.wrapper.JsonWrapper
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.ext.inject
import java.io.File
import java.util.UUID

fun Routing.topicRouting(database: Database) {
    val topicTypeService by inject<TopicTypeService>()
    val topicService by inject<TopicService>()

    route("topic"){
        post("/addType") {
            val type = call.receive<TypeModel>()
            topicTypeService.create(type.type)
            call.respond(JsonWrapper.success("创建成功"))
        }

        get("/getTypes"){
            val types = topicTypeService.readAll()
            call.respond(JsonWrapper.success("成功",types))
        }

        post("/addTopic"){
            val inputRecord = call.receive<TopicModel>()
            topicService.create(inputRecord)
            call.respond(JsonWrapper.success("创建成功"))
        }

        get("/getTopics"){
            val records = topicService.readAll()
            call.respond(JsonWrapper.success("成功",records))
        }

        get("/getTopics/{type}"){
            val type = call.parameters["type"] ?: throw IllegalArgumentException("Invalid Type")
            val user = topicService.read(type)
            if (user != null) {
                call.respond(JsonWrapper.success("成功",user))
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}