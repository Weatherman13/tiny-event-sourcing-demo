package ru.quipy.logic

import ru.quipy.api.UserAggregate
import ru.quipy.api.UserCreatedEvent
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

class UserAggregateState : AggregateState<UUID, UserAggregate> {
    private lateinit var id: UUID
    private var createdAt: Long = System.currentTimeMillis()
    private var updatedAt: Long = System.currentTimeMillis()
    lateinit var fullname: String
    lateinit var nickname: String
    lateinit var password: CharArray

    override fun getId() = id

    @StateTransitionFunc
    fun userCreatedApply(event: UserCreatedEvent) {
        id = event.userId
        fullname = event.fullname
        password = event.password
        nickname = event.nickname
        updatedAt = createdAt

    }
}