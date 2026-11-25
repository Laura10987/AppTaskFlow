package com.example.taskflow.domain.repository

import com.example.taskflow.domain.model.Producto
import kotlinx.coroutines.flow.Flow

interface ProductoRepository {
    fun obtenerProductos(): Flow<List<Producto>>
    suspend fun insertarProducto(producto: Producto)
    suspend fun actualizarProducto(producto: Producto)
    suspend fun deleteProducto(producto: Producto)
}

