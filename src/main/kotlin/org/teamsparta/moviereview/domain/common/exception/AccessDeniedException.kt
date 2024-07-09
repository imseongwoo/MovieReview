package org.teamsparta.moviereview.domain.common.exception

data class AccessDeniedException(
    private val text: String
): RuntimeException(
    "Access Denied: $text"
)