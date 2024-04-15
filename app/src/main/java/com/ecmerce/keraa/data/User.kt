package com.ecmerce.keraa.data

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    var imagePath: String = ""
) {
    constructor() : this("", "", "", "")

    fun copy(): User {
        //uses the fields name and property defined in the constructor
        return User(firstName, lastName, email, imagePath)
    }

    //or if you need a copy with a changed field
    fun copy(changedProperty: String): User {
        return User(firstName, lastName, email, imagePath)
    }
}
