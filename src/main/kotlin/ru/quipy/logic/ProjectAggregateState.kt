package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

// Service's business logic
class ProjectAggregateState : AggregateState<UUID, ProjectAggregate> {
    private lateinit var id: UUID
    private var createdAt: Long = System.currentTimeMillis()
    private var updatedAt: Long = System.currentTimeMillis()
    lateinit var title: String
    lateinit var authorId: UUID

    var statuses = mutableMapOf<UUID, Status>()
    var participantIds = mutableListOf<UUID>()
    override fun getId() = id

    // State transition functions which is represented by the class member function
    @StateTransitionFunc
    fun projectCreatedApply(event: ProjectCreatedEvent) {
        id = event.projectId
        title = event.title
        authorId = event.authorId
        updatedAt = createdAt
        statuses[event.statusId] = Status(event.statusId, event.statusName, Color(0.1, 0.1, 0.1, 0.1), 1)
        participantIds.add(event.authorId)

    }

    @StateTransitionFunc
    fun partisipantAddedApply(event: ProjectAddedNewPartisipantEvent) {
        participantIds.add(event.partisipantId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun statusCreatedApply(event: StatusCreatedEvent) {
        statuses[event.statusId] = Status(event.statusId, event.statusName, event.color, statuses.size + 1)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun statusDeletedApply(event: StatusDeletedEvent) {
        statuses.remove(event.statusId)
        updatedAt = event.createdAt
    }
}

data class User(
    val id: UUID,
    val name: String,
    val nickname: String
)

data class Status(
    val id: UUID,
    val name: String,
    val color: Color,
    var order: Int
)

data class Color(
    val red: Double,
    val green: Double,
    val blue: Double,
    val opacity: Double
)

