package com.example.networkinteractions.data.database.model

import androidx.room.ColumnInfo

data class SourceDb(
    @ColumnInfo(name = "source_id")
    val id: String,
    val name: String,
)