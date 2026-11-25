package com.example.taskflow.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskflow.data.database.dao.ProductoDao
import com.example.taskflow.data.database.entity.ProductoEntity

@Database(
    entities = [ProductoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
}