package com.funin.objectmapperlearning.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

class UserSerializer : JsonSerializer<User>() {
    override fun serialize(user: User, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()
        gen.writeNumberField("user_id", user.id)
        gen.writeStringField("name", user.name)
        // Password 필드는 직렬화하지 않음
        gen.writeEndObject()
    }
}
