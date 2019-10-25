package es.seatcode.testingcoroutines.async.api.model

import ApiName
import Id
import Picture
import es.seatcode.testingcoroutines.model.Name
import es.seatcode.testingcoroutines.model.User


data class UserApi(
    val gender: String,
    val name: ApiName,
    val email: String,
    val phone: String,
    val cell: String,
    @Transient
    val id: Id,
    val picture: Picture,
    val nat: String
)

fun UserApi.toUser(): User {
    return User(
        gender = this.gender,
        name = Name(this.name.title, this.name.first, this.name.last),
        email = this.email,
        phone = this.phone
    )
}
