package com.example.taskflow.data.mapper

import com.example.taskflow.data.database.entity.ProductoEntity
import com.example.taskflow.domain.model.Producto

fun ProductoEntity.toDomain(): Producto {
    return Producto(
        id = id,
        nombre = nombre,
        descripcion = descripcion
    )
}

fun Producto.toEntity(): ProductoEntity {
    return ProductoEntity(
        id = id,
        nombre = nombre,
        descripcion = descripcion
    )
}

