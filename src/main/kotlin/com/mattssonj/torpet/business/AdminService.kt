package com.mattssonj.torpet.business

import com.mattssonj.torpet.controller.IncomingNewUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Service

const val USER_ROLE = "USER"

@Service
class AdminService {

/*    @Autowired lateinit var userDetailsManager: UserDetailsManager

    fun createUser(username: String, password: String) {
        verify()
        val newUser = User.builder()
            .username(username)
            .password(password)
            .roles(USER_ROLE)
            .build()
        userDetailsManager.createUser(newUser)
    }*/

    fun createUser(incomingNewUser: IncomingNewUser, admin: String): NewUser {
        TODO()
    }
}

typealias NewUser = String
class UsernameAlreadyExistsException(username: String): RuntimeException("A User with username $username already exists")