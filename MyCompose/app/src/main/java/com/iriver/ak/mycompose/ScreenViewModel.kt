package com.iriver.ak.mycompose

import androidx.lifecycle.ViewModel
import com.iriver.ak.mycompose.data.MyScreenDepth
import com.iriver.ak.mycompose.data.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ScreenState(depth = MyScreenDepth.DEPTH1.name))
    val uiState : StateFlow<ScreenState> = _uiState.asStateFlow()

    fun setState(screenDepth: String) {
        _uiState.update { currentState ->
            currentState.copy(
                depth = screenDepth,
            )
        }
    }

    fun resetState() {
        _uiState.value = ScreenState(depth = MyScreenDepth.DEPTH1.name)
    }
}