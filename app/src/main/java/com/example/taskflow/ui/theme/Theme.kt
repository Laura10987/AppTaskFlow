package com.example.taskflow.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.taskflow.ui.EstadoTareasUi

private val EsquemaClaro = lightColorScheme(
    primary = AzulFuerte,
    onPrimary = Color.White,
    secondary = AmarilloMostaza,
    onSecondary = TextoOscuro,
    background = FondoClaro,
    onBackground = TextoOscuro,
    surface = Color.White,
    onSurface = TextoOscuro,
    surfaceVariant = Color(0xFFF0F2F7),
    onSurfaceVariant = TextoSecundarioOscuro
)

private val EsquemaOscuro = darkColorScheme(
    primary = AzulOscuroPrimario,
    onPrimary = Color(0xFF10163A),
    secondary = AmarilloMostaza,
    onSecondary = TextoOscuro,
    background = FondoOscuro,
    onBackground = TextoClaro,
    surface = SuperficieOscura,
    onSurface = TextoClaro,
    surfaceVariant = Color(0xFF2A2A2A),
    onSurfaceVariant = TextoSecundarioClaro
)

@Composable
fun TaskFlowTheme(
    darkTheme: Boolean = EstadoTareasUi.modoOscuro,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) EsquemaOscuro else EsquemaClaro

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}