import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

abstract class CreateTemplateForMVITask : DefaultTask() {
    @get:Input
    abstract val tflag: Property<String>

    @get:Input
    abstract val tpackage: Property<String>

    @get:Input
    abstract val tpath: Property<String>

    @get:InputDirectory
    abstract val sourceTemplates: DirectoryProperty
    @get:Internal
    abstract val projectDir: DirectoryProperty


    @TaskAction
    fun execute() {
        //模块名
        val flag = tflag.get()
        //包名
        val packageName = tpackage.get()
        //生成路径
        val path = tpath.get()
        val sourceDir = sourceTemplates.get().asFile


        val targetDir =
        if (path == "null"){
            projectDir.get().dir("gradle/template")
                .dir(flag.lowercase()) // 创建模块名命名的子文件夹
                .apply {
                    asFile.mkdirs()
                }
        } else {
            projectDir.get().dir(path)
                .dir(flag.lowercase()) // 创建模块名命名的子文件夹
                .apply {
                    asFile.mkdirs()
                    logger.lifecycle("创建模板目录: ${asFile.absolutePath}")
                }
        }


        // 复制并处理模板文件
        sourceDir.walk()
            .filter { it.isFile }
            .forEach { sourceFile ->
                val relativePath = "$flag${sourceFile.relativeTo(sourceDir).toString().replace(".template", ".kt")}"
                logger.lifecycle("准备生成: $relativePath")

                val targetFile = targetDir.asFile.resolve(relativePath)

                if (sourceFile.name.endsWith(".template")) {
                    // 处理模板文件（替换占位符）
                    processTemplateFile(sourceFile, targetFile, flag, packageName,path)
                } else {
                    // 直接复制非模板文件
                    sourceFile.copyTo(targetFile, overwrite = true)
                }
            }
        logger.lifecycle(
            """
            MVI 模板创建完成！ ☝🤓
            ========================================================================================
            模块名称: $flag
            包名称: $packageName
            模版位置: ${sourceDir.absolutePath}
            生成位置: ${targetDir.asFile.absolutePath}
            ========================================================================================
        """.trimIndent())
    }

    /**
     * @param source 模版文件
     * @param target 生成文件
     * @param name 模块名
     * @param path 生成路径
     */
    private fun processTemplateFile(source: File, target: File, name: String, packageName: String,path: String) {
        val buildTime = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US).format(Date())
        val content = source.readText()
            .replace("\${COMMAND}", "`./gradlew createTemplateForMVI -Ptflag=${name} -Ptpackage=${packageName} ${if (path == "null") "" else "-Ptpath=$path"}`")
            .replace("\${NAME}", name)
            .replace("\${NAME_LOWER}", name.lowercase())
            .replace("\${NAME_UPPER}", name.uppercase())
            .replace("\${PACKAGE}",  packageName)
            .replace("\${BUILD_TIME}", buildTime)

        // 确保目标目录存在
        target.parentFile.mkdirs()

        // 写入处理后的内容
        target.writeText(content)
        logger.lifecycle("创建文件: ${target.absolutePath}")
    }
}

// 注册任务
tasks.register<CreateTemplateForMVITask>("createTemplateForMVI") {
    tflag.set(project.findProperty("tflag")?.toString() ?: throw GradleException("必须通过 -Pflag=xxx 指定模块名称"))
    tpackage.set(project.findProperty("tpackage")?.toString() ?: throw GradleException("必须通过 -Ppackage=xxx 指定包名"))
    tpath.set(project.findProperty("tpath")?.toString() ?: "null")
    // 设置模板源目录
    sourceTemplates.set(project.layout.projectDirectory.dir("gradle/template/mvi"))

    // 设置项目目录
    projectDir.set(project.layout.projectDirectory)
}