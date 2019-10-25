package es.seatcode.testingcoroutines.data

import arrow.core.Either
import es.seatcode.testingcoroutines.data.datasource.UsersDataSource
import es.seatcode.testingcoroutines.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class DataSourceGetUsersRepository(val dataSource: UsersDataSource) : UsersRepository {
    override suspend fun getUsersOneByOne(numberOfUsers: Int): Either<Throwable, List<User>> {
        return  dataSource.getUsersOneByOne(numberOfUsers)
    }

    override suspend fun getUsersParallel(numberOfUsers: Int): Either<Throwable, List<User>> {
        return withContext(Dispatchers.IO) { dataSource.getUsersConcurrent(numberOfUsers) }
    }

    override suspend fun getUsers(): Either<Throwable, List<User>> {
        return withContext(Dispatchers.IO) { dataSource.getUsers() }
    }
}
