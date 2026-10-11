package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CaseHistoryRecord
import com.example.model.CaseStatus
import com.example.ui.theme.MedGoldScore
import com.example.ui.theme.MedGoldScoreContainer
import com.example.ui.theme.MedNormalGreen

enum class HistoryFilter {
    ALL,
    SOLVED,
    REVEALED
}

@Composable
fun CaseHistoryScreen(
    caseHistory: List<CaseHistoryRecord>,
    onReopenCase: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf(HistoryFilter.ALL) }
    var viewingRecord by remember { mutableStateOf<CaseHistoryRecord?>(null) }

    val filteredRecords = remember(caseHistory, selectedFilter) {
        when (selectedFilter) {
            HistoryFilter.ALL -> caseHistory
            HistoryFilter.SOLVED -> caseHistory.filter { it.status == CaseStatus.SOLVED }
            HistoryFilter.REVEALED -> caseHistory.filter { it.wasRevealed || it.status == CaseStatus.EXHAUSTED }
        }
    }

    val totalCases = caseHistory.size
    val solvedCases = caseHistory.count { it.status == CaseStatus.SOLVED }
    val winRate = if (totalCases > 0) (solvedCases * 100) / totalCases else 0
    val totalScoreEarned = caseHistory.sumOf { it.scoreEarned }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .testTag("case_history_screen")
    ) {
        // TOP HEADER
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Case History & Archive",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Review solved diagnoses and clinical post-mortems",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

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
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "Total Score",
                        tint = MedGoldScore,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "$totalScoreEarned pts",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color(0xFF92400E)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // STATS BAR
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatItem(label = "Encounters", value = "$totalCases")
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp)
                        .background(MaterialTheme.colorScheme.outlineVariant)
                )
                StatItem(label = "Solved", value = "$solvedCases", valueColor = MedNormalGreen)
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp)
                        .background(MaterialTheme.colorScheme.outlineVariant)
                )
                StatItem(label = "Accuracy", value = "$winRate%")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // FILTER CHIPS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedFilter == HistoryFilter.ALL,
                onClick = { selectedFilter = HistoryFilter.ALL },
                label = { Text("All Cases ($totalCases)", fontSize = 12.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.testTag("filter_all_cases")
            )
            FilterChip(
                selected = selectedFilter == HistoryFilter.SOLVED,
                onClick = { selectedFilter = HistoryFilter.SOLVED },
                label = { Text("Solved ($solvedCases)", fontSize = 12.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MedNormalGreen.copy(alpha = 0.15f),
                    selectedLabelColor = MedNormalGreen
                ),
                modifier = Modifier.testTag("filter_solved_cases")
            )
            FilterChip(
                selected = selectedFilter == HistoryFilter.REVEALED,
                onClick = { selectedFilter = HistoryFilter.REVEALED },
                label = { Text("Revealed (${totalCases - solvedCases})", fontSize = 12.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                modifier = Modifier.testTag("filter_revealed_cases")
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // LIST OF CASE HISTORY CARDS
        if (filteredRecords.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Assignment,
                        contentDescription = "No cases",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "No cases found in this category",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Solve cases in the Diagnosis section to build your history log.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredRecords, key = { it.id }) { record ->
                    CaseHistoryCard(
                        record = record,
                        onReviewClicked = { viewingRecord = record },
                        onReopenClicked = { onReopenCase(record.caseId) }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

    // REVIEW CASE DIALOG
    viewingRecord?.let { record ->
        CaseReviewDialog(
            record = record,
            onDismiss = { viewingRecord = null },
            onLoadInDiagnosis = {
                viewingRecord = null
                onReopenCase(record.caseId)
            }
        )
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = valueColor
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun CaseHistoryCard(
    record: CaseHistoryRecord,
    onReviewClicked: () -> Unit,
    onReopenClicked: () -> Unit
) {
    val isSolved = record.status == CaseStatus.SOLVED

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onReviewClicked() }
            .testTag("history_card_${record.caseNumber}"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(
            1.dp,
            if (isSolved) MedNormalGreen.copy(alpha = 0.3f) else MaterialTheme.colorScheme.outlineVariant
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // TOP ROW: CASE NUMBER & OUTCOME BADGE
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "Case #${record.caseNumber}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Text(
                        text = record.specialty,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }

                // OUTCOME BADGE
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSolved) MedNormalGreen.copy(alpha = 0.12f) else MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = if (isSolved) Icons.Default.CheckCircle else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = if (isSolved) MedNormalGreen else MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = if (isSolved) "+${record.scoreEarned} pts" else "Revealed",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSolved) MedNormalGreen else MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // DIAGNOSIS & CHIEF COMPLAINT
            Text(
                text = record.correctDiagnosis,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "${record.patientProfile} • ${record.chiefComplaintHeadline}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(8.dp))

            // METRICS ROW + ACTIONS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Tabs: ${record.tabsExplored}/7",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Guesses: ${record.attemptsCount}/7",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (record.hintsUsedCount > 0) {
                        Text(
                            text = "Hints: ${record.hintsUsedCount}",
                            fontSize = 11.sp,
                            color = Color(0xFFD97706)
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedButton(
                        onClick = onReviewClicked,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Text("Post-Mortem", fontSize = 11.sp)
                    }

                    Button(
                        onClick = onReopenClicked,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MedicalServices,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Open", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun CaseReviewDialog(
    record: CaseHistoryRecord,
    onDismiss: () -> Unit,
    onLoadInDiagnosis: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Case #${record.caseNumber} Review",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = record.correctDiagnosis,
                        style = MaterialTheme.typography.titleSmall,
                        color = MedNormalGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // PATIENT & COMPLAINT
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "PATIENT PROFILE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${record.patientProfile} • ${record.specialty}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = record.chiefComplaintHeadline,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                // CLINICAL PEARLS
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MedGoldScoreContainer.copy(alpha = 0.6f),
                    border = BorderStroke(1.dp, MedGoldScore.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = MedGoldScore,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "CLINICAL PEARLS & HALLMARKS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E)
                            )
                        }
                        Text(
                            text = record.clinicalPearls,
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // DIFFERENTIAL DIAGNOSES
                if (record.differentialDiagnoses.isNotEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "KEY DIFFERENTIAL DIAGNOSES",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                text = record.differentialDiagnoses.joinToString(" • "),
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }

                // PERFORMANCE SUMMARY
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Score Earned: ${record.scoreEarned} pts",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (record.scoreEarned > 0) MedNormalGreen else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Tabs Explored: ${record.tabsExplored} / 7",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onLoadInDiagnosis,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Re-Examine in Diagnosis", fontSize = 12.sp)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Close", fontSize = 12.sp)
            }
        }
    )
}
