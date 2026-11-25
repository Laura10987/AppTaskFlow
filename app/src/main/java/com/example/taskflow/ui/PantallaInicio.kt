package com.example.taskflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskflow.R
import com.example.taskflow.ui.theme.TaskFlowTheme

@Composable
fun PantallaInicio(
    onContinuarClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Tarjeta redondeada con el logo de TaskFlow
            Surface(
                modifier = Modifier.size(120.dp),
                shape = RoundedCornerShape(28.dp),
                color = MaterialTheme.colorScheme.primary,
                shadowElevation = 8.dp
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_taskflow),
                        contentDescription = stringResource(id = R.string.desc_logo_taskflow),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Nombre de la aplicación desde strings.xml
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtítulo descriptivo desde strings.xml
            Text(
                text = stringResource(id = R.string.subtitulo_inicio),
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(56.dp))

            // Botón principal usando colores de MaterialTheme
            Button(
                onClick = onContinuarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary


                )

            ) {
                Text(
                    text = stringResource(id = R.string.btn_ver_mis_tareas),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = stringResource(id = R.string.subtitulo_abajo),
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaInicioPreview() {
    TaskFlowTheme {
        PantallaInicio()
    }
}
