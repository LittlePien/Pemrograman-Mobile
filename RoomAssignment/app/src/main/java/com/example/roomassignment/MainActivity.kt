package com.example.roomassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.roomassignment.navigation.AppNavGraph
import com.example.roomassignment.ui.theme.RoomAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomAssignmentTheme {
                AppNavGraph()
            }
        }
    }
}