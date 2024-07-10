package com.funin.objectmapperlearning

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(PlainObjectMapperConfiguration::class)
class PlainObjectMapperTest {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @DisplayName("ObjectMapper를 직접 생성하면 yml 세팅을 무시한다.")
    @Test
    fun testPlainObjectMapperCreationIgnoresYmlSettings() {
        assertThat(objectMapper.propertyNamingStrategy).isNull()
    }

}
