package com.example.taskflow.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskflow.domain.model.Producto
import com.example.taskflow.domain.repository.ProductoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel//8
class ProductoViewModel @Inject constructor(//8
    private val repository: ProductoRepository
) : ViewModel() {//8

    val producto: StateFlow<List<Producto>> = repository.obtenerProductos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )//6

    fun agregarProducto(nombre: String, desc: String) {
        val trimNombre: String = nombre.trim()
        if (trimNombre.isEmpty()) return
        viewModelScope.launch {
            repository.insertarProducto(
                Producto(
                    nombre = trimNombre,
                    descripcion = desc.trim()
                )
            )
        }
    }

    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            repository.deleteProducto(producto)
        }
    }

    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch {
            repository.actualizarProducto(producto)
        }
    }
}