package com.example.seurtest.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seurtest.R
import com.example.seurtest.ui.navigation.Screen

data class SubMenuLink(
    val titleRes: Int,
    val screen: Screen? = null
)

data class MenuSection(
    val id: String,
    val titleRes: Int,
    val icon: ImageVector,
    val screen: Screen? = null,
    val children: List<SubMenuLink> = emptyList()
)

@Composable
fun SeurSideMenu(
    visible: Boolean,
    currentRoute: Any?,
    onClose: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onLogout: () -> Unit,
    onComingSoon: (String) -> Unit
) {
    val userName = "Eduardo Seri" // TODO: Obtener del usuario autenticado real

    BackHandler(enabled = visible) {
        onClose()
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(300)) + slideInHorizontally(animationSpec = tween(300)) { it },
        exit = fadeOut(animationSpec = tween(300)) + slideOutHorizontally(animationSpec = tween(300)) { it }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.55f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClose()
                }
        ) {
            Surface(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .widthIn(max = 324.dp)
                    .fillMaxWidth(0.85f)
                    .clickable(enabled = false) {},
                shape = RoundedCornerShape(topStart = 28.dp, bottomStart = 28.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                var expandedSection by rememberSaveable { mutableStateOf("miseur") }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding()
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Header
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(MaterialTheme.colorScheme.secondary, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    val initials = userName.split(" ")
                                        .mapNotNull { it.firstOrNull()?.toString() }
                                        .take(2)
                                        .joinToString("")
                                        .uppercase()
                                    Text(
                                        text = initials.ifEmpty { "U" },
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                }
                                Column {
                                    Text(
                                        text = userName,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    val profileText = stringResource(R.string.menu_profile)
                                    TextButton(
                                        onClick = {
                                            onClose()
                                            onComingSoon(profileText)
                                        },
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier.height(24.dp)
                                    ) {
                                        Text(
                                            text = profileText,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontSize = 13.sp,
                                            textDecoration = TextDecoration.Underline
                                        )
                                    }
                                }
                            }

                            IconButton(
                                onClick = onClose,
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                                    .semantics { role = Role.Button }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = stringResource(R.string.menu_close),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    }

                    // Menu List
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val sections = listOf(
                            MenuSection("envios", R.string.menu_envios, Icons.Default.LocalShipping),
                            MenuSection("recibir", R.string.menu_recibir, Icons.Default.MoveToInbox),
                            MenuSection("devolver", R.string.menu_devolver, Icons.Default.Replay),
                            MenuSection("miseur", R.string.menu_miseur, Icons.Default.Person, children = listOf(
                                SubMenuLink(R.string.menu_inicio, Screen.Home),
                                SubMenuLink(R.string.menu_mis_envios, Screen.MisEnvios),
                                SubMenuLink(R.string.menu_enviar_ahora),
                                SubMenuLink(R.string.menu_puntos_seur_pickup),
                                SubMenuLink(R.string.menu_ayuda),
                                SubMenuLink(R.string.menu_datos_personales),
                                SubMenuLink(R.string.menu_notificaciones)
                            )),
                            MenuSection("empresas", R.string.menu_empresas, Icons.Default.Business),
                            MenuSection("pickup", R.string.menu_puntos_pickup, Icons.Default.Place)
                        )

                        sections.forEach { section ->
                            val isExpanded = expandedSection == section.id
                            val hasChildren = section.children.isNotEmpty()
                            val chevronRotation by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f, label = "chevron")
                            val titleText = stringResource(section.titleRes)

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(if (isExpanded) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent)
                                    .clickable(
                                        role = Role.Button,
                                        onClick = {
                                            if (hasChildren) {
                                                expandedSection = if (isExpanded) "" else section.id
                                            } else {
                                                onClose()
                                                if (section.screen != null) {
                                                    onNavigate(section.screen)
                                                } else {
                                                    onComingSoon(titleText)
                                                }
                                            }
                                        }
                                    )
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                                    .semantics {
                                        if (hasChildren) {
                                            stateDescription = if (isExpanded) "Expandido" else "Contraído"
                                        }
                                    }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(36.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .background(
                                                    if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                                                    RoundedCornerShape(10.dp)
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = section.icon,
                                                contentDescription = null,
                                                tint = if (isExpanded) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                        Text(
                                            text = titleText,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }

                                    if (hasChildren) {
                                        Icon(
                                            imageVector = Icons.Default.ExpandMore,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.graphicsLayer { rotationZ = chevronRotation }
                                        )
                                    }
                                }

                                if (hasChildren) {
                                    AnimatedVisibility(
                                        visible = isExpanded,
                                        enter = expandVertically(),
                                        exit = shrinkVertically()
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 24.dp, top = 4.dp, bottom = 4.dp),
                                            verticalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .width(2.dp)
                                                        .fillMaxHeight()
                                                        .background(MaterialTheme.colorScheme.outlineVariant)
                                                )
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(start = 12.dp),
                                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                                ) {
                                                    section.children.forEach { child ->
                                                        val isCurrentScreen = when (child.screen) {
                                                            Screen.Home -> currentRoute == Screen.Home
                                                            Screen.MisEnvios -> currentRoute == Screen.MisEnvios
                                                            else -> false
                                                        }
                                                        val childTitle = stringResource(child.titleRes)

                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .height(42.dp)
                                                                .clip(RoundedCornerShape(8.dp))
                                                                .background(
                                                                    if (isCurrentScreen) MaterialTheme.colorScheme.secondaryContainer
                                                                    else Color.Transparent
                                                                )
                                                                .clickable(role = Role.Button) {
                                                                    onClose()
                                                                    if (child.screen != null) {
                                                                        onNavigate(child.screen)
                                                                    } else {
                                                                        onComingSoon(childTitle)
                                                                    }
                                                                }
                                                                .padding(horizontal = 8.dp),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Text(
                                                                text = childTitle,
                                                                fontSize = 15.sp,
                                                                fontWeight = if (isCurrentScreen) FontWeight.Bold else FontWeight.Normal,
                                                                color = if (isCurrentScreen) MaterialTheme.colorScheme.onSecondaryContainer
                                                                else MaterialTheme.colorScheme.onSurface
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Footer
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.padding(bottom = 16.dp),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )

                        OutlinedButton(
                            onClick = {
                                onClose()
                                onLogout()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(26.dp),
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Logout,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                                Text(
                                    text = stringResource(R.string.logout),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
