package com.funin.objectmapperlearning

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.readValue
import com.funin.objectmapperlearning.jackson.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(CustomizedObjectMapperConfiguration::class)
@SpringBootTest
class CustomizedObjectMapperTest {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @DisplayName("customizers를 customize 하면 yml의 세팅값을 그대로 적용할 수 있다.")
    @Test
    fun testYmlSettingsAppliedWithCustomizedCustomizers() {
        assertThat(objectMapper.propertyNamingStrategy).isEqualTo(PropertyNamingStrategies.SNAKE_CASE)
    }

    @DisplayName("customizers를 customize 하면 Bean으로 등록된 Module의 Serializer가 자동으로 Install 된다.")
    @Test
    fun testBeanRegisteredModuleSerializersAutoInstallWithCustomizedCustomizers() {
        val user = User(1, "John Doe", "secret")
        val json = objectMapper.writeValueAsString(user)

        val expectedJson = """{"user_id":1,"name":"John Doe"}"""
        assertThat(expectedJson).isEqualTo(json)
    }

    @DisplayName("customizers를 customize 하면 Bean으로 등록된 Module의 Deserializer가 자동으로 Install 된다.")
    @Test
    fun testBeanRegisteredModuleDeserializersAutoInstallWithCustomizedCustomizers() {
        val json = """{"user_id":1,"name":"John Doe"}"""
        val user: User = objectMapper.readValue(json)

        val expectedUser = User(1, "John Doe", "")
        assertThat(expectedUser).isEqualTo(user)
    }
}
