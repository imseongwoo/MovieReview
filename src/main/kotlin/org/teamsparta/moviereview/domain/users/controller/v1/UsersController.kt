package org.teamsparta.moviereview.domain.users.controller.v1

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.teamsparta.moviereview.domain.users.dto.LoginRequest
import org.teamsparta.moviereview.domain.users.dto.LoginResponse
import org.teamsparta.moviereview.domain.users.dto.SignUpRequest
import org.teamsparta.moviereview.domain.users.dto.UserDto
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

    @PostMapping("/login")
    fun signIn(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.signIn(loginRequest))
    }
}