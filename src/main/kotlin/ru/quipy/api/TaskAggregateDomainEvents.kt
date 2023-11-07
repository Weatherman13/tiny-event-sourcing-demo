package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val TASK_CREATED_EVENT = "TASK_CREATED_EVENT"
const val CHANGE_STATUS_EVENT = "CHANGE_STATUS_EVENT"
const val ADD_EXECUTOR_EVENT = "ADD_EXECUTOR_EVENT"
const val RENAME_EVENT = "RENAME_EVENT"

@DomainEvent(name = TASK_CREATED_EVENT)
class TaskCreatedEvent(
    val projectId: UUID,
    val authorId: UUID,
    val taskName: String,
    val statusId: UUID,
    val taskId: UUID = UUID.randomUUID(),
    createdAt: Long = System.currentTimeMillis()
) : Event<TaskAggregate>(
    name = TASK_CREATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = CHANGE_STATUS_EVENT)
class ChangeStatusEvent(
    val partisipantId: UUID,
    val statusId: UUID,
    val taskId: UUID,
    createdAt: Long = System.currentTimeMillis()
) : Event<TaskAggregate>(
    name = CHANGE_STATUS_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = ADD_EXECUTOR_EVENT)
class AddExecutorEvent(
    val taskId: UUID,
    val executorId: UUID,
    createdAt: Long = System.currentTimeMillis()
) : Event<TaskAggregate>(
    name = ADD_EXECUTOR_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = RENAME_EVENT)
class RenameEvent(
    val taskId: UUID,
    val newName: String,
    createdAt: Long = System.currentTimeMillis()
) : Event<TaskAggregate>(
    name = RENAME_EVENT,
    createdAt = createdAt,
)