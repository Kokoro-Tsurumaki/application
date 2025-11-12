package kokoro.multiplatform.sasanqua

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
