package com.josetoanto.horoscope.features.horoscope.presentation.horoscope.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.josetoanto.horoscope.features.horoscope.presentation.horoscope.viewmodel.HoroscopeViewModel
import com.josetoanto.horoscope.features.horoscope.presentation.horoscope.viewmodel.HoroscopeViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HoroscopeScreen(factory: HoroscopeViewModelFactory) {

    val viewModel: HoroscopeViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var selectedSign by remember { mutableStateOf("aries") }

    val signs = listOf(
        "aries","taurus","gemini","cancer","leo","virgo",
        "libra","scorpio","sagittarius","capricorn","aquarius","pisces"
    )

    // 🌌 Fondo Galaxia
    val galaxyBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0B0033),
            Color(0xFF1B004F),
            Color(0xFF050016)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Horóscopo ✨",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(galaxyBackground)
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = "Elige tu constelación",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFB388FF)
            )

            Spacer(modifier = Modifier.height(12.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                signs.forEach { sign ->
                    FilterChip(
                        selected = selectedSign == sign,
                        onClick = { selectedSign = sign },
                        label = {
                            Text(
                                sign.replaceFirstChar { it.uppercase() },
                                color = if (selectedSign == sign)
                                    Color.Black else Color.White
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFBB86FC),
                            containerColor = Color(0x332196F3)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { viewModel.loadHoroscopeAndTranslate(selectedSign) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7C4DFF)
                )
            ) {
                Text("Consultar el cosmos 🔮")
            }

            Spacer(modifier = Modifier.height(24.dp))

            val currentHoroscope = uiState.horoscope

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFFB388FF))
                    }
                }

                uiState.error != null -> {
                    Text(
                        text = "⚠ Error: ${uiState.error}",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                currentHoroscope != null -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0x331E88E5)
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(
                                text = "♈ ${currentHoroscope.sign}",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color(0xFFE1BEE7)
                            )

                            Text(
                                text = currentHoroscope.date,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFB39DDB)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = currentHoroscope.text,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White
                            )
                        }
                    }
                }

                else -> {
                    Text(
                        text = "✨ El universo tiene un mensaje para ti",
                        color = Color(0xFF9FA8DA)
                    )
                }
            }
        }
    }
}

