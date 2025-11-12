package kokoro.multiplatform.sasanqua

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import kokoro.multiplatform.sasanqua.exception.InvalidCredentialsException
import kokoro.multiplatform.sasanqua.network.wrapper.JsonWrapper
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.sql.SQLIntegrityConstraintViolationException

fun Application.exceptionHandle() {
    install(StatusPages) {

        status(HttpStatusCode.UnsupportedMediaType) { call, status ->
            call.respond(status, JsonWrapper(status.value,status.description))
        }
        status(HttpStatusCode.Created) { call, status ->
            call.respond(status, JsonWrapper(status.value,status.description))
        }

        status(HttpStatusCode.NotFound) { call, status ->
            call.respond(status, JsonWrapper(status.value,status.description))
        }

        status(HttpStatusCode.Unauthorized) { call, status ->
            call.respond(status, JsonWrapper(status.value,status.description))
        }

        exception<InvalidCredentialsException> { call, cause ->
            call.respond(HttpStatusCode.Unauthorized, mapOf("OK" to false, "error" to (cause.message ?: "")))
        }

        //传入请求体 json不合法，解析失败
        exception<BadRequestException> { call, cause ->
            val rootCause = cause.cause
            if (rootCause is JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest, JsonWrapper(400,"请求体非法"))
                return@exception
            }
            call.application.environment.log.error("未处理的BadRequestException", cause)
            call.respond(HttpStatusCode.BadRequest, JsonWrapper(400, "BadRequestException"))
        }

        // 专门处理ExposedSQLException
        exception<ExposedSQLException> { call, cause ->
            val rootCause = cause.cause
            if (rootCause is SQLIntegrityConstraintViolationException) {
                if (rootCause.errorCode == 1062) { // MySQL duplicate key error code
                    call.respond(HttpStatusCode.Conflict, JsonWrapper(409, "数据已存在，请勿重复添加"))
                    return@exception
                }
            }
            call.application.environment.log.error("未处理的数据库异常", cause)
            call.respond(HttpStatusCode.InternalServerError, JsonWrapper(500, "服务器内部错误"))
        }


//        unhandled{call ->
//            call.respond(call.response.status()?:500, ResponseShell(call.response.status()?:500,call.response.))
//        }

    }
}