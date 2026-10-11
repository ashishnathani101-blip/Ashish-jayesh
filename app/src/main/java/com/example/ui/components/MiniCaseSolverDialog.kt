package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.MiniCase
import com.example.ui.theme.MedEmeraldGreen
import com.example.ui.theme.MedGoldScore

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MiniCaseSolverDialog(
    miniCase: MiniCase,
    isAlreadySolved: Boolean,
    onDismiss: () -> Unit,
    onSolveSuccess: (points: Int, tokens: Int) -> Unit,
    onLaunchFullCase: ((String) -> Unit)? = null
) {
    var selectedOptionId by remember { mutableStateOf<Int?>(null) }
    var isSubmitted by remember { mutableStateOf(isAlreadySolved) }
    var wasSubmittedCorrectly by remember { mutableStateOf(isAlreadySolved) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(vertical = 20.dp)
                .clip(RoundedCornerShape(20.dp))
                .testTag("mini_case_solver_dialog"),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            shadowElevation = 12.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // TOP BAR: Subject / Chapter Header & Close Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.MedicalServices,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Column {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                ) {
                                    Text(
                                        text = miniCase.chapterId.replace("surg_", "").replace("_", " ").uppercase(),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = if (miniCase.difficulty == "Core") MedEmeraldGreen.copy(alpha = 0.12f) else MedGoldScore.copy(alpha = 0.18f)
                                ) {
                                    Text(
                                        text = miniCase.difficulty,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (miniCase.difficulty == "Core") MedEmeraldGreen else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Text(
                                text = "Interactive Mini Case Solving",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp).testTag("close_solver_dialog_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // CASE TITLE & PATIENT PROFILE
                Text(
                    text = miniCase.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalHospital,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = miniCase.patientProfile,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // CHIEF COMPLAINT CARD
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Chief Complaint",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = miniCase.chiefComplaint,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // CLINICAL VIGNETTE
                Text(
                    text = "Clinical Vignette",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = miniCase.vignette,
                    style = MaterialTheme.typography.bodySmall,
                    lineHeight = 19.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // VITALS & PHYSICAL EXAM (IF PRESENT)
                if (miniCase.vitals.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Vitals on Arrival",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        miniCase.vitals.forEach { (label, value) ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "$label: ",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = value,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }

                if (miniCase.physicalExam.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Key Physical Findings",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            miniCase.physicalExam.forEach { (sign, detail) ->
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = "• $sign: ",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = detail,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }

                // KEY INVESTIGATION
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp).padding(top = 2.dp)
                        )
                        Column {
                            Text(
                                text = "Key Investigation & Diagnostic Findings",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = miniCase.keyInvestigation,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f))
                Spacer(modifier = Modifier.height(14.dp))

                // QUESTION PROMPT
                Text(
                    text = miniCase.promptQuestion,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                // OPTIONS (A, B, C, D)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    miniCase.options.forEachIndexed { index, option ->
                        val letter = ('A' + index).toString()
                        val isSelected = selectedOptionId == option.id

                        val cardBg = when {
                            isSubmitted && option.isCorrect -> MedEmeraldGreen.copy(alpha = 0.15f)
                            isSubmitted && isSelected && !option.isCorrect -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
                            isSelected -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            else -> MaterialTheme.colorScheme.surface
                        }

                        val cardBorder = when {
                            isSubmitted && option.isCorrect -> BorderStroke(1.5.dp, MedEmeraldGreen)
                            isSubmitted && isSelected && !option.isCorrect -> BorderStroke(1.5.dp, MaterialTheme.colorScheme.error)
                            isSelected -> BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
                            else -> BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f))
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = cardBg,
                            border = cardBorder,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable(enabled = !isSubmitted) {
                                    selectedOptionId = option.id
                                }
                                .testTag("option_${miniCase.id}_$letter")
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = when {
                                        isSubmitted && option.isCorrect -> MedEmeraldGreen
                                        isSubmitted && isSelected && !option.isCorrect -> MaterialTheme.colorScheme.error
                                        isSelected -> MaterialTheme.colorScheme.primary
                                        else -> MaterialTheme.colorScheme.surfaceVariant
                                    },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        if (isSubmitted && option.isCorrect) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        } else {
                                            Text(
                                                text = letter,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }

                                Text(
                                    text = option.text,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = if (isSelected || (isSubmitted && option.isCorrect)) FontWeight.SemiBold else FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // SUBMIT BUTTON / DEBRIEF SECTION
                if (!isSubmitted) {
                    Button(
                        onClick = {
                            if (selectedOptionId != null) {
                                isSubmitted = true
                                val chosen = miniCase.options.find { it.id == selectedOptionId }
                                val correct = chosen?.isCorrect == true
                                wasSubmittedCorrectly = correct
                                if (correct) {
                                    onSolveSuccess(miniCase.pointsReward, miniCase.tokenReward)
                                }
                            }
                        },
                        enabled = selectedOptionId != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_mini_case_answer_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Confirm Diagnostic Plan", fontWeight = FontWeight.Bold)
                    }
                } else {
                    // POST-SUBMISSION DEBRIEF
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically()
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            // RESULT BANNER
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (wasSubmittedCorrectly) MedEmeraldGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.35f),
                                border = BorderStroke(1.dp, if (wasSubmittedCorrectly) MedEmeraldGreen else MaterialTheme.colorScheme.error),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = if (wasSubmittedCorrectly) Icons.Default.CheckCircle else Icons.Default.ErrorOutline,
                                        contentDescription = null,
                                        tint = if (wasSubmittedCorrectly) MedEmeraldGreen else MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Column {
                                        Text(
                                            text = if (wasSubmittedCorrectly) "Outstanding Diagnostic Reasoning!" else "Diagnostic Review Needed",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = if (wasSubmittedCorrectly) MedEmeraldGreen else MaterialTheme.colorScheme.error
                                        )
                                        Text(
                                            text = if (wasSubmittedCorrectly) "+${miniCase.pointsReward} Clinical XP & +${miniCase.tokenReward} Hint Token Earned" else "Review the clinical rationale and operative pearls below.",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }

                            // CLINICAL EXPLANATION
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Lightbulb,
                                            contentDescription = null,
                                            tint = MedGoldScore,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(
                                            text = "Clinical Rationale",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = miniCase.explanation,
                                        style = MaterialTheme.typography.bodySmall,
                                        lineHeight = 20.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }

                            // OPERATIVE PEARLS
                            if (miniCase.operativePearls.isNotEmpty()) {
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Text(
                                            text = "High-Yield Operative Pearls",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        miniCase.operativePearls.forEach { pearl ->
                                            Text(
                                                text = "✓ $pearl",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                modifier = Modifier.padding(vertical = 2.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            // RED FLAGS
                            if (miniCase.redFlags.isNotEmpty()) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.4f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Warning,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.error,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Text(
                                                text = "Complication & Pitfall Red Flags",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        miniCase.redFlags.forEach { flag ->
                                            Text(
                                                text = "⚠ $flag",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                modifier = Modifier.padding(vertical = 1.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            // ACTION BUTTONS
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (miniCase.linkedFullCaseId != null && onLaunchFullCase != null) {
                                    OutlinedButton(
                                        onClick = {
                                            onLaunchFullCase(miniCase.linkedFullCaseId)
                                            onDismiss()
                                        },
                                        modifier = Modifier.weight(1f).height(46.dp),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.PlayArrow,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Full 7-Clue Case", fontSize = 12.sp)
                                    }
                                }

                                Button(
                                    onClick = onDismiss,
                                    modifier = Modifier.weight(1f).height(46.dp).testTag("close_solver_done_btn"),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Text("Case Complete", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
