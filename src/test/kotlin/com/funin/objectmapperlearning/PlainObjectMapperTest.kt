package com.funin.objectmapperlearning

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
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

@SpringBootTest
@Import(PlainObjectMapperConfiguration::class)
class PlainObjectMapperTest {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @DisplayName("Customized ObjectMapperBuilder 의 주입을 통해 Customized 설정 정보가 제대로 설정된다.")
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

    @DisplayName("ObjectMapperBuilder를 직접 생성하면 yml 세팅을 무시한다.")
    @Test
    fun testPlainObjectMapperCreationIgnoresYmlSettings() {
        assertThat(objectMapper.propertyNamingStrategy).isNull()
    }

    @DisplayName("ObjectMapperBuilder를 직접 생성하면 직접 주입한 Module의 Serializer를 Instsall 할 수 없다.")
    @Test
    fun testPlainObjectMapperBuilderCreationDoesNotInstallInjectedModuleSerializers() {
        val user = User(1, "John Doe", "secret")
        val json = objectMapper.writeValueAsString(user)

        val expectedJson = """{"user_id":1,"name":"John Doe"}"""
        assertThat(expectedJson).isNotEqualTo(json)
    }

    @DisplayName("ObjectMapperBuilder를 직접 생성하면 직접 주입한 Module의 Deserializer를 Instsall 할 수 없다.")
    @Test
    fun testPlainObjectMapperBuilderCreationDoesNotInstallInjectedModuleDeserializers() {
        val json = """{"user_id":1,"name":"John Doe","password":""}"""

        val user: User = objectMapper.readValue(json)

        val expectedUser = User(1, "John Doe", "")
        assertThat(expectedUser).isNotEqualTo(user)
        assertThat(expectedUser.id).isNotEqualTo(user.id)
    }
}
