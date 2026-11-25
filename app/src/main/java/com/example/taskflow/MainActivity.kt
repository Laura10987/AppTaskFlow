package com.example.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskflow.ui.EstadoTareasUi
import com.example.taskflow.ui.PantallaInicio
import com.example.taskflow.ui.PrimeraPantalla
import com.example.taskflow.ui.SegudaPantalla
import com.example.taskflow.ui.theme.TaskFlowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskFlowTheme(darkTheme = EstadoTareasUi.modoOscuro) {
                NavegacionApp()
            }
        }
    }
}

@Composable
fun NavegacionApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "pantalla_inicio"
    ) {
        composable(route = "pantalla_inicio") {
            PantallaInicio(
                onContinuarClick = {
                    navController.navigate("primera_pantalla") {
                        popUpTo("pantalla_inicio") { inclusive = true }
                    }
                }
            )
        }

        composable(route = "primera_pantalla") {
            PrimeraPantalla(
                onAgregarTareaClick = {
                    navController.navigate("nueva_tarea")
                }
            )
        }

        composable(route = "nueva_tarea") {
            SegudaPantalla(
                onBackClick = {
                    navController.popBackStack()
                },
                onTareaGuardada = {
                    navController.popBackStack()
                }
            )
        }
    }
}