package com.example.lab3app.database

import androidx.room.TypeConverter
import java.util.UUID

class UniversityTypeConverter {
    @TypeConverter
    fun toUUID(uuid: String?): UUID?{
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String?{
        return uuid?.toString()
    }
}