package org.teamsparta.moviereview.domain.post.model.report

import jakarta.persistence.*
import org.teamsparta.moviereview.domain.post.model.Post
import org.teamsparta.moviereview.domain.users.model.Users

@Entity
class Report(
    @Enumerated(EnumType.STRING) var reportType: ReportType,

    @Column val description: String,

    @ManyToOne(fetch = FetchType.LAZY) val user: Users,

    @ManyToOne(fetch = FetchType.LAZY) val post: Post
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun of(reportType: String, description: String, user: Users, post: Post): Report {
            val report = Report(
                reportType = ReportType.fromString(reportType), description = description, user = user, post = post
            )
            return report
        }
    }

    fun checkReportPermission(userId: Long, role: String): Boolean {
        return user.id == userId || role == "ROLE_ADMIN"
    }
}