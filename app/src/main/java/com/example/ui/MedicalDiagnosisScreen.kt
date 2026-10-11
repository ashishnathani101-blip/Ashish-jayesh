package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.ui.input.pointer.pointerInput
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.ClinicalCaseHints
import com.example.model.AppSection
import com.example.model.CaseStatus
import com.example.ui.components.AnswerBottomBar
import com.example.ui.components.AppBottomNavigation
import com.example.ui.components.CaseDebriefCard
import com.example.ui.components.CaseHistoryScreen
import com.example.ui.components.CategoryScreen
import com.example.ui.components.ClinicalCompendiumScreen
import com.example.ui.components.ClinicalHeader
import com.example.ui.components.Daily10Screen
import com.example.ui.components.HorizontalCaseTabs
import com.example.ui.components.MainCaseDisplay
import com.example.ui.components.MiniCaseSolverDialog
import com.example.ui.components.ProfileScreen
import com.example.ui.theme.MedGoldScore
import com.example.ui.theme.MedGoldScoreContainer
import com.example.ui.theme.MedNormalGreen
import android.app.Activity
import androidx.compose.ui.platform.LocalContext
import com.example.ui.components.MedflixExitAnimation
import com.example.viewmodel.DiagnosisGameViewModel
import kotlinx.coroutines.delay

