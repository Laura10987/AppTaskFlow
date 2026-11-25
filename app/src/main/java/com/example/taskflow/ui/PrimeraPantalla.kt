package com.example.taskflow.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskflow.R

// Modelo de datos para representar cada tarea en TaskFlow
data class Tarea(
    val id: Int,
    val nombre: String,
    val notas: String,
    val prioridad: String, // "Alta", "Media" o "Baja"
    val fecha: String,
    val completada: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimeraPantalla(
    viewModel: ProductoViewModel = hiltViewModel(),
    onAgregarTareaClick: () -> Unit = {},
    onConfiguracionClick: () -> Unit = {},
    onTareaClick: (Tarea) -> Unit = {}
) {
    // 1. Leemos los datos reales desde Room en tiempo real
    val productosDb by viewModel.producto.collectAsStateWithLifecycle()

    // 2. Estados de completadas, eliminadas, detalle y configuración
    var idsCompletadas by remember { mutableStateOf(setOf<Int>()) }
    var idsEliminadas by remember { mutableStateOf(setOf<Int>()) }
    var tareaSeleccionada by remember { mutableStateOf<Tarea?>(null) }
    var mostrarConfiguracion by remember { mutableStateOf(false) }

    val modoOscuro = EstadoTareasUi.modoOscuro
    val sonidoAlAvisar = EstadoTareasUi.sonidoAlAvisar

    // Si el usuario tocó el ícono de configuración, mostramos ConfiguracionPantalla
    if (mostrarConfiguracion) {
        ConfiguracionPantalla(
            modoOscuro = modoOscuro,
            onModoOscuroChange = { EstadoTareasUi.modoOscuro = it },
            sonidoAlAvisar = sonidoAlAvisar,
            onSonidoAlAvisarChange = { EstadoTareasUi.sonidoAlAvisar = it },
            onBorrarTerminadas = {
                idsEliminadas = idsEliminadas + idsCompletadas
                idsCompletadas.forEach { idCompletado ->
                    EstadoTareasUi.porId.remove(idCompletado)
                }
                idsCompletadas = emptySet()
            },
            onBackClick = { mostrarConfiguracion = false }
        )
        return
    }

    // 3. Convertimos cada Producto de la BD a tu modelo Tarea usando EstadoTareasUi
    val listaTareas = productosDb
        .filter { it.id !in idsEliminadas }
        .map { prod ->
            val meta = EstadoTareasUi.obtener(prod.id, prod.nombre)
            if (prod.id !in EstadoTareasUi.porId && prod.nombre.trim() in EstadoTareasUi.porNombre) {
                EstadoTareasUi.porId[prod.id] = meta
            }

            Tarea(
                id = prod.id,
                nombre = prod.nombre,
                notas = prod.descripcion,
                prioridad = meta.prioridad,
                fecha = meta.fecha,
                completada = prod.id in idsCompletadas
            )
        }

    // Pestañas obtenidas desde strings.xml
    val filtros = listOf(
        stringResource(id = R.string.filtro_todas),
        stringResource(id = R.string.filtro_pendientes),
        stringResource(id = R.string.filtro_terminadas)
    )
    var filtroSeleccionado by remember { mutableIntStateOf(0) }

    // Filtrado dinámico según la pestaña activa
    val tareasFiltradas = when (filtroSeleccionado) {
        1 -> listaTareas.filter { !it.completada }
        2 -> listaTareas.filter { it.completada }
        else -> listaTareas
    }

    // Diálogo para ver o eliminar la tarea seleccionada
    tareaSeleccionada?.let { tarea ->
        val colorPrioridad = when (tarea.prioridad) {
            "Alta" -> Color(0xFFE53935)
            "Media" -> Color(0xFFFB8C00)
            else -> Color(0xFF43A047)
        }

        AlertDialog(
            onDismissRequest = { tareaSeleccionada = null },
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            textContentColor = MaterialTheme.colorScheme.onSurface,
            title = {
                Text(
                    text = tarea.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(colorPrioridad)
                        )
                        Text(
                            text = "Prioridad: ${tarea.prioridad}",
                            fontWeight = FontWeight.Medium,
                            color = colorPrioridad,
                            fontSize = 14.sp
                        )
                    }

                    Text(
                        text = stringResource(id = R.string.entrega_formato, tarea.fecha),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = if (tarea.completada) "Estado: Terminada" else "Estado: Pendiente",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                    Text(
                        text = "Notas",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (tarea.notas.isNotBlank()) tarea.notas else "Sin notas adicionales.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        idsEliminadas = idsEliminadas + tarea.id
                        idsCompletadas = idsCompletadas - tarea.id
                        EstadoTareasUi.porId.remove(tarea.id)
                        EstadoTareasUi.porNombre.remove(tarea.nombre.trim())

                        // Si tienes función para borrar de Room en ProductoViewModel, llámala aquí:
                        // productosDb.find { it.id == tarea.id }?.let { viewModel.eliminarProducto(it) }

                        tareaSeleccionada = null
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { tareaSeleccionada = null }) {
                    Text("Cerrar", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column {
                // Barra superior
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.titulo_mis_tareas),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                mostrarConfiguracion = true
                                onConfiguracionClick()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = stringResource(id = R.string.desc_configuracion),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )

                // Pestañas de filtro: Todas, Pendientes y Terminadas
                TabRow(
                    selectedTabIndex = filtroSeleccionado,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    filtros.forEachIndexed { index, titulo ->
                        Tab(
                            selected = filtroSeleccionado == index,
                            onClick = { filtroSeleccionado = index },
                            text = {
                                Text(
                                    text = titulo,
                                    fontWeight = if (filtroSeleccionado == index) FontWeight.Bold else FontWeight.Normal,
                                    color = if (filtroSeleccionado == index) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            }
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAgregarTareaClick() // Solo navega a la pantalla de formulario
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.desc_agregar_tarea),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { paddingValues ->
        if (tareasFiltradas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.sin_tareas),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tareasFiltradas, key = { it.id }) { tarea ->
                    TarjetaTarea(
                        tarea = tarea,
                        onCheckedChange = { estaMarcada ->
                            idsCompletadas = if (estaMarcada) {
                                idsCompletadas + tarea.id
                            } else {
                                idsCompletadas - tarea.id
                            }
                        },
                        onClick = {
                            tareaSeleccionada = tarea
                            onTareaClick(tarea)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TarjetaTarea(
    tarea: Tarea,
    onCheckedChange: (Boolean) -> Unit,
    onClick: () -> Unit
) {
    val colorPrioridad = when (tarea.prioridad) {
        "Alta" -> Color(0xFFE53935)
        "Media" -> Color(0xFFFB8C00)
        else -> Color(0xFF43A047)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = tarea.completada,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tarea.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (tarea.completada) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                    textDecoration = if (tarea.completada) TextDecoration.LineThrough else TextDecoration.None,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (tarea.notas.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = tarea.notas,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = R.string.entrega_formato, tarea.fecha),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorPrioridad.copy(alpha = 0.14f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(colorPrioridad)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = tarea.prioridad,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorPrioridad
                )
            }
        }
    }
}