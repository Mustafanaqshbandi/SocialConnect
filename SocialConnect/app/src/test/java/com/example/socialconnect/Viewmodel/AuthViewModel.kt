package com.example.socialconnect.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val displayName: String = "",
    val loading: Boolean = false,
    val error: String? = null
)

class AuthViewModel(
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun onEmailChange(v: String) = _uiState.update { it.copy(email = v) }
    fun onPasswordChange(v: String) = _uiState.update { it.copy(password = v) }
    fun onDisplayNameChange(v: String) = _uiState.update { it.copy(displayName = v) }

    fun login(onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }
            val res = repo.login(_uiState.value.email, _uiState.value.password)
            _uiState.update { it.copy(loading = false) }

            res.onSuccess { onResult(true, null) }
            res.onFailure { onResult(false, it.message) }
        }
    }

    fun signUp(onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }
            val res = repo.signUp(
                _uiState.value.email,
                _uiState.value.password,
                _uiState.value.displayName
            )
            _uiState.update { it.copy(loading = false) }

            res.onSuccess { onResult(true, null) }
            res.onFailure { onResult(false, it.message) }
        }
    }
}
