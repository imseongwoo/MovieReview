package org.teamsparta.moviereview.domain.users.controller.v1

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.teamsparta.moviereview.domain.users.dto.SignUpRequest
import org.teamsparta.moviereview.domain.users.dto.UserDto
import org.teamsparta.moviereview.domain.users.dto.UserUpdateProfileDto
import org.teamsparta.moviereview.domain.users.service.v1.UserService

@RequestMapping("/api/v1/users")
@RestController
class UsersController(
    private val userService: UserService
) {

    @PostMapping("/sign-up")
    fun signUp(
        @Valid @RequestBody signUpRequest: SignUpRequest
    ): ResponseEntity<UserDto> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.signUp(signUpRequest))
    }

    @PatchMapping("/profile")
    fun updateProfile(
        @AuthenticationPrincipal principal: UserPrincipal,
        @@RequestBody profile: UserUpdateProfileDto
    ): ResponseEntity<UserDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateProfile(profile, principal.id))
    }
}