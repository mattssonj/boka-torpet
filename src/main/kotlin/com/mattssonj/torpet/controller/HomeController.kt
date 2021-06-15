package com.mattssonj.torpet.controller

import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {

    private val logger = LoggerFactory.getLogger(HomeController::class.java)

    @RequestMapping("/login")
    fun loginRedirect(auth: Authentication?): String {
        return if (isAuthenticated(auth)) "redirect:/" else "login"
    }

    @RequestMapping("/") fun index(): String = "index"
    @RequestMapping("/admin") fun admin(): String = defaultForward
    @RequestMapping("/path/**") fun forwardAllRequestToHome(): String = defaultForward

    private val defaultForward = "forward:/"

    private fun isAuthenticated(auth: Authentication?): Boolean {
        return if (auth != null && auth.isAuthenticated) {
            logger.info("Logged in as ${(auth.principal as UserDetails).username}")
            true
        } else false
    }

}