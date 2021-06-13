package com.mattssonj.torpet.business

import com.mattssonj.torpet.controller.IncomingNewUser
import com.mattssonj.torpet.controller.OutgoingUser
import com.mattssonj.torpet.controller.UsernameAlreadyExistsException
import com.mattssonj.torpet.security.Roles
import com.mattssonj.torpet.security.encode
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Service

const val MIN_PASSWORD_SIZE = 6

@Service
class AdminService(
    private val userDetailsManager: UserDetailsManager,
    private val userService: UserService,
) {

    fun createUser(incomingNewUser: IncomingNewUser, admin: String): NewUser {
        verifyUsername(incomingNewUser.username)
        verifyPassword(incomingNewUser.password)
        addUser(incomingNewUser)
        userService.createUserInformation(incomingNewUser.username, admin)
        return incomingNewUser.username
    }

    fun getAllRegisteredUsers(admin: String): List<OutgoingUser> {
        return userService.getAllUserInformation()
            .map { OutgoingUser(it.username, it.createdBy, admin.equals(it.createdBy, true)) }
    }

    fun updatePassword(username: String, newPassword: String) {
        verifyPassword(newPassword)
        getUserDetails(username).let {
            User.builder()
                .authorities(it.authorities)
                .username(it.username)
                .password(newPassword.encode())
                .build()
        }.also { userDetailsManager.updateUser(it) }
    }

    private fun verifyUsername(username: String) {
        if (username.isBlank()) throw IllegalArgumentException("Username cannot be empty or only contain spaces")
        if (username.contains(' ')) throw IllegalArgumentException("Username cannot contain spaces")
        if (userDetailsManager.userExists(username))
            throw UsernameAlreadyExistsException(username)
    }

    private fun verifyPassword(password: String) {
        if (password.isBlank()) throw IllegalArgumentException("Password cannot be empty or only contain spaces")
        if (password.length < MIN_PASSWORD_SIZE) throw IllegalArgumentException("Password needs to contain at least $MIN_PASSWORD_SIZE")
    }

    private fun addUser(incomingNewUser: IncomingNewUser) {
        val newUser = User.builder()
            .username(incomingNewUser.username)
            .password(incomingNewUser.password.encode())
            .roles(Roles.USER)
            .build()
        userDetailsManager.createUser(newUser)
    }

    private fun getUserDetails(username: String): UserDetails {
        return try {
            userDetailsManager.loadUserByUsername(username)
        } catch (e: UsernameNotFoundException) {
            throw IllegalArgumentException("No user with username $username was found")
        }
    }

}

typealias NewUser = String