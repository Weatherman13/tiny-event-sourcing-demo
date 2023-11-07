package ru.quipy.dto

import ru.quipy.logic.Color
import java.util.*

data class CreatedStatusEventDto(
    val statusName: String,
    val initiatorId: UUID,
    val color: Color
)
