@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package pl.fewbits.radioexample.android.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TopBar(title: String, onBackAction: (() -> Unit)? = null, modifier: Modifier = Modifier) = TopAppBar(
    title = {
        Text(title, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    },
    navigationIcon = {
        if (onBackAction != null) {
            IconButton(onClick = { onBackAction() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
            }
        }
    }
)
