package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.api.*
import ru.quipy.core.EventSourcingService
import ru.quipy.dto.CreatedStatusEventDto
import ru.quipy.logic.*
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>
) {

    @PostMapping("/{projectTitle}")
    fun createProject(@PathVariable projectTitle: String, @RequestParam creatorId: UUID): ProjectCreatedEvent {
        return projectEsService.create { it.create(projectTitle, creatorId) }
    }

    @GetMapping("/{projectId}")
    fun getProject(@PathVariable projectId: UUID): ProjectAggregateState? {
        return projectEsService.getState(projectId)
    }

    @PostMapping("/{projectId}/partisipants")
    fun addNewPartisipant(
        @PathVariable projectId: UUID,
        @RequestParam partisipantId: UUID,
        @RequestParam initiatorId: UUID
    ): ProjectAddedNewPartisipantEvent {
        return projectEsService.update(projectId) {
            it.addPartisipant(projectId, partisipantId, initiatorId)
        }
    }

    @PostMapping("/{projectId}/statuses")
    fun addNewStatus(
        @PathVariable projectId: UUID,
        @RequestBody dto: CreatedStatusEventDto
    ): StatusCreatedEvent {
        return projectEsService.update(projectId) {
            it.createNewStatus(projectId, dto.statusName, dto.color, dto.initiatorId)
        }
    }

    @DeleteMapping("/{projectId}/statuses/{statusId}")
    fun deleteStatus(
        @PathVariable projectId: UUID,
        @PathVariable statusId: UUID,
        @RequestParam initiatorId: UUID

    ): StatusDeletedEvent {
        return projectEsService.update(projectId) {
            it.deleteStatus(projectId, statusId, initiatorId)
        }
    }
}