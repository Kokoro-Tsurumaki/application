package com.web.kokoro.backend


import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@Component
class RouteChecker {

    @Autowired
    private lateinit var handlerMapping: RequestMappingHandlerMapping

    fun isRouteExists(request: HttpServletRequest): Boolean {
        val handlerExecutionChain = handlerMapping.getHandler(request)
        return handlerExecutionChain != null
    }
}