package com.example.taskflow.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class MetadatosTarea(
    val prioridad: String = "Media",
    val fecha: String = "Hoy"
)

object EstadoTareasUi {
    val porNombre = mutableStateMapOf<String, MetadatosTarea>()
    val porId = mutableStateMapOf<Int, MetadatosTarea>()

    var modoOscuro by mutableStateOf(false)
    var sonidoAlAvisar by mutableStateOf(true)

    fun registrarNueva(nombre: String, prioridad: String, fecha: String) {
        porNombre[nombre.trim()] = MetadatosTarea(prioridad = prioridad, fecha = fecha)
    }

    fun obtener(id: Int, nombre: String): MetadatosTarea {
        return porId[id]
            ?: porNombre[nombre.trim()]
            ?: MetadatosTarea(prioridad = "Media", fecha = "Hoy")
    }
}