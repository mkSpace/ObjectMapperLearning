package com.funin.objectmapperlearning

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@TestConfiguration
class CustomizedObjectMapperBuilderConfiguration {

    @Bean
    fun objectMapperBuilder(customizers: List<Jackson2ObjectMapperBuilderCustomizer>): Jackson2ObjectMapperBuilder {
        val builder = Jackson2ObjectMapperBuilder()
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .serializerByType(LocalDateTime::class.java, LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .serializerByType(LocalDate::class.java, LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE))
            .serializerByType(LocalTime::class.java, LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME))
        customizers.forEach { customizer -> customizer.customize(builder) }
        return builder
    }

    @Bean
    fun userModule(): UserModule {
        return UserModule()
    }
}
