package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CaseTab
import com.example.ui.theme.MedGoldScore
import com.example.ui.theme.MedGoldScoreContainer

@Composable
fun HorizontalCaseTabs(
    tabs: List<CaseTab>,
    highestTabExplored: Int,
    selectedTabIndex: Int,
    potentialScore: Int,
    hintsUsedTabIds: Set<Int> = emptySet(),
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    // Smooth auto-slide transition: when moving between tabs (e.g. from tab 4 to tab 5), auto slide to it!
    LaunchedEffect(selectedTabIndex) {
        if (tabs.isNotEmpty()) {
            val targetIndex = selectedTabIndex.coerceIn(0, tabs.size - 1)
            lazyListState.animateScrollToItem(
                index = targetIndex,
                scrollOffset = -100
            )
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "CLINICAL CASE TABS",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                    ) {
                        Text(
                            text = "7 Clues",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    // Interactive quick transition button: auto-slide to next tab
                    if (selectedTabIndex < tabs.size - 1) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                            modifier = Modifier
                                .clickable { onTabSelected(selectedTabIndex + 1) }
                                .testTag("slide_next_tab_button")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Tab ${selectedTabIndex + 2}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    ),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "Slide to next tab",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(10.dp)
                                )
                            }
                        }
                    }
                }

                // HIGH-VISIBILITY HIGHLIGHTED POTENTIAL POINTS PILL
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFEF3C7),
                    border = BorderStroke(1.5.dp, Color(0xFFF59E0B)),
                    shadowElevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Stars,
                            contentDescription = "Points Reward",
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$potentialScore pts max",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.3.sp
                            ),
                            color = Color(0xFF78350F)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Auto-sliding 7 Horizontal Tab Row
            LazyRow(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(tabs, key = { _, tab -> tab.id }) { index, tab ->
                    val isExplored = index < highestTabExplored
                    val isSelected = index == selectedTabIndex
                    val hasHintUsed = hintsUsedTabIds.contains(tab.id)

                    CaseTabPill(
                        tabNumber = tab.id,
                        tabName = tab.shortTitle,
                        isExplored = isExplored,
                        isSelected = isSelected,
                        hasHintUsed = hasHintUsed,
                        onClick = { onTabSelected(index) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CaseTabPill(
    tabNumber: Int,
    tabName: String,
    isExplored: Boolean,
    isSelected: Boolean,
    hasHintUsed: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primary
        isExplored -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
    }

    val contentColor = when {
        isSelected -> MaterialTheme.colorScheme.onPrimary
        isExplored -> MaterialTheme.colorScheme.onPrimaryContainer
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    val scaleAnim by animateFloatAsState(
        targetValue = if (isSelected) 1.04f else 1.0f,
        animationSpec = spring(dampingRatio = 0.7f, stiffness = Spring.StiffnessMediumLow),
        label = "tab_scale"
    )

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        border = if (isSelected) {
            BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
        } else if (!isExplored) {
            BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        } else null,
        shadowElevation = if (isSelected) 3.dp else 0.dp,
        modifier = Modifier
            .testTag("tab_pill_$tabNumber")
            .graphicsLayer {
                scaleX = scaleAnim
                scaleY = scaleAnim
            }
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circle with number or check icon
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
                        else if (isExplored) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        else MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isExplored && !isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Explored",
                        tint = contentColor,
                        modifier = Modifier.size(12.dp)
                    )
                } else {
                    Text(
                        text = "$tabNumber",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = contentColor
                    )
                }
            }

            Spacer(modifier = Modifier.width(6.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = tabName,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        ),
                        color = contentColor
                    )
                    if (hasHintUsed) {
                        Spacer(modifier = Modifier.width(3.dp))
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "Hint Used",
                            tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MedGoldScore,
                            modifier = Modifier.size(11.dp)
                        )
                    }
                }
                if (!isExplored && !isSelected) {
                    Text(
                        text = "-150 pts if read",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                        color = MaterialTheme.colorScheme.error.copy(alpha = 0.85f)
                    )
                }
            }

            if (isSelected) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Visibility,
                    contentDescription = "Currently Viewing",
                    tint = contentColor,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}
