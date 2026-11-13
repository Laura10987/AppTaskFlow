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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskflow.R
import com.example.taskflow.ui.theme.TaskFlowTheme

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
    onAgregarTareaClick: () -> Unit = {},
    onConfiguracionClick: () -> Unit = {},
    onTareaClick: (Tarea) -> Unit = {}
) {
    // Lista de tareas de ejemplo para visualizar y probar la interfaz
    val listaTareas = remember {
        mutableStateListOf(
            Tarea(
                id = 1,
                nombre = "Entregar prototipo de TaskFlow",
                notas = "Subir reporte digital y código en Android Studio",
                prioridad = "Alta",
                fecha = "15 Nov 2026",
                completada = false
            ),
            Tarea(
                id = 2,
                nombre = "Estudiar Jetpack Compose",
                notas = "Repasar estados, LazyColumn y navegación",
                prioridad = "Media",
                fecha = "17 Nov 2026",
                completada = false
            ),
            Tarea(
                id = 3,
                nombre = "Comprar cuaderno de notas",
                notas = "Para apuntes de Desarrollo de Aplicaciones Móviles",
                prioridad = "Baja",
                fecha = "12 Nov 2026",
                completada = true
            )
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
                        IconButton(onClick = onConfiguracionClick) {
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
            // Botón flotante con color secondary (amarillo mostaza) de MaterialTheme
            FloatingActionButton(
                onClick = onAgregarTareaClick,
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
                        onCheckedChange = { marcada ->
                            val index = listaTareas.indexOfFirst { it.id == tarea.id }
                            if (index != -1) {
                                listaTareas[index] = tarea.copy(completada = marcada)
                            }
                        },
                        onClick = { onTareaClick(tarea) }
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
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorPrioridad.copy(alpha = 0.12f))
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrimeraPantallaPreview() {
    TaskFlowTheme {
        PrimeraPantalla()
    }
}


