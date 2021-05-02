package com.mattssonj.torpet.controller

import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {

    private val logger = LoggerFactory.getLogger(HomeController::class.java)

    @RequestMapping("/")
    fun index(): String {
        return "index"
    }

    @RequestMapping("/login")
    fun loginRedirect(auth: Authentication?): String {
        return if (isAuthenticated(auth)) "redirect:/" else "login"
    }

    @RequestMapping("/admin")
    fun admin(): String {
        return "forward:/"
    }

    private fun isAuthenticated(auth: Authentication?): Boolean {
        return if (auth != null && auth.isAuthenticated) {
            logger.info("Logged in as ${(auth.principal as UserDetails).username}")
            true
        } else false
    }

}