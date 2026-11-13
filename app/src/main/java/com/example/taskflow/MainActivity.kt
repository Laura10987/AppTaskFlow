package com.example.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskflow.ui.PantallaInicio
import com.example.taskflow.ui.PrimeraPantalla
import com.example.taskflow.ui.theme.TaskFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskFlowTheme {
                NavegacionApp()
            }
        }
    }
}

@Composable
fun NavegacionApp() {
    // 1. Controlador que gestiona los cambios entre pantallas
    val navController = rememberNavController()

    // 2. NavHost define cuál es la pantalla inicial y las rutas disponibles
    NavHost(
        navController = navController,
        startDestination = "pantalla_inicio"
    ) {
        // Primera vista: Pantalla de Inicio
        composable(route = "pantalla_inicio") {
            PantallaInicio(
                onContinuarClick = {
                    // Al presionar "Comenzar", navega hacia PrimeraPantalla
                    navController.navigate("primera_pantalla") {
                        // Evita regresar a la pantalla de inicio al presionar "Atrás"
                        popUpTo("pantalla_inicio") { inclusive = true }
                    }
                }
            )
        }

        // Segunda vista: PrimeraPantalla (Lista de tareas)
        composable(route = "primera_pantalla") {
            PrimeraPantalla()
        }
    }
}