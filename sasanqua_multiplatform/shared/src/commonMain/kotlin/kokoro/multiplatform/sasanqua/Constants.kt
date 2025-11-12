package kokoro.multiplatform.sasanqua

const val SERVER_PORT = 8080

val isWeb:Boolean
    get() = getPlatform().name.contains("Web")

const val BASE_URL = "http://127.0.0.1:${SERVER_PORT}"
const val GET_RECORD_TYPES = "$BASE_URL/record/getTypes"
const val GET_RECORD_RECORDS = "$BASE_URL/record/getRecords"
const val READ_MD = "$BASE_URL/record/read/MVI.md"
