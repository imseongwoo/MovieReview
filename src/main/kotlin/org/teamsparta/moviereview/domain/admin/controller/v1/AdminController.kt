package org.teamsparta.moviereview.domain.admin.controller.v1

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.teamsparta.moviereview.domain.admin.service.v1.AdminService
import org.teamsparta.moviereview.domain.users.dto.AdminDto
import org.teamsparta.moviereview.domain.users.dto.SignUpRequest

@RequestMapping("/api/v1/admin")
@RestController
class AdminController(
    private val adminService: AdminService
) {
    @PostMapping("/create")
    fun createAdmin(
        @Valid @RequestBody signUpRequest: SignUpRequest
    ): ResponseEntity<AdminDto> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(adminService.createAdmin(signUpRequest))
    }
}