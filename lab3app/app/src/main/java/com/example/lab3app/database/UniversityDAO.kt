package com.example.lab3app.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lab3app.data.Faculty
import com.example.lab3app.data.University
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface UniversityDAO {
    @Query("select * from University order by university_name")
    fun getUniversities(): Flow<List<University>>

    @Insert(entity = University::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUniversity(university: University)

    @Update(entity = University::class)
    suspend fun updateUniversity(university: University)

    @Insert(entity = University::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListUniversity(universityList: List<University>)

    @Delete(entity = University::class)
    suspend fun deleteUniversity(university: University)

    @Query("delete from University")
    suspend fun deleteAllUniversities()



    @Query("select * from faculties order by faculty_name")
    fun getFaculties(): Flow<List<Faculty>>

    @Query("select * from faculties where university_id=:universityID")
    fun getUniversityFaculties(universityID: UUID): Flow<List<Faculty>>

    @Insert(entity = Faculty::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFaculty(faculty: Faculty)

    @Update(entity = Faculty::class)
    suspend fun updateFaculty(faculty: Faculty)

    @Insert(entity = Faculty::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListFaculty(facultyList: List<Faculty>)

    @Delete(entity = Faculty::class)
    suspend fun deleteFaculty(faculty: Faculty)

    @Query("delete from faculties where university_id=:universityID")
    suspend fun deleteUniversityFaculties(universityID: UUID)

    @Query("delete from faculties")
    suspend fun deleteAllFaculties()
}