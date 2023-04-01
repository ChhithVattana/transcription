package com.example.project.config

import com.example.project.utils.ResponseObjectMap
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun getResponseObjectMap(): ResponseObjectMap = ResponseObjectMap()
}