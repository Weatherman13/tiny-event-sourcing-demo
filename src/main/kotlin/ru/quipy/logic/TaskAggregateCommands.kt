package ru.quipy.logic

import ru.quipy.api.AddExecutorEvent
import ru.quipy.api.ChangeStatusEvent
import ru.quipy.api.RenameEvent
import ru.quipy.api.TaskCreatedEvent
import java.util.*


fun TaskAggregateState.create(
    taskName: String,
    authorId: UUID,
    projectId: UUID,
    statusId: UUID,
): TaskCreatedEvent {
    if (taskName.isBlank())
        throw IllegalArgumentException("Task name must be contain > 0 char: $taskName")
    return TaskCreatedEvent(
        projectId, authorId, taskName, statusId
    )
}

// TODO: По идее, здесь мне нужно проверить: есть ли участник с таким ID в проекте и есть ли вообще таска с таким ID в проекте.
// TODO: Нормальной практикой считается делать здесь доп запросы к базе? Или это делается в другом месте, а в команде только
// TODO: валидация, которую возможно провернуть только с имеющимися полями агрегата?
fun TaskAggregateState.changeStatus(
    partisipantId: UUID,
    statusId: UUID,
    taskId: UUID,
): ChangeStatusEvent {
    return ChangeStatusEvent(
        partisipantId, statusId, taskId
    )
}

fun TaskAggregateState.addExecutor(
    executorId: UUID,
    taskId: UUID
): AddExecutorEvent {
    return AddExecutorEvent(
        taskId, executorId
    )
}

fun TaskAggregateState.rename(
    taskId: UUID,
    newName: String
): RenameEvent {
    if (taskName.isBlank())
        throw IllegalArgumentException("Task name must be contain > 0 char: $newName")
    if (taskName == newName)
        throw IllegalArgumentException("Such name is already used as task name")
    return RenameEvent(
        taskId, newName
    )
}
