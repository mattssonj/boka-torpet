package com.mattssonj.torpet.controller

import com.mattssonj.torpet.business.AdminService
import com.mattssonj.torpet.business.NewUser
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminController(private val adminService: AdminService) {

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    fun createUser(
        @RequestBody incomingNewUser: IncomingNewUser,
        @AuthenticationPrincipal user: User
    ): OutgoingNewUser {
        return OutgoingNewUser(adminService.createUser(incomingNewUser, user.username))
    }



}

data class IncomingNewUser(val username: String, val password: String)
data class OutgoingNewUser(val username: String)