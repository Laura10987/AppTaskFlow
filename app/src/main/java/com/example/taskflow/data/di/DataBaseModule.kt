package com.example.taskflow.data.di

import android.content.Context
import androidx.room.Room
import com.example.taskflow.data.database.AppDatabase
import com.example.taskflow.data.database.dao.ProductoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module//8
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides//8
    fun getInstance(@ApplicationContext context: Context): AppDatabase {
        return synchronized(this) {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "taskflow.db"
            )
                .fallbackToDestructiveMigration(dropAllTables = true)
                .build()
        }
    }

    @Provides
    fun provideProductoDao(database: AppDatabase): ProductoDao {
        return database.productoDao()
    }
}