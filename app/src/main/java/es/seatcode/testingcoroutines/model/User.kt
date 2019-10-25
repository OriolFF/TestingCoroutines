package es.seatcode.testingcoroutines.model


data class User(val gender: String, val name: Name, val email: String, val phone: String)
data class Name(val title: String, val first: String, val last: String)
