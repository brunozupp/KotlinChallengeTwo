package com.novelitech.kotlinchallengetwo.models

import java.time.LocalDateTime
import java.util.Date

data class TodoItemModel(
    val id: String,
    val text: String,
    val done: Boolean,
    val createdOn: LocalDateTime,
    val updatedOn: LocalDateTime?,
)