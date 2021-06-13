package com.mattssonj.torpet.controller

import com.mattssonj.torpet.business.AdminService
import com.mattssonj.torpet.security.Roles
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

internal const val NEW_PASSWORD_REQUEST_PARAM = "newPassword"

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAnyRole('${Roles.ADMIN}', '${Roles.DEV}')")
class AdminController(private val adminService: AdminService) {

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(
        @RequestBody incomingNewUser: IncomingNewUser,
        @AuthenticationPrincipal user: User
    ): OutgoingUser {
        val newUserUsername = adminService.createUser(incomingNewUser, user.username)
        return OutgoingUser(newUserUsername, user.username, true)
    }

    @GetMapping("/users")
    fun getRegisteredUsers(@AuthenticationPrincipal user: User): List<OutgoingUser> {
        return adminService.getAllRegisteredUsers(user.username)
    }

    @PreAuthorize("hasAnyRole('${Roles.ADMIN}', '${Roles.DEV}') or #userPassword.username == principal.username")
    @PostMapping("/users/changePassword")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changePassword(@RequestBody userPassword: UserPassword) {
        adminService.updatePassword(userPassword.username, userPassword.newPassword)
    }

}

data class IncomingNewUser(val username: String, val password: String)
data class OutgoingUser(val username: String, val createdBy: String, val ableToDelete: Boolean)
data class UserPassword(val username: String, val newPassword: String)