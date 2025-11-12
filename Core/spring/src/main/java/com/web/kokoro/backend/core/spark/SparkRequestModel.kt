package kokoro.mobile

/**
{
	"model": "generalv3.5",
	"user": "用户唯一id",
	"messages": [{
			"role": "system",
			"content": "你是知识渊博的助理"
		},
		{
			"role": "user",
			"content": "你好，讯飞星火"
		}
	],
	"temperature": 0.5,
	"top_k": 4,
	"stream": false,
	"max_tokens": 1024,
	"presence_penalty": 1,
	"frequency_penalty": 1,
	"tools": [{
			"type": "function",
			"function": {
				"name": "str2int",
				"description": "将字符串类型转为 int 类型",
				"parameters": null
			}
		},
		{
			"type": "web_search",
			"web_search": {
				"enable": true,
				"show_ref_label": true,
				"search_mode": "deep"
			}
		}
	],
	"response_format": {
		"type": "json_object"
	},
	"suppress_plugin": [
		"knowledge"
	]
}
*/
data class SparkRequestModel(
//    val frequency_penalty: Int? = 0,
//    val max_tokens: Int? = 0,
    val messages: List<Message?>? = listOf(),
    val model: String? = "",
//    val presence_penalty: Int? = 0,
//    val response_format: ResponseFormat? = ResponseFormat(),
    val stream: Boolean = true,
//    val suppress_plugin: List<String?>? = listOf(),
//    val temperature: Double? = 0.0,
//    val tools: List<Tool?>? = listOf(),
//    val top_k: Int? = 0,
    val user: String? = ""
) {
    data class Message(
        val content: String? = "",
        val role: String? = ""
    )

    data class ResponseFormat(
        val type: String? = ""
    )

    data class Tool(
        val function: Function? = Function(),
        val type: String? = "",
        val web_search: WebSearch? = WebSearch()
    ) {
        data class Function(
            val description: String? = "",
            val name: String? = "",
            val parameters: Any? = Any()
        )

        data class WebSearch(
            val enable: Boolean? = false,
            val search_mode: String? = "",
            val show_ref_label: Boolean? = false
        )
    }
}