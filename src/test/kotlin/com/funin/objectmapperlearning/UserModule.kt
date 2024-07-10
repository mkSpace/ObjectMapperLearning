package com.funin.objectmapperlearning

import com.fasterxml.jackson.databind.module.SimpleModule
import com.funin.objectmapperlearning.jackson.User
import com.funin.objectmapperlearning.jackson.UserDeserializer
import com.funin.objectmapperlearning.jackson.UserSerializer

class UserModule: SimpleModule() {

    init {
        addSerializer(User::class.java, UserSerializer())
        addDeserializer(User::class.java, UserDeserializer())
    }

}
