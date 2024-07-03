package org.teamsparta.moviereview.domain.users.repository.v1

import org.springframework.data.jpa.repository.JpaRepository
import org.teamsparta.moviereview.domain.users.model.Users

interface UserRepository : JpaRepository<Users, Long> {
    fun findByEmail(email: String) : Users?
}