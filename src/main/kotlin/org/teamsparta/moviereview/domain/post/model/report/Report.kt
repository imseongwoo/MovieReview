package org.teamsparta.moviereview.domain.post.model.report

import jakarta.persistence.*
import org.teamsparta.moviereview.domain.post.model.Post
import org.teamsparta.moviereview.domain.users.model.Users
import java.time.LocalDateTime

@Entity
class Report(
    @Enumerated(EnumType.STRING)
    var reportType: ReportType,

    @Column
    val description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val user: Users,

    @ManyToOne(fetch = FetchType.LAZY)
    val post: Post
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}