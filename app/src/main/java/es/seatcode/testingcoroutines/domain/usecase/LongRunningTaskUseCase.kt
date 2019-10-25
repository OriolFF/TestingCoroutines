package es.seatcode.testingcoroutines.domain.usecase

import arrow.core.Either
import es.seatcode.testingcoroutines.data.UsersRepository
import es.seatcode.testingcoroutines.model.User


class LongRunningTaskUseCase(private val repository: UsersRepository) {
    suspend fun getUsers(): Either<Throwable, List<User>> {
        return repository.getUsersOneByOne(5)
    }
}
