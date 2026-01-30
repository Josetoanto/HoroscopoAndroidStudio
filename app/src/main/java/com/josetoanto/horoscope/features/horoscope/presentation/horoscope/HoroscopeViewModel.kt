package com.josetoanto.horoscope.features.horoscope.presentation.horoscope

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josetoanto.horoscope.features.horoscope.domain.usecase.GetHoroscopeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HoroscopeViewModel(
    private val getHoroscopeUseCase: GetHoroscopeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HoroscopeUiState())
    val uiState = _uiState.asStateFlow()

    fun loadHoroscope(sign: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            val result = getHoroscopeUseCase(sign)
            result.fold(
                onSuccess = { h ->
                    _uiState.value = HoroscopeUiState(isLoading = false, horoscope = h)
                },
                onFailure = { e ->
                    _uiState.value = HoroscopeUiState(isLoading = false, error = e.message ?: "Error")
                }
            )
        }
    }
}