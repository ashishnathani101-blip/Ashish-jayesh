package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppSection
import com.example.ui.theme.MedGoldScore
import com.example.ui.theme.MedNormalGreen

private data class NavItemData(
    val section: AppSection,
    val label: String,
    val icon: ImageVector,
    val testTag: String
)

@Composable
fun AppBottomNavigation(
    selectedSection: AppSection,
    onSectionSelected: (AppSection) -> Unit,
    dailyProgressText: String = "10",
    historyCount: Int = 0,
    modifier: Modifier = Modifier
) {
    val navItems = remember {
        listOf(
            NavItemData(AppSection.DIAGNOSIS, "Diagnosis", Icons.Default.MedicalServices, "nav_tab_diagnosis"),
            NavItemData(AppSection.DAILY_10, "Daily 10", Icons.Default.Stars, "nav_tab_daily10"),
            NavItemData(AppSection.HISTORY, "History", Icons.Default.History, "nav_tab_history"),
            NavItemData(AppSection.CATEGORIES, "Category", Icons.Default.Category, "nav_tab_category"),
            NavItemData(AppSection.PROFILE, "Profile", Icons.Default.Person, "nav_tab_profile")
        )
    }

    val selectedIndex = navItems.indexOfFirst { it.section == selectedSection }.coerceAtLeast(0)

    // Animated float value tracking selected index for continuous fluid flowing between icons
    val animatedFraction by animateFloatAsState(
        targetValue = selectedIndex.toFloat(),
        animationSpec = spring(
            dampingRatio = 0.76f,
            stiffness = 280f
        ),
        label = "nav_flow_fraction"
    )

    // Pulse transition for live profile clinician presence
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_transition")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1100, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
        shadowElevation = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .testTag("app_bottom_nav")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
            ) {
                val totalWidth = maxWidth
                val tabWidth = totalWidth / navItems.size
                val pillWidth = (tabWidth * 0.82f).coerceAtMost(64.dp)
                val pillHorizontalPadding = (tabWidth - pillWidth) / 2
                val currentPillOffset = (tabWidth * animatedFraction) + pillHorizontalPadding

                // 1. FLUID CONNECTOR LINE & GLOW RUNNER (Flows smoothly between icons)
                val primaryColor = MaterialTheme.colorScheme.primary
                val secondaryColor = MaterialTheme.colorScheme.tertiary
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .align(Alignment.TopStart)
                ) {
                    val activeCenterX = (tabWidth.toPx() * animatedFraction) + (tabWidth.toPx() / 2f)

                    // Subtle background track
                    drawLine(
                        color = Color.LightGray.copy(alpha = 0.3f),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 2f
                    )

                    // Flowing luminous gradient beam centered on active position
                    val beamWidth = tabWidth.toPx() * 1.3f
                    val beamStart = (activeCenterX - beamWidth / 2f).coerceAtLeast(0f)
                    val beamEnd = (activeCenterX + beamWidth / 2f).coerceAtMost(size.width)

                    drawRoundRect(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                primaryColor.copy(alpha = 0f),
                                primaryColor.copy(alpha = 0.85f),
                                secondaryColor.copy(alpha = 0.95f),
                                primaryColor.copy(alpha = 0.85f),
                                primaryColor.copy(alpha = 0f)
                            ),
                            startX = beamStart,
                            endX = beamEnd
                        ),
                        topLeft = Offset(beamStart, 0f),
                        size = androidx.compose.ui.geometry.Size(beamEnd - beamStart, 3.dp.toPx()),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f)
                    )

                    // Leading runner energy bead
                    drawCircle(
                        color = primaryColor,
                        radius = 3.dp.toPx(),
                        center = Offset(activeCenterX, 1.5.dp.toPx())
                    )
                }

                // 2. GLIDING FLUID PILL CAPSULE (Smoothly slides horizontally directly between tabs)
                Box(
                    modifier = Modifier
                        .offset(x = currentPillOffset, y = 6.dp)
                        .width(pillWidth)
                        .height(42.dp)
                        .clip(RoundedCornerShape(21.dp))
                        .background(
                            if (selectedSection == AppSection.DAILY_10) {
                                Brush.verticalGradient(
                                    listOf(
                                        MedGoldScore.copy(alpha = 0.22f),
                                        MedGoldScore.copy(alpha = 0.12f)
                                    )
                                )
                            } else {
                                Brush.verticalGradient(
                                    listOf(
                                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f),
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                    )
                                )
                            }
                        )
                )

                // 3. ROW OF 5 INTERACTIVE TABS
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    navItems.forEachIndexed { index, item ->
                        val isSelected = item.section == selectedSection

                        // Icon scale & vertical translation spring physics
                        val iconScale by animateFloatAsState(
                            targetValue = if (isSelected) 1.14f else 1.0f,
                            animationSpec = spring(
                                dampingRatio = 0.75f,
                                stiffness = Spring.StiffnessMediumLow
                            ),
                            label = "icon_scale_${item.section}"
                        )
                        val iconYOffset by animateDpAsState(
                            targetValue = if (isSelected) (-2).dp else 0.dp,
                            animationSpec = spring(dampingRatio = 0.75f, stiffness = Spring.StiffnessMediumLow),
                            label = "icon_offset_${item.section}"
                        )
                        val iconTint by animateColorAsState(
                            targetValue = when {
                                isSelected && item.section == AppSection.DAILY_10 -> MedGoldScore
                                isSelected -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.72f)
                            },
                            animationSpec = tween(durationMillis = 200),
                            label = "icon_tint_${item.section}"
                        )

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    onSectionSelected(item.section)
                                }
                                .testTag(item.testTag),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.offset(y = iconYOffset)
                            ) {
                                // ICON CONTAINER WITH BADGES & CUSTOM PROFILE PRESENCE
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(28.dp)
                                        .graphicsLayer {
                                            scaleX = iconScale
                                            scaleY = iconScale
                                        }
                                ) {
                                    when (item.section) {
                                        AppSection.DAILY_10 -> {
                                            BadgedBox(
                                                badge = {
                                                    Badge(
                                                        containerColor = MedGoldScore,
                                                        contentColor = Color.White
                                                    ) {
                                                        Text(
                                                            text = dailyProgressText,
                                                            fontSize = 8.sp,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = item.icon,
                                                    contentDescription = item.label,
                                                    tint = iconTint,
                                                    modifier = Modifier.size(22.dp)
                                                )
                                            }
                                        }

                                        AppSection.HISTORY -> {
                                            if (historyCount > 0) {
                                                BadgedBox(
                                                    badge = {
                                                        Badge(
                                                            containerColor = MaterialTheme.colorScheme.secondary,
                                                            contentColor = MaterialTheme.colorScheme.onSecondary
                                                        ) {
                                                            Text("$historyCount", fontSize = 8.sp)
                                                        }
                                                    }
                                                ) {
                                                    Icon(
                                                        imageVector = item.icon,
                                                        contentDescription = item.label,
                                                        tint = iconTint,
                                                        modifier = Modifier.size(22.dp)
                                                    )
                                                }
                                            } else {
                                                Icon(
                                                    imageVector = item.icon,
                                                    contentDescription = item.label,
                                                    tint = iconTint,
                                                    modifier = Modifier.size(22.dp)
                                                )
                                            }
                                        }

                                        AppSection.PROFILE -> {
                                            // CUSTOM PROFILE PRESENCE: Shows active clinician online presence ring & dot
                                            Box(contentAlignment = Alignment.Center) {
                                                // Outer pulse halo when in Profile tab
                                                if (isSelected) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(28.dp)
                                                            .clip(CircleShape)
                                                            .background(MedNormalGreen.copy(alpha = 0.18f * pulseAlpha))
                                                    )
                                                }

                                                Icon(
                                                    imageVector = item.icon,
                                                    contentDescription = item.label,
                                                    tint = iconTint,
                                                    modifier = Modifier.size(22.dp)
                                                )

                                                // Green presence status dot indicating clinician active
                                                Box(
                                                    modifier = Modifier
                                                        .size(8.dp)
                                                        .align(Alignment.BottomEnd)
                                                        .clip(CircleShape)
                                                        .background(if (isSelected) MedNormalGreen else MedNormalGreen.copy(alpha = 0.6f))
                                                )
                                            }
                                        }

                                        else -> {
                                            Icon(
                                                imageVector = item.icon,
                                                contentDescription = item.label,
                                                tint = iconTint,
                                                modifier = Modifier.size(22.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(2.dp))

                                // LABEL WITH CUSTOM ACTIVE INDICATION
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = item.label,
                                        fontSize = 10.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) {
                                            if (item.section == AppSection.DAILY_10) Color(0xFFB45309)
                                            else MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                        }
                                    )

                                    // Special micro-tag for active Profile tab
                                    if (item.section == AppSection.PROFILE && isSelected) {
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Box(
                                            modifier = Modifier
                                                .size(4.dp)
                                                .clip(CircleShape)
                                                .background(MedNormalGreen)
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
