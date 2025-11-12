package kokoro.mobile.hachimi.core.network.collection

import okhttp3.RequestBody
import retrofit2.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by Zebra-RD张先杰 on 2022年6月30日11:28:40
 *
 * Description:这个是Retrofit的请求代理类
 */
interface ApiService {
    /**
     * HTTP, GET, POST, PUT, PATCH, DELETE, OPTIONS and HEAD
     * POST
     */


    @GET()
    suspend fun getData(
        @Url url: String,@QueryMap body: Map<String, String>): String


    @FormUrlEncoded
    @POST
    suspend fun postData(
        @Url url: String,
        @FieldMap body: Map<String, String>,
    ): String

    @POST
    suspend fun postData(
        @Url url: String,
        @Body info: RequestBody
    ): String

    @Streaming
    @GET
    suspend fun getFileData(@Url url: String): Response<ResponseBody>

    @POST("spring-api/test")
    suspend fun test():  Response<ResponseBody>

    @POST("spring-api/hachimi/authorize/hint")
    suspend fun getAuthorizeHint(
    ):  Response<ResponseBody>

    @POST("spring-api/login")
    suspend fun login(@Body info: RequestBody):  Response<ResponseBody>

}