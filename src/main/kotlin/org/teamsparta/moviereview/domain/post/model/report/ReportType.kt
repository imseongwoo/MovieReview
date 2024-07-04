package org.teamsparta.moviereview.domain.post.model.report

enum class ReportType {
    ABUSE, SPAM, INAPPROPRIATE_CONTENT, HARASSMENT, FALSE_INFORMATION;

    companion object {
        fun fromString(reportType: String): ReportType {
            return try {
                ReportType.valueOf(reportType.uppercase())
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Invalid reportType : $reportType")
            }
        }
    }
}