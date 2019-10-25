package es.seatcode.testingcoroutines.domain.usecase

import arrow.core.Either
import es.seatcode.testingcoroutines.data.UsersRepository
import es.seatcode.testingcoroutines.model.User

class GetUsersUseCase(private val repository: UsersRepository) {
    suspend fun getUsers(): Either<Throwable, List<User>> {
       // throw Exception()
        return repository.getUsers()
    }
}
