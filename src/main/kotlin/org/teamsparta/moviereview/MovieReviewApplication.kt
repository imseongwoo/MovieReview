package org.teamsparta.moviereview

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class MovieReviewApplication

fun main(args: Array<String>) {
    runApplication<MovieReviewApplication>(*args)
}
