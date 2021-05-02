package com.mattssonj.torpet.business

import com.mattssonj.torpet.controller.IncomingNewUser
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Service

const val USER_ROLE = "USER"
const val MIN_PASSWORD_SIZE = 6

@Service
class AdminService(private val userDetailsManager: UserDetailsManager) {

    fun createUser(incomingNewUser: IncomingNewUser, admin: String): NewUser {
        verify(incomingNewUser)
        addUser(incomingNewUser)
        return incomingNewUser.username
    }

    private fun verify(incomingNewUser: IncomingNewUser) {
        if (incomingNewUser.username.isBlank()) throw IllegalArgumentException("Username cannot be empty or only contain spaces")
        if (incomingNewUser.username.contains(' ')) throw IllegalArgumentException("Username cannot contain spaces")
        if (userDetailsManager.userExists(incomingNewUser.username))
            throw UsernameAlreadyExistsException(incomingNewUser.username)

        if (incomingNewUser.password.isBlank()) throw IllegalArgumentException("Password cannot be empty or only contain spaces")
        if (incomingNewUser.password.length < MIN_PASSWORD_SIZE) throw IllegalArgumentException("Password needs to contain at least $MIN_PASSWORD_SIZE")
    }

    private fun addUser(incomingNewUser: IncomingNewUser) {
        val newUser = User.builder()
            .username(incomingNewUser.username)
            .password(incomingNewUser.password) // TODO To I need to encode password or is it done automatically
            .roles(USER_ROLE)
            .build()
        userDetailsManager.createUser(newUser)
    }

}

typealias NewUser = String

class UsernameAlreadyExistsException(username: String) :
    RuntimeException("A User with username $username already exists")