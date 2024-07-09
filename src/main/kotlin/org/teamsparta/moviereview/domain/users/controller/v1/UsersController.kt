package org.teamsparta.moviereview.domain.users.controller.v1

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.teamsparta.moviereview.domain.users.dto.*
import org.teamsparta.moviereview.domain.users.service.v1.UserService
import org.teamsparta.moviereview.infra.security.UserPrincipal

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

    @PatchMapping("/profile")
    fun updateProfile(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody profile: UserUpdateProfileDto
    ): ResponseEntity<UserDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateProfile(profile, principal.id))
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest): ResponseEntity<Unit> {
        val token = request.getAttribute("accessToken") as String?
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.signOut(token!!))
    }

    @PostMapping("/token/refresh")
    fun tokenRefresh(@RequestBody tokenRefreshDto: TokenRefreshDto): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.validateRefreshTokenAndCreateToken(tokenRefreshDto.refreshToken))
    }
}