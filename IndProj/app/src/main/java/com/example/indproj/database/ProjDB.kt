package com.example.indproj.database

import com.example.indproj.data.Category
import com.example.indproj.data.Product

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Category::class, Product::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(ProjTypeConverter::class)

abstract class ProjDB: RoomDatabase() {
    abstract fun projDAO(): ProjDAO

    companion object{
        @Volatile
        private var INSTANCE: ProjDB? = null

        fun getDatabase(context: Context): ProjDB{
            return INSTANCE ?: synchronized(this){
                buildDatabase(context).also{ INSTANCE=it}
            }
        }

//    val MIGRAION_2_3 = object : Migration(2,3){
//        override fun migrate(db: SupportSQLiteDatabase) {
//            TODO("Not yet implemented")
//        }
//    }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            ProjDB::class.java,
            "proj_database")
            .fallbackToDestructiveMigration()
//        .addMigrations(MIGRATION_2_3)
            .build()

    }
}