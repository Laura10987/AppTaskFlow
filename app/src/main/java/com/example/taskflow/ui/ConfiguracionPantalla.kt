package com.example.taskflow.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracionPantalla(
    modoOscuro: Boolean = EstadoTareasUi.modoOscuro,
    onModoOscuroChange: (Boolean) -> Unit = { EstadoTareasUi.modoOscuro = it },
    sonidoAlAvisar: Boolean = EstadoTareasUi.sonidoAlAvisar,
    onSonidoAlAvisarChange: (Boolean) -> Unit = { EstadoTareasUi.sonidoAlAvisar = it },
    onBorrarTerminadas: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var mostrarConfirmacionBorrar by remember { mutableStateOf(false) }

    val azulPrincipal = if (modoOscuro) Color(0xFF1E2A78) else Color(0xFF3949AB)
    val fondoPantalla = if (modoOscuro) Color(0xFF121212) else Color(0xFFF4F6FA)
    val superficieTarjeta = if (modoOscuro) Color(0xFF1E1E1E) else Color.White
    val textoPrincipal = if (modoOscuro) Color(0xFFF1F5F9) else Color(0xFF1A202C)
    val textoSecundario = if (modoOscuro) Color(0xFF94A3B8) else Color(0xFF718096)
    val divisorColor = if (modoOscuro) Color(0xFF2D3748) else Color(0xFFEDF2F7)
    val fondoIconoAzul = if (modoOscuro) Color(0xFF283593).copy(alpha = 0.35f) else Color(0xFFEEF2FF)
    val tinteIconoAzul = if (modoOscuro) Color(0xFF9FA8DA) else azulPrincipal
    val rojoSuave = Color(0xFFEF5350)

    if (mostrarConfirmacionBorrar) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacionBorrar = false },
            containerColor = superficieTarjeta,
            titleContentColor = textoPrincipal,
            textContentColor = textoSecundario,
            title = {
                Text(
                    text = "Borrar terminadas",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text("¿Seguro que deseas eliminar todas las tareas completadas?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarConfirmacionBorrar = false
                        onBorrarTerminadas()
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = rojoSuave)
                ) {
                    Text("Borrar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmacionBorrar = false }) {
                    Text("Cancelar", color = textoSecundario)
                }
            }
        )
    }

    Scaffold(
        containerColor = fondoPantalla,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckBox,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            text = "Configuración",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 19.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = azulPrincipal
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                // Sección PREFERENCIAS
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "PREFERENCIAS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = textoSecundario
                    )
                    Text(
                        text = "Personaliza tu experiencia en TaskFlow.",
                        fontSize = 13.sp,
                        color = textoSecundario
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = superficieTarjeta),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            // Modo oscuro
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onModoOscuroChange(!modoOscuro) }
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(fondoIconoAzul),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Contrast,
                                        contentDescription = null,
                                        tint = tinteIconoAzul,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Modo oscuro",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 15.sp,
                                        color = textoPrincipal
                                    )
                                    Text(
                                        text = "Reduce el brillo de la pantalla",
                                        fontSize = 12.sp,
                                        color = textoSecundario
                                    )
                                }

                                Switch(
                                    checked = modoOscuro,
                                    onCheckedChange = onModoOscuroChange,
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = Color(0xFF3949AB)
                                    )
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = divisorColor
                            )

                            // Sonido al avisar
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onSonidoAlAvisarChange(!sonidoAlAvisar) }
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(fondoIconoAzul),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.NotificationsActive,
                                        contentDescription = null,
                                        tint = tinteIconoAzul,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Sonido al avisar",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 15.sp,
                                        color = textoPrincipal
                                    )
                                    Text(
                                        text = "Reproducir sonido en recordatorios",
                                        fontSize = 12.sp,
                                        color = textoSecundario
                                    )
                                }

                                Switch(
                                    checked = sonidoAlAvisar,
                                    onCheckedChange = onSonidoAlAvisarChange,
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = Color(0xFF3949AB)
                                    )
                                )
                            }
                        }
                    }
                }

                // Sección DATOS
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "DATOS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = textoSecundario
                    )

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = superficieTarjeta),
                        border = BorderStroke(1.dp, rojoSuave.copy(alpha = 0.30f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { mostrarConfirmacionBorrar = true }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(rojoSuave.copy(alpha = 0.14f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DeleteOutline,
                                    contentDescription = null,
                                    tint = rojoSuave,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Borrar terminadas",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = textoPrincipal
                                )
                                Text(
                                    text = "Elimina todas las tareas completadas",
                                    fontSize = 12.sp,
                                    color = textoSecundario
                                )
                            }

                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null,
                                tint = textoSecundario
                            )
                        }
                    }
                }
            }

            // Pie inferior: TaskFlow y Versión 1.0.0
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp, bottom = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(azulPrincipal),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckBox,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "TaskFlow",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = textoPrincipal
                )
                Text(
                    text = "Versión 1.0.0",
                    fontSize = 12.sp,
                    color = textoSecundario
                )
            }
        }
    }
}