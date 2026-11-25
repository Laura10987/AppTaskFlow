package com.example.taskflow.data.repository

import com.example.taskflow.data.database.dao.ProductoDao
import com.example.taskflow.data.mapper.toDomain
import com.example.taskflow.data.mapper.toEntity
import com.example.taskflow.domain.model.Producto
import com.example.taskflow.domain.repository.ProductoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductoRepositoryImp @Inject constructor(
    private val productoDao: ProductoDao
) : ProductoRepository {

    override fun obtenerProductos(): Flow<List<Producto>> {
        return productoDao.obtenerProductos().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insertarProducto(producto: Producto) {
        productoDao.insertarProducto(producto.toEntity())
    }

    override suspend fun actualizarProducto(producto: Producto) {
        productoDao.actualizarProducto(producto.toEntity())
    }

    override suspend fun deleteProducto(producto: Producto) {
        productoDao.eliminarProducto(producto.toEntity())
    }
}

