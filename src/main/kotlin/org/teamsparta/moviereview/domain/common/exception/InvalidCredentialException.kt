package org.teamsparta.moviereview.domain.common.exception

data class InvalidCredentialException(
    override val message: String? = "The credential is invalid"
): RuntimeException()