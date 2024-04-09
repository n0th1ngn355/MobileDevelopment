package com.example.lab3app.repository

import com.example.lab3app.data.Faculty
import com.example.lab3app.data.University
import com.example.lab3app.database.UniversityDAO
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class DBRepository(val dao: UniversityDAO): UniversityDBRepository {
    override fun getUniversities(): Flow<List<University>> = dao.getUniversities()

    override suspend fun insertUniversity(university: University) = dao.insertUniversity(university)

    override suspend fun updateUniversity(university: University) = dao.updateUniversity(university)

    override suspend fun insertListUniversity(universityList: List<University>) = dao.insertListUniversity(universityList)

    override suspend fun deleteUniversity(university: University) = dao.deleteUniversity(university)

    override suspend fun deleteAllUniversities() = dao.deleteAllUniversities()

    override fun getFaculties(): Flow<List<Faculty>> = dao.getFaculties()

    override fun getUniversityFaculties(universityID: UUID): Flow<List<Faculty>> = dao.getUniversityFaculties(universityID)

    override suspend fun insertFaculty(faculty: Faculty) = dao.insertFaculty(faculty)

    override suspend fun updateFaculty(faculty: Faculty) = dao.updateFaculty(faculty)

    override suspend fun insertListFaculty(facultyList: List<Faculty>) = dao.insertListFaculty(facultyList)

    override suspend fun deleteFaculty(faculty: Faculty) = dao.deleteFaculty(faculty)

    override suspend fun deleteUniversityFaculties(universityID: UUID) = dao.deleteUniversityFaculties(universityID)

    override suspend fun deleteAllFaculties() = dao.deleteAllFaculties()
}