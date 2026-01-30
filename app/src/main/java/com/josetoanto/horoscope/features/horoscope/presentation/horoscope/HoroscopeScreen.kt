package com.josetoanto.horoscope.features.horoscope.presentation.horoscope

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class) // Necesario para TopAppBar
@Composable
fun HoroscopeScreen(factory: HoroscopeViewModelFactory) {
    val viewModel: HoroscopeViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var selectedSign by remember { mutableStateOf("aries") }
    val signs = listOf(
        "aries","taurus","gemini","cancer","leo","virgo",
        "libra","scorpio","sagittarius","capricorn","aquarius","pisces"
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Horoscope") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(text = "Selecciona tu signo")
            Spacer(modifier = Modifier.height(8.dp))

            // FlowRow corregido con los nombres de parámetros actuales
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                signs.forEach { sign ->
                    FilterChip(
                        selected = selectedSign == sign,
                        onClick = { selectedSign = sign },
                        label = { Text(sign.replaceFirstChar { it.uppercase() }) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { viewModel.loadHoroscope(selectedSign) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Buscar")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Lógica de UI con Smart Cast corregido
            val currentHoroscope = uiState.horoscope // Variable local para Smart Cast

            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                uiState.error != null -> {
                    Text("Error: ${uiState.error}", color = MaterialTheme.colorScheme.error)
                }
                currentHoroscope != null -> {
                    Text("Signo: ${currentHoroscope.sign}", style = MaterialTheme.typography.titleLarge)
                    Text("Fecha: ${currentHoroscope.date}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(currentHoroscope.text, style = MaterialTheme.typography.bodyLarge)
                }
                else -> {
                    Text("Pulsa Buscar para ver tu horóscopo")
                }
            }
        }
    }
}