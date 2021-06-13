package com.mattssonj.torpet.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

object Roles {
    const val USER = "USER"
    const val ADMIN = "ADMIN"
    const val DEV = "DEVELOPER"
}

val passwordEncoder = BCryptPasswordEncoder()
fun String.encode(): String = passwordEncoder.encode(this)