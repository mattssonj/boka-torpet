package com.mattssonj.torpet.business

import com.mattssonj.torpet.controller.IncomingNewUser
import com.mattssonj.torpet.controller.OutgoingUser
import com.mattssonj.torpet.controller.UsernameAlreadyExistsException
import com.mattssonj.torpet.persistence.UserInformation
import com.mattssonj.torpet.persistence.UserInformationRepository
import com.mattssonj.torpet.security.Roles
import com.mattssonj.torpet.security.encode
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Service

const val MIN_PASSWORD_SIZE = 6

@Service
class AdminService(
    private val userDetailsManager: UserDetailsManager,
    private val userService: UserService,
) {

    fun createUser(incomingNewUser: IncomingNewUser, admin: String): NewUser {
        verify(incomingNewUser)
        addUser(incomingNewUser)
        userService.createUserInformation(incomingNewUser.username, admin)
        return incomingNewUser.username
    }

    fun getAllRegisteredUsers(admin: String): List<OutgoingUser> {
        return userService.getAllUserInformation()
            .map { OutgoingUser(it.username, it.createdBy, admin.equals(it.createdBy, true)) }
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
            .password(incomingNewUser.password.encode())
            .roles(Roles.USER)
            .build()
        userDetailsManager.createUser(newUser)
    }

}

typealias NewUser = String