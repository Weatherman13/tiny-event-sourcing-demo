package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.api.*
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.*
import java.util.*

@RestController
@RequestMapping("/tasks")
class TaskController(
    val taskEsService: EventSourcingService<UUID, TaskAggregate, TaskAggregateState>
) {
    @PostMapping
    fun create(
        @RequestParam taskName: String,
        @RequestParam authorId: UUID,
        @RequestParam projectId: UUID,
        @RequestParam statusId: UUID,
    ): TaskCreatedEvent {
        return taskEsService.create { it.create(taskName, authorId, projectId, statusId) }
    }

    @GetMapping("/{taskId}")
    fun getUser(@PathVariable taskId: UUID): TaskAggregateState? {
        return taskEsService.getState(taskId)
    }

    @PatchMapping("/{taskId}/statuses")
    fun changeStatus(
        @RequestParam partisipantId: UUID,
        @RequestParam statusId: UUID,
        @PathVariable taskId: UUID,
    ): ChangeStatusEvent {
        return taskEsService.update(taskId) { it.changeStatus(partisipantId, statusId, taskId) }
    }

    @PatchMapping("/{taskId}/executors")
    fun addExecutor(
        @RequestParam executorId: UUID,
        @PathVariable taskId: UUID,
    ): AddExecutorEvent {
        return taskEsService.update(taskId) { it.addExecutor(executorId, taskId) }
    }

    @PatchMapping("/{taskId}")
    fun renameTask(
        @RequestParam newName: String,
        @PathVariable taskId: UUID,
    ): RenameEvent {
        return taskEsService.update(taskId) { it.rename(taskId, newName) }
    }
}