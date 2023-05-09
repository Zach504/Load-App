package com.udacity.loadapp

sealed class ButtonState {
    object Idle : ButtonState()
    object Loading : ButtonState()
}
