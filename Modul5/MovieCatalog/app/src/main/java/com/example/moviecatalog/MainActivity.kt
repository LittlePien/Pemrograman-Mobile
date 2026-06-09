package com.example.moviecatalog

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.moviecatalog.navigation.AppNavGraph
import com.example.moviecatalog.ui.theme.MovieCatalogTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val langPref = (application as MovieCatalogApplication).languagePreference
        val localeTag = if (langPref.isIndonesian()) "id" else "en"
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(localeTag))

        enableEdgeToEdge()
        setContent {
            MovieCatalogTheme {
                AppNavGraph()
            }
        }
    }
}