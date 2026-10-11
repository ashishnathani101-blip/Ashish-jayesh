package com.example.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Daily10CaseStatus
import com.example.model.DailyCheckInDay
import com.example.ui.theme.MedGoldScore
import com.example.ui.theme.MedGoldScoreContainer
import com.example.ui.theme.MedNormalGreen

@Composable
fun Daily10Screen(
    dailyCases: List<Daily10CaseStatus>,
    solvedCount: Int,
    isJackpotClaimed: Boolean,
    checkInDays: List<DailyCheckInDay>,
    currentStreakDay: Int,
    hintTokensCount: Int,
    lastClaimedReward: DailyCheckInDay?,
    onClaimCheckIn: () -> Unit,
    onDismissClaimedDialog: () -> Unit,
    onClaimJackpot: () -> Unit,
    onLaunchCase: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val progressFloat = (solvedCount.toFloat() / 10f).coerceIn(0f, 1f)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .testTag("daily_10_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // TOP HEADER WITH HINT TOKEN INVENTORY
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Daily 10 Challenge",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "10 cases every 24h • Claim daily streak rewards",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // INVENTORY BADGE: HINT TOKENS
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MedGoldScoreContainer,
                    border = BorderStroke(1.dp, MedGoldScore.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "Hint Tokens",
                            tint = MedGoldScore,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "$hintTokensCount Tokens",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Color(0xFF92400E)
                        )
                    }
                }
            }
        }

        // 8-DAY DAILY CHECK-IN SECTION
        item {
            CheckInStreakCard(
                checkInDays = checkInDays,
                currentStreakDay = currentStreakDay,
                onClaimCheckIn = onClaimCheckIn
            )
        }

        // DAILY 10 PROGRESS & JACKPOT BANNER
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalHospital,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Daily Rounds Progress",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = null,
                                    modifier = Modifier.size(12.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Resets in 14h",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "$solvedCount of 10 Cases Solved",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (solvedCount == 10) MedNormalGreen else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${(progressFloat * 100).toInt()}%",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = { progressFloat },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // JACKPOT REWARD CALLOUT
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (solvedCount == 10) MedGoldScoreContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        border = BorderStroke(
                            1.dp,
                            if (solvedCount == 10) MedGoldScore else MaterialTheme.colorScheme.outlineVariant
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.EmojiEvents,
                                    contentDescription = null,
                                    tint = MedGoldScore,
                                    modifier = Modifier.size(20.dp)
                                )
                                Column {
                                    Text(
                                        text = "10/10 Rounds Jackpot",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = if (solvedCount == 10) Color(0xFF92400E) else MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "+500 Bonus XP • +5 💡 Hint Tokens",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            if (solvedCount == 10) {
                                Button(
                                    onClick = onClaimJackpot,
                                    enabled = !isJackpotClaimed,
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MedGoldScore)
                                ) {
                                    Text(
                                        text = if (isJackpotClaimed) "Claimed" else "Claim!",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // CASES LIST HEADER
        item {
            Text(
                text = "TODAY'S 10 CLINICAL PATIENTS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        // 10 CASES LIST
        items(dailyCases, key = { it.caseId }) { dailyCase ->
            DailyCaseCard(
                caseStatus = dailyCase,
                onLaunch = { onLaunchCase(dailyCase.caseNumber - 1) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // CELEBRATION REWARD DIALOG
    lastClaimedReward?.let { reward ->
        AlertDialog(
            onDismissRequest = onDismissClaimedDialog,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = if (reward.isGrandJackpot) Icons.Default.Star else Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = MedGoldScore,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = if (reward.isGrandJackpot) "Day 8 Grand Jackpot!" else "Day ${reward.dayNumber} Reward Claimed!",
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Congratulations! Your continuous diagnostic practice has earned you clinical rewards:",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MedGoldScoreContainer,
                        border = BorderStroke(1.dp, MedGoldScore),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "+${reward.xpReward} Clinical XP",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E)
                            )
                            Text(
                                text = "+${reward.hintTokensReward} Consumable Hint Tokens 💡",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF92400E)
                            )
                            if (reward.specialRewardTitle != null) {
                                Text(
                                    text = "🏆 Unlocked: ${reward.specialRewardTitle}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    Text(
                        text = "Hint tokens allow you to view clinical hints during diagnosis without losing potential case points!",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = onDismissClaimedDialog,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Collect & Continue")
                }
            }
        )
    }
}

@Composable
private fun CheckInStreakCard(
    checkInDays: List<DailyCheckInDay>,
    currentStreakDay: Int,
    onClaimCheckIn: () -> Unit
) {
    val currentDayConfig = checkInDays.find { it.dayNumber == currentStreakDay }
    val isReadyToClaim = currentDayConfig?.isAvailable == true
    val lazyListState = rememberLazyListState()

    // Smooth auto-slide transition: When a day (e.g. Day 4) is claimed, automatically slide to the next day (Day 5)!
    LaunchedEffect(currentStreakDay) {
        val targetIndex = (currentStreakDay - 1).coerceIn(0, (checkInDays.size - 1).coerceAtLeast(0))
        lazyListState.animateScrollToItem(
            index = targetIndex,
            scrollOffset = -80
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // HEADER & CLAIM BUTTON
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = MedGoldScore,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "8-Day Check-In Streak",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = if (isReadyToClaim) "Claim Day $currentStreakDay & slide to next reward!" else "Day $currentStreakDay claimed • Next unlocks daily",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = onClaimCheckIn,
                    enabled = isReadyToClaim,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentStreakDay == 8) MedGoldScore else MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.testTag("claim_daily_checkin_button")
                ) {
                    Text(
                        text = if (isReadyToClaim) "Claim Day $currentStreakDay" else "Day $currentStreakDay Claimed ✓",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // AUTO-SLIDING 8-DAY HORIZONTAL TRACK WITH SMOOTH TRANSITION
            LazyRow(
                state = lazyListState,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(checkInDays, key = { it.dayNumber }) { day ->
                    CheckInDayPill(
                        day = day,
                        isCurrent = day.dayNumber == currentStreakDay,
                        onPillClick = {
                            if (day.dayNumber == currentStreakDay && isReadyToClaim) {
                                onClaimCheckIn()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CheckInDayPill(
    day: DailyCheckInDay,
    isCurrent: Boolean,
    onPillClick: () -> Unit = {}
) {
    val isGrand = day.isGrandJackpot
    val isClaimed = day.isClaimed

    // Animated scale and border elevation for active/transitioning day
    val targetScale = if (isCurrent) 1.04f else 1.0f
    val scaleAnim by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = spring(dampingRatio = 0.7f, stiffness = Spring.StiffnessMediumLow),
        label = "pill_scale"
    )

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = when {
            isClaimed -> MedNormalGreen.copy(alpha = 0.12f)
            isGrand -> MedGoldScoreContainer
            isCurrent -> MaterialTheme.colorScheme.primaryContainer
            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        },
        border = BorderStroke(
            if (isCurrent || isGrand) 1.5.dp else 1.dp,
            when {
                isClaimed -> MedNormalGreen
                isGrand -> MedGoldScore
                isCurrent -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.outlineVariant
            }
        ),
        shadowElevation = if (isCurrent) 2.dp else 0.dp,
        modifier = Modifier
            .width(if (isGrand) 95.dp else 74.dp)
            .clickable(onClick = onPillClick)
            .testTag("checkin_pill_day_${day.dayNumber}")
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = if (isGrand) "DAY 8 🏆" else "Day ${day.dayNumber}",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = when {
                    isClaimed -> MedNormalGreen
                    isGrand -> Color(0xFF92400E)
                    isCurrent -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                }
            )

            // ICON
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            isClaimed -> MedNormalGreen
                            isGrand -> MedGoldScore
                            else -> MaterialTheme.colorScheme.surface
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isClaimed) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Claimed",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                } else if (isGrand) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Jackpot",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = "Token",
                        tint = if (isCurrent) MedGoldScore else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }

            Text(
                text = "+${day.hintTokensReward} 💡",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (isGrand) Color(0xFF92400E) else MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "+${day.xpReward} XP",
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (isCurrent && day.isAvailable) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = "READY",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DailyCaseCard(
    caseStatus: Daily10CaseStatus,
    onLaunch: () -> Unit
) {
    val isSolved = caseStatus.isSolved

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLaunch() }
            .testTag("daily_case_card_${caseStatus.caseNumber}"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            1.dp,
            if (isSolved) MedNormalGreen.copy(alpha = 0.35f) else MaterialTheme.colorScheme.outlineVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                // NUMBER BADGE
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSolved) MedNormalGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSolved) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Solved",
                            tint = MedNormalGreen,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text(
                            text = "${caseStatus.caseNumber}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = if (isSolved) caseStatus.title else "Case #${caseStatus.caseNumber}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = caseStatus.difficulty,
                                fontSize = 9.sp,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Text(
                        text = "${caseStatus.patientProfile} • ${caseStatus.specialty}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }

            // ACTION / STATUS
            if (isSolved) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MedNormalGreen.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = "SOLVED",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedNormalGreen,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            } else {
                Button(
                    onClick = onLaunch,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier.height(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Solve", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
