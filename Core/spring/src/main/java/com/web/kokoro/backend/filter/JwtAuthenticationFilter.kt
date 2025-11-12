package com.web.kokoro.backend.filter

import com.web.kokoro.backend.RouteChecker
import com.web.kokoro.backend.base.Result
import com.web.kokoro.backend.base.ResultCode.UNAUTHORIZED
import com.web.kokoro.backend.base.JwtUtils
import com.web.kokoro.backend.base.ResultCode
import io.jsonwebtoken.Claims
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtAuthenticationFilter(
    private val jwtUtils: JwtUtils,
    private val routeChecker: RouteChecker
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        println("Token拦截器成功拦截=======================================================")

        //1.获取请求url
        val url: String = request.requestURL.toString()

        if (!routeChecker.isRouteExists(request)) {
            val json: String = com.alibaba.fastjson.JSONObject.toJSONString(Result.error(ResultCode.NOT_FOUND))
            response.contentType = "application/json;charset=utf-8"
            response.writer.write(json)
            return
        }

        println("请求路径：{$url}") //请求路径：http://localhost:8080/login
        if(url.contains("/login") || url.contains("/register") || url.contains("/deepseek/chat/completions")||url.contains("/spark/chat/completions") || url.contains("/hachimi/authorize/hint")){
            filterChain.doFilter(request, response);//放行请求
            println("放行")
            return;//结束当前方法的执行
        }
        println("拦截")

        val token = extractToken(request)

        if (token != null && jwtUtils.validateToken(token)) {
            val claims = jwtUtils.parseJwt(token)
            val auth = createAuthentication(claims)
            SecurityContextHolder.getContext().authentication = auth
        }else{
            //没有token返回无权限
            //把Result对象转换为JSON格式字符串
            val json: String = com.alibaba.fastjson.JSONObject.toJSONString(Result.error(UNAUTHORIZED))
            response.contentType = "application/json;charset=utf-8"
            response.writer.write(json)
            return
        }

        filterChain.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val header = request.getHeader("Authorization")
        return if (header != null && header.startsWith("Bearer ")) {
            header.substring(7)
        } else {
            null
        }
    }

    private fun createAuthentication(claims: Claims): Authentication {
        val username = claims.subject
        val roles:List<String> = claims["roles"] as? List<String> ?: emptyList()
        val authorities = roles.map { SimpleGrantedAuthority(it) }
        return UsernamePasswordAuthenticationToken(username, null, authorities)
    }
}