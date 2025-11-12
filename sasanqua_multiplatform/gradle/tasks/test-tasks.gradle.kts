// test-tasks.gradle.kts

// 注册一个简单的测试任务
tasks.register("testTaskSetup") {
    group = "verification"
    description = "验证自定义Task配置是否可用"

    doLast {
        println("✅ 自定义Task配置工作正常！")
        println("当前项目: ${project.name}")
        println("项目路径: ${project.projectDir}")
        println("构建目录: ${project.buildDir}")
    }
}

// 注册一个带参数的高级测试任务
tasks.register("advancedTestTask", AdvancedTestTask::class)

abstract class AdvancedTestTask : DefaultTask() {
    @get:Input
    abstract val testParam: Property<String>

    init {
        testParam.convention("默认参数值")
        group = "verification"
        description = "高级测试任务"
    }

    @TaskAction
    fun run() {
        println("🔄 高级测试任务执行中...")
        println("参数值: ${testParam.get()}")
        println("Java版本: ${JavaVersion.current()}")
        println("操作系统: ${System.getProperty("os.name")}")
        println("✅ 高级测试完成！")
    }
}