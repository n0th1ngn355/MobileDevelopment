package com.example.lab3app.database

import androidx.room.Database
import androidx.room.TypeConverters
import com.example.lab3app.data.Faculty
import com.example.lab3app.data.University


@Database(
    entities = [University::class, Faculty::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(UniversityTypeConverter::class)

class UniversityDB {

    
}