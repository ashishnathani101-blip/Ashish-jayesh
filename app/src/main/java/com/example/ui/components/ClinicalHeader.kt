package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CaseStatus
import com.example.model.MedicalCase

@Composable
fun ClinicalHeader(
    currentCase: MedicalCase,
    attemptsCount: Int,
    maxAttempts: Int,
    highestTabExplored: Int,
    potentialScore: Int,
    totalScore: Int,
    currentStreak: Int,
    caseStatus: CaseStatus,
    onNewCaseClicked: () -> Unit,
    onExitFullScreen: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectVerticalDragGestures { _, dragAmount ->
                    // Swiping down exits full screen diagnosis
                    if (dragAmount > 18f) {
                        onExitFullScreen?.invoke()
                    }
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            // TOP MINIMAL SWIPE HANDLE BAR (Exit Gesture indicator)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Exit button
                if (onExitFullScreen != null) {
                    IconButton(
                        onClick = onExitFullScreen,
                        modifier = Modifier
                            .size(28.dp)
                            .testTag("exit_fullscreen_diagnosis_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Exit Fullscreen Diagnosis",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(28.dp))
                }

                // Centered Swipe-Down Handle Pill
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f))
                        .clickable { onExitFullScreen?.invoke() }
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(3.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Swipe down to exit",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Streak tag or spacer
                if (currentStreak > 0) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.TrendingUp,
                                contentDescription = "Streak",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "$currentStreak streak",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.size(28.dp))
                }
            }

            // UPPER ROW: CASE NUMBER & HEADING MOVED TO UPPER SECTION + SCORE & NEW CASE BUTTON
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // UPPER LEFT: CASE NUMBER BADGE & SPECIALTY
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.testTag("case_number_badge")
                    ) {
                        Text(
                            text = "CASE #${currentCase.caseNumber}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 0.4.sp
                            ),
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                    ) {
                        Text(
                            text = currentCase.specialty.uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            ),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // UPPER RIGHT: SCORE BADGE & NEW CASE BUTTON (GUARANTEED VISIBILITY)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Highlighted gold score pill with high contrast
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFEF3C7),
                        border = BorderStroke(1.2.dp, Color(0xFFF59E0B)),
                        shadowElevation = 1.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Stars,
                                contentDescription = "Score",
                                tint = Color(0xFFD97706),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$totalScore pts",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 11.sp
                                ),
                                color = Color(0xFF78350F)
                            )
                        }
                    }

                    // NEW CASE BUTTON: Explicit width and padding so "New Case" text is NEVER truncated
                    FilledTonalButton(
                        onClick = onNewCaseClicked,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                        modifier = Modifier
                            .testTag("new_case_button")
                            .height(32.dp)
                            .widthIn(min = 96.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "New Case",
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "New Case",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1,
                            softWrap = false
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // PATIENT PROFILE & CHIEF COMPLAINT HEADLINE (Moved upper, compact)
            Text(
                text = "${currentCase.patientProfile} • ${currentCase.chiefComplaintHeadline}",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                ),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Step Indicator Dots for the 7 Tabs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 1..7) {
                    val isExplored = i <= highestTabExplored
                    val dotColor = when {
                        caseStatus == CaseStatus.SOLVED -> MaterialTheme.colorScheme.primary
                        isExplored -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(3.dp)
                            .clip(CircleShape)
                            .background(dotColor)
                    )
                }
            }
        }
    }
}
