package ru.quipy.logic

import ru.quipy.api.ProjectAddedNewPartisipantEvent
import ru.quipy.api.ProjectCreatedEvent
import ru.quipy.api.StatusCreatedEvent
import ru.quipy.api.StatusDeletedEvent
import java.util.*


fun ProjectAggregateState.create(name: String, creatorId: UUID): ProjectCreatedEvent {
    if (name.isBlank())
        throw IllegalArgumentException("Project name must be contain > 0 char: $name")
    return ProjectCreatedEvent(
        UUID.randomUUID(),
        name,
        creatorId
    )
}

// TODO: Нужно ли здесь передавать id проекта? Ведь мы как бы уже в нем.
//  С другой стороны это доп. проверка для того чтобы не накатить ивент на другой проекта по ошибке

fun ProjectAggregateState.addPartisipant(
    projectId: UUID,
    partisipantId: UUID,
    initiatorId: UUID
): ProjectAddedNewPartisipantEvent {
    if (getId() != projectId)
        throw IllegalArgumentException("Passed id is not equals to target project ID")
    if (!participantIds.contains(initiatorId))
        throw IllegalArgumentException("You are not a member of the project")
    if (participantIds.contains(partisipantId))
        throw IllegalArgumentException("User is already a partisipant of the project")
    return ProjectAddedNewPartisipantEvent(projectId, partisipantId, initiatorId)
}

fun ProjectAggregateState.createNewStatus(
    projectId: UUID,
    statusName: String,
    color: Color,
    initiatorId: UUID
): StatusCreatedEvent {
    if (getId() != projectId)
        throw IllegalArgumentException("Passed id is not equals to target project ID")
    if (statusName.isBlank())
        throw IllegalArgumentException("Status name must be contain > 0 char: $statusName")
    if (!participantIds.contains(initiatorId))
        throw IllegalArgumentException("You are not a member of the project")
    return StatusCreatedEvent(projectId, statusName, initiatorId, color)
}

fun ProjectAggregateState.deleteStatus(
    projectId: UUID,
    statusId: UUID,
    initiatorId: UUID
): StatusDeletedEvent {
    if (getId() != projectId)
        throw IllegalArgumentException("Passed id is not equals to target project ID")
    if (!statuses.containsKey(statusId))
        throw IllegalArgumentException("Status does not exist in target project : $statusId")
    if (!participantIds.contains(initiatorId))
        throw IllegalArgumentException("You are not a member of the project")
    return StatusDeletedEvent(projectId, statusId, initiatorId)
}