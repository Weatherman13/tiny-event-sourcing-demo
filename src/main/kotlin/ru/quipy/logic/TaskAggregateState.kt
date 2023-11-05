package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

class TaskAggregateState : AggregateState<UUID, TaskAggregate> {
    private lateinit var id: UUID
    private var createdAt: Long = System.currentTimeMillis()
    private var updatedAt: Long = System.currentTimeMillis()
    lateinit var taskName: String
    lateinit var authorId: UUID
    lateinit var projectId: UUID
    lateinit var statusId: UUID

    var executorsIds = mutableListOf<UUID>()
    override fun getId() = id

    @StateTransitionFunc
    fun taskCreatedApply(event: TaskCreatedEvent) {
        id = event.taskId
        taskName = event.taskName
        authorId = event.authorId
        projectId = event.projectId
        statusId = event.statusId
        createdAt = event.createdAt
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun statusChangedApply(event: ChangeStatusEvent) {
        statusId = event.statusId
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun addExecutor(event: AddExecutorEvent) {
        executorsIds.add(event.executorId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun renameTask(event: RenameEvent) {
        taskName = event.newName
        updatedAt = event.createdAt
    }
}
