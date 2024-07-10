package org.teamsparta.moviereview.infra.cache

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
class CacheConfig {

    @Bean("caffeineCacheManager")
    fun caffeineCacheManager(): CacheManager {
        val cacheManager = SimpleCacheManager()

        val caches = listOf(
            CaffeineCache("hotKeywordsLastHour", Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build()
            ),
            CaffeineCache("hotKeywordsLastDay", Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build()
            ),
            CaffeineCache("searchPostAOP", Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build()
            )
        )
        cacheManager.setCaches(caches)
        return cacheManager
    }
}