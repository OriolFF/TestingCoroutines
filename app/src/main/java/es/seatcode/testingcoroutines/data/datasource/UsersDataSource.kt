package es.seatcode.testingcoroutines.data.datasource

import arrow.core.Either
import es.seatcode.testingcoroutines.model.User


interface UsersDataSource {
    suspend fun getUsers(): Either<Throwable, List<User>>
    suspend fun getUsersOneByOne(numOfUsers: Int): Either<Throwable, List<User>>
    suspend fun getUsersConcurrent(numberOfUsers: Int): Either<Throwable, List<User>>
}
