package ru.quipy.logic

import ru.quipy.api.UserCreatedEvent

fun UserAggregateState.create(
    fullname: String,
    nickname: String,
    password: CharArray
): UserCreatedEvent {
    if (fullname.isBlank())
        throw IllegalArgumentException("Fullname must be contain > 0 char: $fullname")
    if (nickname.isBlank())
        throw IllegalArgumentException("Nickname must be contain > 0 char: $nickname")
    if (password.isEmpty())
        throw IllegalArgumentException("Password must be contain > 0 char: $password")
    return UserCreatedEvent(
        fullname, nickname, password
    )
}