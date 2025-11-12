package kokoro.multiplatform.sasanqua

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.routing.*
import kokoro.multiplatform.sasanqua.core.record.route.recordRouting
import kokoro.multiplatform.sasanqua.core.record.service.RecordService
import kokoro.multiplatform.sasanqua.core.record.service.TypeService
import kokoro.multiplatform.sasanqua.core.topics.TopicService
import kokoro.multiplatform.sasanqua.core.topics.TopicTypeService
import kokoro.multiplatform.sasanqua.core.topics.topicRouting
import kotlinx.serialization.json.*
import org.jetbrains.exposed.sql.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.ktor.plugin.KoinApplicationStarted
import org.koin.ktor.plugin.KoinApplicationStopPreparing
import org.koin.ktor.plugin.KoinApplicationStopped
import org.koin.logger.slf4jLogger

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val database = Database.connect("jdbc:mysql://localhost:3306/kkrdb", driver = "com.mysql.cj.jdbc.Driver", user = "root", password = "1320")
    val appModule = module {
        single { database }
        singleOf(::TypeService) { bind<TypeService>() }
        singleOf(::RecordService) { bind<RecordService>() }
        singleOf(::TopicTypeService) { bind<TopicTypeService>() }
        singleOf(::TopicService) { bind<TopicService>() }
    }

    // Install Koin
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }

    environment.monitor.subscribe(KoinApplicationStarted) {
        log.info("Koin started.")
    }

    environment.monitor.subscribe(KoinApplicationStopPreparing) {
        log.info("Koin stopping...")
    }

    environment.monitor.subscribe(KoinApplicationStopped) {
        log.info("Koin stopped.")
    }

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            explicitNulls = false
        })
    }

    install(CORS) {
        allowHost("0.0.0.0:5000")
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost()
    }

    install(DefaultHeaders)

    launchJwtAuth()

    exceptionHandle()


    routing {
        //需认证的接口
        authenticate {}

        recordRouting(database)

        topicRouting(database)
    }

}