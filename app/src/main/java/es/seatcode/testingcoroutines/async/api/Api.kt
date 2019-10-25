package es.seatcode.testingcoroutines.async.api

import es.seatcode.testingcoroutines.model.User

interface Api {
    fun getUsers(users: Int = 5):List<User>
}
