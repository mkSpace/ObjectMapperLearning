package com.funin.objectmapperlearning.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class UserDeserializer : JsonDeserializer<User>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): User {
        val node = p.codec.readTree<com.fasterxml.jackson.databind.node.ObjectNode>(p)
        val id = node.get("user_id").asInt()
        val name = node.get("name").asText()
        // 역직렬화할 때 패스워드 필드는 기본 값으로 설정
        return User(id, name, "")
    }
}
