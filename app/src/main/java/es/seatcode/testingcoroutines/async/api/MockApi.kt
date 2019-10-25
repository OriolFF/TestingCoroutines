package es.seatcode.testingcoroutines.async.api

import es.seatcode.testingcoroutines.model.Name
import es.seatcode.testingcoroutines.model.User
import kotlin.random.Random


class MockApi() : es.seatcode.testingcoroutines.async.api.Api {
    private val charPool = ('a'..'z').toList()
    private val capCharPool = ('A'..'Z').toList()
    override fun getUsers(users: Int): List<User> {
        Thread.sleep(6000)
        return (0..users).map { generateUser() }
    }

    fun generateUser(): User {
        val initial = capCharPool.get(Random.nextInt(0, capCharPool.size))
        val randomString = (1..10)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
        return User(
            //id = Random.nextInt(1000, 9999).toString(),
            name = Name(initial + randomString, "", ""),
            phone = "93 ${Random.nextLong(10000000, 99999999)}",
            email = "",
            gender = ""
        )

    }
}
