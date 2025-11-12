// 清理目标目录
tasks.register<Delete>("cleanTargetDir") {
    delete(layout.projectDirectory.dir("apks"))
}

// 复制APK文件
tasks.register<Copy>("copyApks") {
    dependsOn("cleanTargetDir")

    from(layout.projectDirectory.dir("app")) {
        include("**/*.apk")
        exclude("build/**")
    }

    into(layout.projectDirectory.dir("apks"))
}

// 清理构建目录
tasks.register<Delete>("cleanBuildDir") {
    dependsOn("copyApks")
    delete(
        "app/beta",
        "app/release",
        "app/release32",
        "app/release64",
        "app/releaseCompatibility"
    )
}

// 主入口任
tasks.register("moveAndClean") {
    dependsOn("cleanBuildDir")
    doLast {
        logger.lifecycle("APK移动和清理完成！")
    }
}