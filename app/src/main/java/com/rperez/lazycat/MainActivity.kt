package com.rperez.lazycat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rperez.lazycat.ui.composables.CatGrid
import com.rperez.lazycat.ui.theme.LazyCatTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LazyCatTheme {
                CatGrid()
            }
        }
    }
}
