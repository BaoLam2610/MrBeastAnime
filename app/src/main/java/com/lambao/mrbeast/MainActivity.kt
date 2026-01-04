package com.lambao.mrbeast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.lambao.core.ui.theme.MrBeastTheme
import com.lambao.mrbeast.navigation.MrBeastNavigation

/**
 * Main Activity - Entry point for the app
 * Uses Jetpack Compose for UI
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            MrBeastTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MrBeastNavigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}