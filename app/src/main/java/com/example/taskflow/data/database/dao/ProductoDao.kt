package com.example.taskflow.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskflow.data.database.entity.ProductoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Insert
    suspend fun insertarProducto(producto: ProductoEntity)

    @Update
    suspend fun actualizarProducto(producto: ProductoEntity)

    @Delete
    suspend fun eliminarProducto(producto: ProductoEntity)

    @Query("SELECT * FROM product WHERE id = :id")
    fun getItem(id: Int): Flow<ProductoEntity?>

    @Query("SELECT * FROM product ORDER BY nombre DESC")
    fun obtenerProductos(): Flow<List<ProductoEntity>>
}
