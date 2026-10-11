package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.NorthWest
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CaseStatus
import com.example.model.GuessAttempt
import com.example.ui.theme.MedAlertTertiary
import com.example.ui.theme.MedGoldScore
import com.example.ui.theme.MedGoldScoreContainer

@Composable
fun AnswerBottomBar(
    inputText: String,
    onInputTextChange: (String) -> Unit,
    suggestedDiagnoses: List<String>,
    onSelectSuggestion: (String) -> Unit,
    onSubmitGuess: () -> Unit,
    onRevealSolution: () -> Unit,
    onNextCase: () -> Unit,
    selectedTabId: Int,
    isCurrentTabHintUsed: Boolean,
    onUseHintForCurrentTab: () -> Unit,
    attemptsCount: Int,
    maxAttempts: Int,
    highestTabExplored: Int,
    totalTabsCount: Int,
    potentialScore: Int,
    caseStatus: CaseStatus,
    answerRevealed: Boolean,
    feedbackMessage: String?,
    isFeedbackError: Boolean,
    guessHistory: List<GuessAttempt>,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val isSolved = caseStatus == CaseStatus.SOLVED
    val canInput = caseStatus == CaseStatus.IN_PROGRESS && !answerRevealed

    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp,
        shadowElevation = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            // Feedback Banner (e.g. "Tab 3 unlocked" or score updates or error messages)
            AnimatedVisibility(
                visible = !feedbackMessage.isNullOrBlank(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                feedbackMessage?.let { msg ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isFeedbackError) {
                            MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.85f)
                        } else {
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.85f)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isFeedbackError) Icons.Default.ErrorOutline else Icons.Default.Info,
                                contentDescription = null,
                                tint = if (isFeedbackError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = msg,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                color = if (isFeedbackError) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            // DROP-UP MENU FOR NEAR OR EXACT MEDICAL DIAGNOSES SUGGESTIONS
            AnimatedVisibility(
                visible = canInput && suggestedDiagnoses.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                    ),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)
                    ),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .testTag("diagnosis_dropup_menu")
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 6.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "SUGGESTED DIAGNOSES (NEAR / EXACT)",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.8.sp
                                    ),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Text(
                                text = "Tap to choose",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }

                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
                            thickness = 0.8.dp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(3.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            suggestedDiagnoses.take(4).forEachIndexed { idx, suggestion ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("dropup_suggestion_$idx")
                                        .clickable {
                                            onSelectSuggestion(suggestion)
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp, vertical = 7.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.MedicalServices,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(15.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = suggestion,
                                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "Select",
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Icon(
                                                imageVector = Icons.Default.NorthWest,
                                                contentDescription = "Fill Diagnosis",
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(12.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Prior guesses chip row
            if (guessHistory.isNotEmpty() && canInput) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                ) {
                    Text(
                        text = "Prior guesses: ",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(guessHistory) { attempt ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                            ) {
                                Text(
                                    text = attempt.guessedText,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }

            if (canInput) {
                // ACTIVE INPUT BAR
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = onInputTextChange,
                        placeholder = {
                            Text(
                                text = "Type diagnosis (e.g. Appendicitis)...",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                onSubmitGuess()
                            }
                        ),
                        trailingIcon = {
                            if (inputText.isNotEmpty()) {
                                IconButton(onClick = { onInputTextChange("") }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear input",
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("diagnosis_input_field")
                    )

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            onSubmitGuess()
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .height(54.dp)
                            .testTag("submit_guess_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Submit Diagnosis",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Submit",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // HIGH-IMPACT STATUS & ACTIONS BAR (HINT BUTTON + HIGHLIGHTED POTENTIAL POINTS)
                val tabsUnread = (totalTabsCount - highestTabExplored).coerceAtLeast(0)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Hint Button for Current Step / Tab
                    OutlinedButton(
                        onClick = onUseHintForCurrentTab,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(
                            1.dp,
                            if (isCurrentTabHintUsed) MaterialTheme.colorScheme.outlineVariant
                            else MedGoldScore.copy(alpha = 0.8f)
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isCurrentTabHintUsed) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            else MedGoldScoreContainer.copy(alpha = 0.6f)
                        ),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier
                            .height(34.dp)
                            .testTag("step_hint_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "Step Hint",
                            tint = if (isCurrentTabHintUsed) MaterialTheme.colorScheme.onSurfaceVariant else MedGoldScore,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = if (isCurrentTabHintUsed) "Tab $selectedTabId Hint (Read)" else "Tab $selectedTabId Hint (-50 pts)",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = if (isCurrentTabHintUsed) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // PROMINENTLY HIGHLIGHTED POTENTIAL SCORE PILL
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFFEF3C7),
                        border = BorderStroke(1.5.dp, Color(0xFFF59E0B)),
                        shadowElevation = 1.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Stars,
                                contentDescription = "Points Reward",
                                tint = Color(0xFFD97706),
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "Win $potentialScore pts",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp,
                                    letterSpacing = 0.3.sp
                                ),
                                color = Color(0xFF78350F)
                            )
                        }
                    }
                }

                // Answer Reveal option: available on final step (Tab 7) or as desired
                if (selectedTabId == totalTabsCount && !answerRevealed) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onRevealSolution,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .testTag("tab7_reveal_answer_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Reveal Answer for Case (Final Step)",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }

            } else {
                // Case is either SOLVED or Answer is REVEALED
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isSolved) "Case solved! All tabs unlocked." else "Diagnosis revealed.",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Button(
                        onClick = onNextCase,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.testTag("bottom_next_case_button")
                    ) {
                        Text("Next Case")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
