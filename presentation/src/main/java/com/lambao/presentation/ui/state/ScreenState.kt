package com.lambao.presentation.ui.state

/**
 * Sealed class representing the different states of a screen.
 * 
 * This class provides a type-safe way to handle different screen states
 * in the presentation layer, allowing for proper state management.
 */
sealed class ScreenState {
    /**
     * Screen is in idle state (initial state).
     */
    class Idle : ScreenState()
    
    /**
     * Screen is loading data.
     */
    class Loading : ScreenState()
    
    /**
     * Screen has successfully loaded data.
     */
    class Success : ScreenState()
    
    /**
     * Screen encountered an error.
     * 
     * @param throwable The error that occurred
     */
    data class Error(val throwable: Throwable) : ScreenState()
}
