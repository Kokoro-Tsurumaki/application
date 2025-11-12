package kokoro.multiplatform.sasanqua.network.client

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kokoro.multiplatform.sasanqua.*
import kokoro.multiplatform.sasanqua.model.*
import kokoro.multiplatform.sasanqua.network.wrapper.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.*

//internal expect val ApplicationDispatcher: CoroutineDispatcher

class ApplicationApi {
    private val client = HttpClient(){
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }
    }

    fun getTypes(viewModelScope: CoroutineScope,callback: ResultWrapper.JsonWrapperResultCallback<List<TypeModel>>) {
        fetch {
            client.get { url(GET_RECORD_TYPES) }
        }.launchJsonCallback(viewModelScope,callback)
    }

    fun getRecord(viewModelScope: CoroutineScope, callback: ResultWrapper.JsonWrapperResultCallback<List<RecordModel>>) {
        fetch {
            client.get { url(GET_RECORD_RECORDS) }
        }.launchJsonCallback(viewModelScope,callback)
    }
    fun readMd(viewModelScope: CoroutineScope,callback: ResultWrapper.StringResultCallback) {
        fetch {
            client.get { url(READ_MD) }
        }.launchStringCallback(viewModelScope,callback)
    }

}
