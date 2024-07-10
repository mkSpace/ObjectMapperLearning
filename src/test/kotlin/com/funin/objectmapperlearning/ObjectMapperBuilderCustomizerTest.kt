package com.funin.objectmapperlearning

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.readValue
import com.funin.objectmapperlearning.jackson.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Import(ObjectMapperBuilderCustomizerConfiguration::class)
@SpringBootTest
class ObjectMapperBuilderCustomizerTest {
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @DisplayName("customized ObjectMapperBuilderCustomizer의 주입을 통해 Customized 설정 정보가 제대로 설정된다.")
    @Test
    fun testObjectMapperBuilderCustomizerSettings() {
        assertThat(objectMapper.serializationConfig.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)).isFalse()
        assertThat(objectMapper.deserializationConfig.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)).isFalse()

        // Verify LocalDateTime serializer
        val dateTime = LocalDateTime.of(2020, 1, 1, 12, 0)
        val dateTimeJson = objectMapper.writeValueAsString(dateTime)
        assertThat(dateTimeJson).isEqualTo("\"2020-01-01T12:00:00\"")

        // Verify LocalDate serializer
        val date = LocalDate.of(2020, 1, 1)
        val dateJson = objectMapper.writeValueAsString(date)
        assertThat(dateJson).isEqualTo("\"2020-01-01\"")

        // Verify LocalTime serializer
        val time = LocalTime.of(12, 0)
        val timeJson = objectMapper.writeValueAsString(time)
        assertThat(timeJson).isEqualTo("\"12:00:00\"")
    }

    @DisplayName("customized ObjectMapperBuilderCustomizer를 주입하면 yml의 세팅값을 그대로 적용할 수 있다.")
    @Test
    fun testYmlSettingsAppliedWithCustomizedCustomizers() {
        assertThat(objectMapper.propertyNamingStrategy).isEqualTo(PropertyNamingStrategies.SNAKE_CASE)
    }

    @DisplayName("customized ObjectMapperBuilderCustomizer를 주입하면 Bean으로 등록된 Module의 Serializer가 자동으로 Install 된다.")
    @Test
    fun testBeanRegisteredModuleSerializersAutoInstallWithCustomizedCustomizers() {
        val user = User(1, "John Doe", "secret")
        val json = objectMapper.writeValueAsString(user)

        val expectedJson = """{"user_id":1,"name":"John Doe"}"""
        assertThat(expectedJson).isEqualTo(json)
    }

    @DisplayName("customized ObjectMapperBuilderCustomizer를 주입하면 Bean으로 등록된 Module의 Deserializer가 자동으로 Install 된다.")
    @Test
    fun testBeanRegisteredModuleDeserializersAutoInstallWithCustomizedCustomizers() {
        val json = """{"user_id":1,"name":"John Doe"}"""
        val user: User = objectMapper.readValue(json)

        val expectedUser = User(1, "John Doe", "")
        assertThat(expectedUser).isEqualTo(user)
    }
}
