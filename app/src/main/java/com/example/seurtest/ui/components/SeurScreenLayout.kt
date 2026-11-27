package com.example.seurtest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun SeurScreenLayout(
    headerContent: (@Composable ColumnScope.() -> Unit)? = null,
    cardContent: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        topBar = {
            SeurTopBar()
        },
        containerColor = MaterialTheme.colorScheme.surface // Root background matches surface color so bottom navigation bar area is never orange
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.secondary) // Orange background for top area behind header / top bar
        ) {
            if (headerContent != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                ) {
                    headerContent()
                }
                Spacer(modifier = Modifier.height(24.dp))
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Surface card filling remaining height (weight(1f)) so it always reaches the bottom edge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .imePadding()
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    cardContent()

                    // System navigation bar / gesture bar bottom inset spacer
                    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                }
            }
        }
    }
}
