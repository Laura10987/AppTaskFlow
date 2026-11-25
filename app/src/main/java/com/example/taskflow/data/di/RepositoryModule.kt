package com.example.taskflow.data.di

import com.example.taskflow.data.repository.ProductoRepositoryImp
import com.example.taskflow.domain.repository.ProductoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductoRepository(
        productoRepository: ProductoRepositoryImp
    ): ProductoRepository
}