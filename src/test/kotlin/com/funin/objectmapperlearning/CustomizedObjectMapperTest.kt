package com.funin.objectmapperlearning

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
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

}
