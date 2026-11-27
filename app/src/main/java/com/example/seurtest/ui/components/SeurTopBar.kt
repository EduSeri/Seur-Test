package com.example.seurtest.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.seurtest.R
import com.example.seurtest.ui.theme.SeurTestTheme

@Composable
fun SeurTopBar(
    onMenuClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo PNG de SEUR en blanco
        Image(
            painter = painterResource(R.drawable.logo_seur_cabecera_blanco),
            contentDescription = stringResource(R.string.logo_seur_description),
            contentScale = ContentScale.Fit,
            modifier = Modifier.height(28.dp)
        )

        // Botones de la derecha
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Non-functional */ }, modifier = Modifier.size(36.dp)) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.content_desc_search),
                    tint = Color.White
                )
            }
            IconButton(onClick = { /* Non-functional */ }, modifier = Modifier.size(36.dp)) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = stringResource(R.string.content_desc_notifications),
                    tint = Color.White
                )
            }
            IconButton(onClick = { /* Non-functional */ }, modifier = Modifier.size(36.dp)) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(R.string.content_desc_profile),
                    tint = Color.White
                )
            }
            IconButton(onClick = onMenuClick, modifier = Modifier.size(36.dp)) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.content_desc_menu),
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SeurTopBarPreview() {
    SeurTestTheme {
        SeurTopBar()
    }
}
