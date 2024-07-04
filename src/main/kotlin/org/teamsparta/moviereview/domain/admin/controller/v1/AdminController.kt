package org.teamsparta.moviereview.domain.admin.controller.v1

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.teamsparta.moviereview.domain.admin.service.v1.AdminService
import org.teamsparta.moviereview.domain.post.dto.UpdateCategoryRequest
import org.teamsparta.moviereview.domain.users.dto.AdminDto
import org.teamsparta.moviereview.domain.users.dto.SignUpRequest

@RequestMapping("/api/v1/admin")
@RestController
@PreAuthorize("hasRole('ADMIN')")
class AdminController(
    private val adminService: AdminService
) {
    @PostMapping("/create")
    fun createAdmin(
        @Valid @RequestBody signUpRequest: SignUpRequest
    ): ResponseEntity<AdminDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createAdmin(signUpRequest))
    }

    @PatchMapping("/posts/{postId}")
    fun updatePostCategory(
        @PathVariable postId: Long,
        @RequestBody updateCategoryRequest: UpdateCategoryRequest
    ): ResponseEntity<Unit> {
        adminService.updatePostCategory(postId, updateCategoryRequest)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("reports/{reportId}")
    fun approveReport(
        @PathVariable reportId: Long
    ): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteReportedPost(reportId))
    }

    @DeleteMapping("reports/{reportId}")
    fun rejectReport(
        @PathVariable reportId: Long
    ): ResponseEntity<Unit> {
        adminService.rejectReport(reportId)
        return ResponseEntity.noContent().build()
    }
}