@Composable
fun MedicalDiagnosisScreen(
    viewModel: DiagnosisGameViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val currentCase = uiState.currentCase
    val selectedTab = currentCase.tabs.getOrElse(uiState.selectedTabIndex) { currentCase.tabs.first() }

    val activeHintText = selectedTab.hint ?: ClinicalCaseHints.getHint(currentCase.id, selectedTab.id)
    val isCurrentTabHintUsed = uiState.hintsUsedTabIds.contains(selectedTab.id)

    val context = LocalContext.current
    var showOpeningAnimation by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(900)
        showOpeningAnimation = false
    }

    var showExitAnimation by remember { mutableStateOf(false) }
    var shouldFinishActivityOnExit by remember { mutableStateOf(false) }

    // BackHandler: If user is on another section, back returns to default PROFILE section
    // If on PROFILE, back triggers the Medflix medical exit outro animation
    BackHandler(enabled = true) {
        if (uiState.selectedSection != AppSection.PROFILE) {
            viewModel.switchSection(AppSection.PROFILE)
        } else {
            shouldFinishActivityOnExit = true
            showExitAnimation = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            topBar = {
                if (uiState.selectedSection == AppSection.DIAGNOSIS) {
                    Column {
                        ClinicalHeader(
                            currentCase = currentCase,
                            attemptsCount = uiState.attemptsCount,
                            maxAttempts = uiState.maxAttempts,
                            highestTabExplored = uiState.highestTabExplored,
                            potentialScore = uiState.potentialScore,
                            totalScore = uiState.totalScore,
                            currentStreak = uiState.currentStreak,
                            caseStatus = uiState.caseStatus,
                            onNewCaseClicked = { viewModel.loadNewCase() },
                            onExitFullScreen = { viewModel.switchSection(AppSection.PROFILE) }
                        )

                        HorizontalCaseTabs(
                            tabs = currentCase.tabs,
                            highestTabExplored = if (uiState.answerRevealed || uiState.caseStatus == CaseStatus.SOLVED) {
                                currentCase.tabs.size
                            } else {
                                uiState.highestTabExplored
                            },
                            selectedTabIndex = uiState.selectedTabIndex,
                            potentialScore = uiState.potentialScore,
                            hintsUsedTabIds = uiState.hintsUsedTabIds,
                            onTabSelected = { index -> viewModel.selectTab(index) }
                        )
                    }
                }
            },
            bottomBar = {
                Column {
                    // If on Diagnosis section, show diagnostic input bar in full-screen immersion without bottom navigation
                    if (uiState.selectedSection == AppSection.DIAGNOSIS) {
                        AnswerBottomBar(
                            inputText = uiState.currentInputText,
                            onInputTextChange = { viewModel.onInputTextChange(it) },
                            suggestedDiagnoses = uiState.suggestedDiagnoses,
                            onSelectSuggestion = { viewModel.selectSuggestion(it) },
                            onSubmitGuess = { viewModel.submitGuess() },
                            onRevealSolution = { viewModel.revealCaseSolution() },
                            onNextCase = { viewModel.loadNewCase() },
                            selectedTabId = selectedTab.id,
                            isCurrentTabHintUsed = isCurrentTabHintUsed,
                            onUseHintForCurrentTab = { viewModel.requestHint(selectedTab.id) },
                            attemptsCount = uiState.attemptsCount,
                            maxAttempts = uiState.maxAttempts,
                            highestTabExplored = uiState.highestTabExplored,
                            totalTabsCount = currentCase.tabs.size,
                            potentialScore = uiState.potentialScore,
                            caseStatus = uiState.caseStatus,
                            answerRevealed = uiState.answerRevealed,
                            feedbackMessage = uiState.feedbackMessage,
                            isFeedbackError = uiState.isFeedbackError,
                            guessHistory = uiState.guessHistory
                        )
                    }

                    // Global Bottom Navigation Bar (Hidden when in full-screen Diagnosis mode to maximize space for X-rays)
                    if (uiState.selectedSection != AppSection.DIAGNOSIS) {
                        AppBottomNavigation(
                            selectedSection = uiState.selectedSection,
                            onSectionSelected = { section -> viewModel.switchSection(section) },
                            dailyProgressText = "${uiState.daily10SolvedCount}/10",
                            historyCount = uiState.caseHistory.size
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AnimatedContent(
                    targetState = uiState.selectedSection,
                    transitionSpec = {
                        val navOrder = listOf(
                            AppSection.DIAGNOSIS,
                            AppSection.DAILY_10,
                            AppSection.HISTORY,
                            AppSection.CATEGORIES,
                            AppSection.PROFILE
                        )
                        val initialIdx = navOrder.indexOf(initialState).coerceAtLeast(0)
                        val targetIdx = navOrder.indexOf(targetState).coerceAtLeast(0)

                        if (targetIdx > initialIdx) {
                            (slideInHorizontally(animationSpec = tween(280)) { width -> width / 4 } + fadeIn(animationSpec = tween(240)))
                                .togetherWith(slideOutHorizontally(animationSpec = tween(240)) { width -> -width / 4 } + fadeOut(animationSpec = tween(200)))
                        } else {
                            (slideInHorizontally(animationSpec = tween(280)) { width -> -width / 4 } + fadeIn(animationSpec = tween(240)))
                                .togetherWith(slideOutHorizontally(animationSpec = tween(240)) { width -> width / 4 } + fadeOut(animationSpec = tween(200)))
                        }
                    },
                    label = "section_screen_transition"
                ) { section ->
                    when (section) {
                        AppSection.PROFILE -> {
                            // User lands on PROFILE first by default
                            ProfileScreen(
                                profile = uiState.profile,
                                totalScore = uiState.totalScore,
                                casesSolved = uiState.casesSolved,
                                totalEncounters = uiState.caseHistory.size,
                                currentStreak = uiState.currentStreak,
                                bestStreak = uiState.bestStreak,
                                clinicianRank = uiState.clinicianRank,
                                clinicianTierLevel = uiState.clinicianTierLevel,
                                nextRankThreshold = uiState.nextRankThreshold,
                                achievements = uiState.achievements,
                                hintTokensCount = uiState.hintTokens,
                                onUpdateProfile = { name, title, dept, inst ->
                                    viewModel.updateProfile(name, title, dept, inst)
                                },
                                onUpdateFullProfile = { name, role, doctorField, doctorExp, hospital, studentYear, studentBranch, studentUni, avatarUri, avatarPreset ->
                                    viewModel.updateFullProfile(
                                        name,
                                        role,
                                        doctorField,
                                        doctorExp,
                                        hospital,
                                        studentYear,
                                        studentBranch,
                                        studentUni,
                                        avatarUri,
                                        avatarPreset
                                    )
                                },
                                onExitApp = {
                                    shouldFinishActivityOnExit = true
                                    showExitAnimation = true
                                },
                                onPreviewExitAnimation = {
                                    shouldFinishActivityOnExit = false
                                    showExitAnimation = true
                                }
                            )
                        }

                        AppSection.DIAGNOSIS -> {
                            Column(modifier = Modifier.fillMaxSize()) {
                                // If Solved or Answer is revealed, show Debrief card at top of content
                                if (uiState.caseStatus == CaseStatus.SOLVED || uiState.answerRevealed) {
                                    val tabsLeft = (currentCase.tabs.size - uiState.highestTabExplored).coerceAtLeast(0)
                                    CaseDebriefCard(
                                        medicalCase = currentCase,
                                        caseStatus = uiState.caseStatus,
                                        scoreEarned = uiState.scoreEarnedThisCase,
                                        tabsRemaining = tabsLeft,
                                        onNextCase = { viewModel.loadNewCase() },
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                    )
                                }

                                // Main Display showing currently selected horizontal tab
                                MainCaseDisplay(
                                    currentTab = selectedTab,
                                    isUnlocked = true,
                                    isHintUsed = isCurrentTabHintUsed,
                                    activeHintText = activeHintText,
                                    onUseHint = { viewModel.requestHint(selectedTab.id) },
                                    isAnswerRevealed = uiState.answerRevealed || uiState.caseStatus == CaseStatus.SOLVED,
                                    onRevealAnswer = { viewModel.revealCaseSolution() },
                                    onNavigateTab = { index -> viewModel.selectTab(index) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        AppSection.DAILY_10 -> {
                            Daily10Screen(
                                dailyCases = uiState.daily10Cases,
                                solvedCount = uiState.daily10SolvedCount,
                                isJackpotClaimed = uiState.daily10JackpotClaimed,
                                checkInDays = uiState.checkInDays,
                                currentStreakDay = uiState.checkInStreakDay,
                                hintTokensCount = uiState.hintTokens,
                                lastClaimedReward = uiState.lastClaimedCheckInDay,
                                onClaimCheckIn = { viewModel.claimDailyCheckIn() },
                                onDismissClaimedDialog = { viewModel.dismissClaimedRewardDialog() },
                                onClaimJackpot = { viewModel.claimDaily10Jackpot() },
                                onLaunchCase = { caseIndex -> viewModel.launchDaily10Case(caseIndex) }
                            )
                        }

                        AppSection.HISTORY -> {
                            CaseHistoryScreen(
                                caseHistory = uiState.caseHistory,
                                onReopenCase = { caseId -> viewModel.resumeCase(caseId) }
                            )
                        }

                        AppSection.CATEGORIES -> {
                            ClinicalCompendiumScreen(
                                searchQuery = uiState.compendiumSearchQuery,
                                onSearchQueryChange = { viewModel.setCompendiumSearch(it) },
                                selectedCategory = uiState.selectedCompendiumCategory,
                                onCategorySelected = { viewModel.setCompendiumCategory(it) }
                            )
                        }
                    }
                }
            }
        }

        // Step Hint Dialog (Interactive Consumable Hint Token or Points selection)
        uiState.activeHintDialogTabId?.let { tabId ->
            val hintDialogTab = currentCase.tabs.find { it.id == tabId } ?: selectedTab
            val dialogHintText = hintDialogTab.hint ?: ClinicalCaseHints.getHint(currentCase.id, tabId)
            val isAlreadyRead = uiState.hintsUsedTabIds.contains(tabId)
            val hasTokens = uiState.hintTokens > 0

            AlertDialog(
                onDismissRequest = { viewModel.dismissHintDialog() },
                icon = {
                    Surface(
                        shape = CircleShape,
                        color = MedGoldScoreContainer,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = MedGoldScore,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                },
                title = {
                    Text(
                        text = "Step Hint • ${hintDialogTab.shortTitle} (Tab $tabId)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Text(
                                text = dialogHintText,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(14.dp)
                            )
                        }

                        if (!isAlreadyRead) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MedGoldScoreContainer.copy(alpha = 0.6f)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Your Hint Tokens:",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF78350F)
                                    )
                                    Text(
                                        text = "${uiState.hintTokens} 💡 Available",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF92400E)
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    if (!isAlreadyRead && hasTokens) {
                        Button(
                            onClick = {
                                viewModel.requestHint(tabId, useToken = true)
                                viewModel.dismissHintDialog()
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Use 1 Token (0 pts loss)")
                        }
                    } else {
                        Button(
                            onClick = { viewModel.dismissHintDialog() },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Understood")
                        }
                    }
                },
                dismissButton = {
                    if (!isAlreadyRead && hasTokens) {
                        OutlinedButton(
                            onClick = {
                                viewModel.requestHint(tabId, useToken = false)
                                viewModel.dismissHintDialog()
                            },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Take -50 pts instead")
                        }
                    } else if (!isAlreadyRead) {
                        OutlinedButton(
                            onClick = { viewModel.dismissHintDialog() },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Close")
                        }
                    }
                }
            )
        }

        // SNAPPY OPENING ANIMATION OVERLAY
        AnimatedVisibility(
            visible = showOpeningAnimation,
            enter = fadeIn(tween(150)),
            exit = fadeOut(tween(300))
        ) {
            SnappyOpeningSplash(onSkip = { showOpeningAnimation = false })
        }

        // SIGNATURE MEDFLIX MEDICAL OUTRO ANIMATION OVERLAY
        AnimatedVisibility(
            visible = showExitAnimation,
            enter = fadeIn(tween(200)),
            exit = fadeOut(tween(250))
        ) {
            MedflixExitAnimation(
                onExitComplete = {
                    showExitAnimation = false
                    if (shouldFinishActivityOnExit) {
                        (context as? Activity)?.finish()
                    }
                },
                onCancel = {
                    showExitAnimation = false
                }
            )
        }
    }
}

@Composable
private fun SnappyOpeningSplash(onSkip: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(450, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onSkip() },
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Pulsing Medflix Provider Emblem (Medical Teal + Crimson Cross)
                Box(
                    modifier = Modifier
                        .scale(pulseScale)
                        .size(88.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalHospital,
                            contentDescription = "Medflix Provider Emblem",
                            tint = Color.White,
                            modifier = Modifier.size(42.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "M E D F L I X",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 4.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Clinical Case Streaming & Diagnosis Platform",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Interactive Cases for Medical Students & Doctors",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Small sleek loading bar
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.5.dp,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Preparing clinical shift...",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
