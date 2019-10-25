package es.seatcode.testingcoroutines.data

import arrow.core.Either
import es.seatcode.testingcoroutines.model.User


interface UsersRepository {
    suspend fun getUsers(): Either<Throwable, List<User>>
    suspend fun getUsersOneByOne(numberOfUsers: Int): Either<Throwable, List<User>>
    suspend fun getUsersParallel(numberOfUsers: Int): Either<Throwable, List<User>>
}
