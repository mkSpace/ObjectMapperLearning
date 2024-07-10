package com.funin.objectmapperlearning

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class ObjectMapperBuilderCustomizerConfiguration {

    @Bean
    fun objectMapperBuilderCustomizer(): SimpleObjectMapperBuilderCustomizer {
        return SimpleObjectMapperBuilderCustomizer()
    }

    @Bean
    fun userModule(): UserModule {
        return UserModule()
    }
}
