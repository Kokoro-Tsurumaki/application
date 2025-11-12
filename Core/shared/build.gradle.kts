plugins {
	alias(libs.plugins.kotlinJvm)
	id("java-library")
	id("maven-publish")
}
group = "kokoro.shared"
version = "1.0.0"

dependencies {
	// 这里可以添加一些公共依赖，例如序列化库等
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}
	repositories {
		mavenLocal()
	}
}