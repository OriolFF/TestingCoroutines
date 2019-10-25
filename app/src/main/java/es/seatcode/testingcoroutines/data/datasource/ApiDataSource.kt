package es.seatcode.testingcoroutines.data.datasource

import arrow.core.Either
import es.seatcode.testingcoroutines.async.api.model.toUser
import es.seatcode.testingcoroutines.model.User
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.system.measureTimeMillis


class ApiDataSource(val api: es.seatcode.testingcoroutines.async.api.WebserviceApi) :
    UsersDataSource {

    override suspend fun getUsers(): Either<Throwable, List<User>> {
        val results = api.getResultsAsync()
        return if (results.isSuccessful) {
            val users = results.body()!!.users
            Either.Right(users.map { it.toUser() })
        } else {
            Either.Left(Exception())
        }
    }

    //EXAMPLE OF SERIAL USE
    override suspend fun getUsersOneByOne(numberOfUsers: Int): Either<Throwable, List<User>> {
        val users: MutableList<User> = mutableListOf()
        val time = measureTimeMillis {
            repeat(numberOfUsers) {
                val oneUser = api.getResultsSync().users[0].toUser()
                println("COROUTINES recovered one user:$oneUser")
                users.add(oneUser)
                //Thread.sleep(1000)
                //delay(1000)
            }
        }
        println("COROUTINES recovered all users in:$time")
        return if (users.size > 0) {
            Either.Right(users)
        } else {
            Either.Left(Exception())
        }
    }

    //EXAMPLE OF CONCURRENT USE
    override suspend fun getUsersConcurrent(numberOfUsers: Int): Either<Throwable, List<User>> {
        var users: List<User> = listOf()
        val deferreds =
            ConcurrentLinkedQueue<Deferred<User>>() // mutable lists are base on arrayList witch is not thread safe
        withContext(Dispatchers.IO) {
            repeat(numberOfUsers) {
                val oneUserDeferred = async {
                    api.getResultsSync().users[0].toUser()
                }
                deferreds.add(oneUserDeferred)
                println("COROUTINES recovered one user")
            }

            users = deferreds.awaitAll()
            println("COROUTINES recovered all users in:, users recovered:${users.size}")
        }
        return if (users.isNotEmpty()) {
            Either.Right(users.toList())
        } else {
            Either.Left(Exception())
        }
    }
}
