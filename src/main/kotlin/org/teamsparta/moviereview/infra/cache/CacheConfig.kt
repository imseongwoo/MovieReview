package org.teamsparta.moviereview.infra.cache

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
class CacheConfig {

    @Bean("caffeineCacheManager")
    fun caffeineCacheManager(): CaffeineCacheManager {
        val cacheManager = CaffeineCacheManager()

        val caffeine = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)

        cacheManager.setCaffeine(caffeine)
        cacheManager.setCacheNames(listOf("hotKeywordsLastHour", "hotKeywordsLastDay", "searchPost"))

        return cacheManager
    }
}