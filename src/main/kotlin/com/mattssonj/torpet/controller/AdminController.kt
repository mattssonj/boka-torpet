package com.mattssonj.torpet.controller

import com.mattssonj.torpet.business.AdminService
import com.mattssonj.torpet.security.Roles
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

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

}

data class IncomingNewUser(val username: String, val password: String)
data class OutgoingUser(val username: String, val createdBy: String, val ableToDelete: Boolean)
