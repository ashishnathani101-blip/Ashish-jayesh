package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.CaseRepository
import com.example.data.ClinicalCaseHints
import com.example.data.MedicalDiagnosisCatalog
import com.example.model.AppSection
import com.example.model.CaseHistoryRecord
import com.example.model.CaseStatus
import com.example.model.ClinicianAchievement
import com.example.model.ClinicianProfile
import com.example.model.CompendiumCategory
import com.example.model.Daily10CaseStatus
import com.example.model.DailyCheckInDay
import com.example.model.GuessAttempt
import com.example.model.MedicalCase
import com.example.model.MiniCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

data class GameUiState(
    val currentCase: MedicalCase,
    val currentCaseIndex: Int = 0,
    val highestTabExplored: Int = 1, // tracks maximum tab index accessed (1 to 7)
    val unlockedCluesCount: Int = 7, // All 7 tabs are unlocked!
    val selectedTabIndex: Int = 0, // currently active tab displayed on main display
    val attemptsCount: Int = 0, // tracks guesses (0 to 7)
    val maxAttempts: Int = 7,
    val currentInputText: String = "",
    val suggestedDiagnoses: List<String> = emptyList(), // Drop-up suggestions matching user input
    val caseStatus: CaseStatus = CaseStatus.IN_PROGRESS,
    val answerRevealed: Boolean = false,
    val scoreEarnedThisCase: Int = 0,
    val totalScore: Int = 350,
    val casesSolved: Int = 1,
    val currentStreak: Int = 1,
    val bestStreak: Int = 1,
    val feedbackMessage: String? = null,
    val isFeedbackError: Boolean = false,
    val guessHistory: List<GuessAttempt> = emptyList(),
    // Game Reward & Consumable Hint Token Economy
    val hintTokens: Int = 5, // Player's consumable inventory of Hint Tokens
    val hintsUsedWithPointsTabIds: Set<Int> = emptySet(), // Tab IDs where 50 pts were deducted
    val hintsUsedWithTokenTabIds: Set<Int> = emptySet(), // Tab IDs where a Hint Token was consumed (0 pts deducted!)
    val activeHintDialogTabId: Int? = null, // Tab ID currently showing hint dialog, or null
    val selectedSection: AppSection = AppSection.PROFILE,
    // Daily 8-Day Check-In System
    val checkInStreakDay: Int = 2, // Active check-in day ready to claim (Day 2)
    val claimedCheckInDays: Set<Int> = setOf(1), // Days already claimed (Day 1 claimed)
    val lastClaimedCheckInDay: DailyCheckInDay? = null,
    // Daily 10 Cases Progress
    val daily10SolvedCaseIds: Set<String> = setOf("case_01"),
    val daily10JackpotClaimed: Boolean = false,
    val caseHistory: List<CaseHistoryRecord> = listOf(
        CaseHistoryRecord(
            id = "hist_01",
            caseId = "case_01",
            caseNumber = 101,
            patientProfile = "21-year-old male (runner)",
            chiefComplaintHeadline = "Sudden right-sided pleuritic chest pain and dyspnea after feeling a sharp 'pop'",
            specialty = "Pulmonology / Emergency Radiology",
            correctDiagnosis = "Spontaneous Pneumothorax",
            status = CaseStatus.SOLVED,
            wasRevealed = false,
            scoreEarned = 400,
            attemptsCount = 1,
            tabsExplored = 2,
            hintsUsedCount = 0,
            clinicalPearls = "Hallmark: Visible visceral pleural line with absent peripheral lung vascular markings on upright PA chest radiograph. High-flow oxygen accelerates pleural air resorption.",
            differentialDiagnoses = listOf("Tension Pneumothorax", "Pulmonary Embolism", "Pleuritis", "Acute Pericarditis", "Costochondritis"),
            timestampMs = System.currentTimeMillis() - 7200000L
        )
    ),
    val selectedHistoryItem: CaseHistoryRecord? = null,
    val profile: ClinicianProfile = ClinicianProfile(
        name = "Dr. Ashish Nathani",
        role = com.example.model.UserRole.DOCTOR,
        doctorField = "Emergency & Internal Medicine",
        doctorExperience = "3+ Years",
        hospitalAffiliation = "City Teaching Hospital",
        studentYear = "3rd Year",
        studentBranch = "MBBS / Clinical Medicine",
        studentUniversity = "State Medical University",
        avatarPreset = "doctor_male"
    ),
    val compendiumSearchQuery: String = "",
    val selectedCompendiumCategory: CompendiumCategory = CompendiumCategory.ALL,
    // Category Tab & Surgical Chapters State
    val selectedSubjectId: String = "general_surgery",
    val selectedChapterId: String? = null,
    val categorySearchQuery: String = "",
    val activeSolvingMiniCase: MiniCase? = null,
    val solvedMiniCaseIds: Set<String> = setOf("mini_breast_01")
) {
    // Total hints used across both token and point usage
    val hintsUsedTabIds: Set<Int>
        get() = hintsUsedWithPointsTabIds + hintsUsedWithTokenTabIds

    // Current potential points if solved right now:
    // Only deducted for hints taken with point penalty; Token hints preserve full points!
    val potentialScore: Int
        get() {
            val tabsRemaining = (7 - highestTabExplored).coerceAtLeast(0)
            val basePotential = if (tabsRemaining > 0) tabsRemaining * 150 + 100 else 100
            val hintDeductions = hintsUsedWithPointsTabIds.size * 50
            return (basePotential - hintDeductions).coerceAtLeast(50)
        }

    val clinicianRank: String
        get() = when {
            totalScore >= 2000 -> "Department Chair"
            totalScore >= 1200 -> "Attending Diagnostician"
            totalScore >= 700 -> "Chief Medical Resident"
            totalScore >= 300 -> "Senior Resident"
            totalScore >= 100 -> "Junior Resident"
            else -> "Medical Intern"
        }

    val clinicianTierLevel: Int
        get() = when {
            totalScore >= 2000 -> 5
            totalScore >= 1200 -> 4
            totalScore >= 700 -> 3
            totalScore >= 300 -> 2
            totalScore >= 100 -> 1
            else -> 0
        }

    val nextRankThreshold: Int
        get() = when {
            totalScore >= 2000 -> 3000
            totalScore >= 1200 -> 2000
            totalScore >= 700 -> 1200
            totalScore >= 300 -> 700
            totalScore >= 100 -> 300
            else -> 100
        }

    val checkInDays: List<DailyCheckInDay>
        get() = listOf(
            DailyCheckInDay(1, xpReward = 50, hintTokensReward = 1, isClaimed = claimedCheckInDays.contains(1), isAvailable = checkInStreakDay == 1 && !claimedCheckInDays.contains(1)),
            DailyCheckInDay(2, xpReward = 75, hintTokensReward = 1, isClaimed = claimedCheckInDays.contains(2), isAvailable = checkInStreakDay == 2 && !claimedCheckInDays.contains(2)),
            DailyCheckInDay(3, xpReward = 100, hintTokensReward = 2, isClaimed = claimedCheckInDays.contains(3), isAvailable = checkInStreakDay == 3 && !claimedCheckInDays.contains(3)),
            DailyCheckInDay(4, xpReward = 125, hintTokensReward = 2, isClaimed = claimedCheckInDays.contains(4), isAvailable = checkInStreakDay == 4 && !claimedCheckInDays.contains(4)),
            DailyCheckInDay(5, xpReward = 150, hintTokensReward = 3, isClaimed = claimedCheckInDays.contains(5), isAvailable = checkInStreakDay == 5 && !claimedCheckInDays.contains(5)),
            DailyCheckInDay(6, xpReward = 200, hintTokensReward = 3, isClaimed = claimedCheckInDays.contains(6), isAvailable = checkInStreakDay == 6 && !claimedCheckInDays.contains(6)),
            DailyCheckInDay(7, xpReward = 250, hintTokensReward = 4, isClaimed = claimedCheckInDays.contains(7), isAvailable = checkInStreakDay == 7 && !claimedCheckInDays.contains(7)),
            DailyCheckInDay(
                8,
                xpReward = 500,
                hintTokensReward = 10,
                isGrandJackpot = true,
                specialRewardTitle = "Golden Grandmaster Badge",
                isClaimed = claimedCheckInDays.contains(8),
                isAvailable = checkInStreakDay == 8 && !claimedCheckInDays.contains(8)
            )
        )

    val daily10Cases: List<Daily10CaseStatus>
        get() = CaseRepository.cases.take(10).mapIndexed { index, medicalCase ->
            Daily10CaseStatus(
                caseNumber = index + 1,
                caseId = medicalCase.id,
                title = medicalCase.correctDiagnosis,
                patientProfile = medicalCase.patientProfile,
                specialty = medicalCase.specialty,
                difficulty = medicalCase.difficulty,
                isSolved = daily10SolvedCaseIds.contains(medicalCase.id),
                scoreEarned = caseHistory.find { it.caseId == medicalCase.id && it.status == CaseStatus.SOLVED }?.scoreEarned ?: 0
            )
        }

    val daily10SolvedCount: Int
        get() = daily10Cases.count { it.isSolved }

    val achievements: List<ClinicianAchievement>
        get() = listOf(
            ClinicianAchievement(
                id = "first_solve",
                title = "Hippocratic Oath",
                description = "Diagnose your first clinical case correctly",
                iconName = "check_circle",
                isUnlocked = casesSolved >= 1,
                progressText = if (casesSolved >= 1) "Completed" else "0/1"
            ),
            ClinicianAchievement(
                id = "sharp_mind",
                title = "Instant Triage",
                description = "Solve a case exploring only Tab 1 (Chief Complaint)",
                iconName = "speed",
                isUnlocked = caseHistory.any { it.status == CaseStatus.SOLVED && it.tabsExplored <= 1 },
                progressText = if (caseHistory.any { it.status == CaseStatus.SOLVED && it.tabsExplored <= 1 }) "Unlocked" else "Needs 1-Tab Win"
            ),
            ClinicianAchievement(
                id = "zero_hints",
                title = "Pure Clinical Intuition",
                description = "Correctly diagnose a case without taking any hints",
                iconName = "lightbulb",
                isUnlocked = caseHistory.any { it.status == CaseStatus.SOLVED && it.hintsUsedCount == 0 },
                progressText = if (caseHistory.any { it.status == CaseStatus.SOLVED && it.hintsUsedCount == 0 }) "Unlocked" else "0 Hints Used"
            ),
            ClinicianAchievement(
                id = "streak_3",
                title = "Diagnostic Momentum",
                description = "Achieve a winning streak of 3 consecutive cases",
                iconName = "trending_up",
                isUnlocked = bestStreak >= 3,
                progressText = "$bestStreak / 3 Streak"
            ),
            ClinicianAchievement(
                id = "points_500",
                title = "Grand Rounds Ace",
                description = "Accumulate over 500 total clinical points",
                iconName = "emoji_events",
                isUnlocked = totalScore >= 500,
                progressText = "$totalScore / 500 pts"
            ),
            ClinicianAchievement(
                id = "veteran",
                title = "Chief Fellow (1,000 pts)",
                description = "Reach 1,000+ points and lead department diagnoses",
                iconName = "school",
                isUnlocked = totalScore >= 1000,
                progressText = "$totalScore / 1000 pts"
            )
        )
}

class DiagnosisGameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        GameUiState(
            currentCase = CaseRepository.getCaseByIndex(0)
        )
    )
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun switchSection(section: AppSection) {
        _uiState.update { it.copy(selectedSection = section, feedbackMessage = null) }
    }

    fun selectHistoryItem(record: CaseHistoryRecord?) {
        _uiState.update { it.copy(selectedHistoryItem = record) }
    }

    fun resumeCase(caseId: String) {
        val caseIndex = CaseRepository.cases.indexOfFirst { it.id == caseId }
        if (caseIndex >= 0) {
            loadSpecificCase(caseIndex)
            _uiState.update {
                it.copy(
                    selectedSection = AppSection.DIAGNOSIS,
                    selectedHistoryItem = null
                )
            }
        }
    }

    fun launchDaily10Case(caseIndex: Int) {
        loadSpecificCase(caseIndex)
        _uiState.update {
            it.copy(
                selectedSection = AppSection.DIAGNOSIS,
                feedbackMessage = "Daily Case #${caseIndex + 1} loaded. Solve to earn XP & Hint Tokens!"
            )
        }
    }

    fun claimDailyCheckIn() {
        val state = _uiState.value
        val todayNumber = state.checkInStreakDay
        val dayConfig = state.checkInDays.find { it.dayNumber == todayNumber } ?: return

        if (state.claimedCheckInDays.contains(todayNumber)) {
            _uiState.update { it.copy(feedbackMessage = "You already claimed Day $todayNumber reward!") }
            return
        }

        val nextStreakDay = if (todayNumber >= 8) 1 else todayNumber + 1
        val newClaimed = state.claimedCheckInDays + todayNumber

        _uiState.update {
            it.copy(
                totalScore = it.totalScore + dayConfig.xpReward,
                hintTokens = it.hintTokens + dayConfig.hintTokensReward,
                claimedCheckInDays = newClaimed,
                checkInStreakDay = nextStreakDay,
                lastClaimedCheckInDay = dayConfig,
                feedbackMessage = if (dayConfig.isGrandJackpot) {
                    "🏆 Day 8 Grand Jackpot Claimed! +${dayConfig.xpReward} XP, +${dayConfig.hintTokensReward} Hint Tokens & Golden Badge!"
                } else {
                    "🎁 Day ${dayConfig.dayNumber} Reward Claimed! +${dayConfig.xpReward} XP & +${dayConfig.hintTokensReward} Hint Tokens!"
                },
                isFeedbackError = false
            )
        }
    }

    fun dismissClaimedRewardDialog() {
        _uiState.update { it.copy(lastClaimedCheckInDay = null) }
    }

    fun claimDaily10Jackpot() {
        val state = _uiState.value
        if (state.daily10SolvedCount < 10) {
            _uiState.update {
                it.copy(
                    feedbackMessage = "Solve all 10 daily cases to claim the Daily 10 Jackpot! (${state.daily10SolvedCount}/10 completed)",
                    isFeedbackError = true
                )
            }
            return
        }

        if (state.daily10JackpotClaimed) {
            _uiState.update { it.copy(feedbackMessage = "Daily 10 Jackpot already claimed today!") }
            return
        }

        _uiState.update {
            it.copy(
                totalScore = it.totalScore + 500,
                hintTokens = it.hintTokens + 5,
                daily10JackpotClaimed = true,
                feedbackMessage = "🎉 Daily 10 Rounds Completed! +500 Bonus XP & +5 Hint Tokens awarded!",
                isFeedbackError = false
            )
        }
    }

    fun updateProfile(name: String, title: String, department: String, institution: String) {
        _uiState.update {
            it.copy(
                profile = it.profile.copy(
                    name = name.trim().ifBlank { "Dr. Diagnostician" },
                    title = title.trim().ifBlank { "Resident Physician" },
                    department = department.trim().ifBlank { "General Medicine" },
                    institution = institution.trim().ifBlank { "City Teaching Hospital" }
                )
            )
        }
    }

    fun updateFullProfile(
        name: String,
        role: com.example.model.UserRole,
        doctorField: String,
        doctorExperience: String,
        hospitalAffiliation: String,
        studentYear: String,
        studentBranch: String,
        studentUniversity: String,
        avatarUri: String?,
        avatarPreset: String
    ) {
        _uiState.update {
            val updated = it.profile.copy(
                name = name.trim().ifBlank { if (role == com.example.model.UserRole.STUDENT) "Student Med" else "Dr. Physician" },
                role = role,
                doctorField = doctorField.trim().ifBlank { "Internal Medicine" },
                doctorExperience = doctorExperience.trim().ifBlank { "3+ Years" },
                hospitalAffiliation = hospitalAffiliation.trim().ifBlank { "Metropolitan Hospital" },
                studentYear = studentYear.trim().ifBlank { "3rd Year" },
                studentBranch = studentBranch.trim().ifBlank { "MBBS / Medicine" },
                studentUniversity = studentUniversity.trim().ifBlank { "State Medical School" },
                avatarUri = avatarUri,
                avatarPreset = avatarPreset,
                title = if (role == com.example.model.UserRole.STUDENT) "Medical Student ($studentYear)" else "Physician ($doctorExperience)",
                department = if (role == com.example.model.UserRole.STUDENT) studentBranch else doctorField,
                institution = if (role == com.example.model.UserRole.STUDENT) studentUniversity else hospitalAffiliation
            )
            it.copy(
                profile = updated,
                feedbackMessage = "Profile updated as ${role.label}!"
            )
        }
    }

    fun updateAvatar(uri: String?, preset: String) {
        _uiState.update {
            it.copy(
                profile = it.profile.copy(
                    avatarUri = uri,
                    avatarPreset = preset
                )
            )
        }
    }

    fun setCompendiumSearch(query: String) {
        _uiState.update { it.copy(compendiumSearchQuery = query) }
    }

    fun setCompendiumCategory(category: CompendiumCategory) {
        _uiState.update { it.copy(selectedCompendiumCategory = category) }
    }

    fun selectCategorySubject(subjectId: String) {
        _uiState.update { it.copy(selectedSubjectId = subjectId, selectedChapterId = null) }
    }

    fun selectCategoryChapter(chapterId: String?) {
        _uiState.update { it.copy(selectedChapterId = chapterId) }
    }

    fun setCategorySearch(query: String) {
        _uiState.update { it.copy(categorySearchQuery = query) }
    }

    fun startSolvingMiniCase(miniCase: MiniCase) {
        _uiState.update { it.copy(activeSolvingMiniCase = miniCase) }
    }

    fun dismissSolvingMiniCase() {
        _uiState.update { it.copy(activeSolvingMiniCase = null) }
    }

    fun solveMiniCase(caseId: String, points: Int, tokens: Int) {
        _uiState.update { current ->
            val isNew = !current.solvedMiniCaseIds.contains(caseId)
            current.copy(
                solvedMiniCaseIds = current.solvedMiniCaseIds + caseId,
                totalScore = if (isNew) current.totalScore + points else current.totalScore,
                hintTokens = if (isNew) current.hintTokens + tokens else current.hintTokens,
                casesSolved = if (isNew) current.casesSolved + 1 else current.casesSolved
            )
        }
    }

    fun launchFullCaseFromMiniCase(fullCaseId: String) {
        val caseIndex = CaseRepository.cases.indexOfFirst { it.id == fullCaseId }
        if (caseIndex >= 0) {
            loadSpecificCase(caseIndex)
            _uiState.update {
                it.copy(
                    selectedSection = AppSection.DIAGNOSIS,
                    activeSolvingMiniCase = null
                )
            }
        }
    }

    fun onInputTextChange(newText: String) {
        val matches = if (newText.isNotBlank()) {
            MedicalDiagnosisCatalog.findMatches(newText)
        } else {
            emptyList()
        }

        _uiState.update {
            it.copy(
                currentInputText = newText,
                suggestedDiagnoses = matches,
                feedbackMessage = null
            )
        }
    }

    fun selectSuggestion(suggestion: String) {
        _uiState.update {
            it.copy(
                currentInputText = suggestion,
                suggestedDiagnoses = emptyList(),
                feedbackMessage = null
            )
        }
    }

    fun selectTab(index: Int) {
        val state = _uiState.value
        val tabNumber = index + 1

        if (tabNumber > state.highestTabExplored && state.caseStatus == CaseStatus.IN_PROGRESS) {
            val newHighest = tabNumber
            val remaining = (7 - newHighest).coerceAtLeast(0)
            val basePotential = if (remaining > 0) remaining * 150 + 100 else 100
            val hintDeductions = state.hintsUsedWithPointsTabIds.size * 50
            val newPotential = (basePotential - hintDeductions).coerceAtLeast(50)

            _uiState.update {
                it.copy(
                    selectedTabIndex = index,
                    highestTabExplored = newHighest,
                    feedbackMessage = "Opened Tab $tabNumber (${state.currentCase.tabs[index].shortTitle}). Potential score: $newPotential pts.",
                    isFeedbackError = false
                )
            }
        } else {
            _uiState.update { it.copy(selectedTabIndex = index) }
        }
    }

    fun requestHint(tabId: Int, useToken: Boolean = false) {
        val state = _uiState.value
        val isAlreadyUsed = state.hintsUsedTabIds.contains(tabId)

        if (isAlreadyUsed || state.caseStatus != CaseStatus.IN_PROGRESS) {
            _uiState.update {
                it.copy(
                    activeHintDialogTabId = tabId,
                    feedbackMessage = "Hint for Tab $tabId"
                )
            }
            return
        }

        // Check if using a token
        if (useToken && state.hintTokens > 0) {
            _uiState.update {
                it.copy(
                    hintTokens = it.hintTokens - 1,
                    hintsUsedWithTokenTabIds = it.hintsUsedWithTokenTabIds + tabId,
                    activeHintDialogTabId = tabId,
                    feedbackMessage = "💡 Used 1 Hint Token! (Zero points deducted from case score, ${it.hintTokens - 1} tokens left)",
                    isFeedbackError = false
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    hintsUsedWithPointsTabIds = it.hintsUsedWithPointsTabIds + tabId,
                    activeHintDialogTabId = tabId,
                    feedbackMessage = "Hint for Tab $tabId (-50 pts deducted)",
                    isFeedbackError = false
                )
            }
        }
    }

    fun dismissHintDialog() {
        _uiState.update { it.copy(activeHintDialogTabId = null) }
    }

    fun submitGuess() {
        val state = _uiState.value
        val rawInput = state.currentInputText.trim()
        if (rawInput.isBlank()) {
            _uiState.update {
                it.copy(
                    feedbackMessage = "Please enter your suspected medical diagnosis.",
                    isFeedbackError = true
                )
            }
            return
        }

        if (state.caseStatus != CaseStatus.IN_PROGRESS) {
            return
        }

        val isCorrect = evaluateMatch(rawInput, state.currentCase)
        val newAttemptNumber = state.attemptsCount + 1

        if (isCorrect) {
            val totalTabs = state.currentCase.tabs.size
            val tabsLeft = (totalTabs - state.highestTabExplored).coerceAtLeast(0)
            val baseScore = if (tabsLeft > 0) {
                tabsLeft * 150 + 100
            } else {
                100
            }
            // Only deduct points for hints NOT redeemed with Hint Tokens
            val hintDeductions = state.hintsUsedWithPointsTabIds.size * 50
            val earnedScore = (baseScore - hintDeductions).coerceAtLeast(50)

            val newStreak = state.currentStreak + 1
            val updatedBestStreak = maxOf(state.bestStreak, newStreak)

            // Reward: Every solved case grants +1 Hint Token!
            val newTokens = state.hintTokens + 1
            val newDailySolved = state.daily10SolvedCaseIds + state.currentCase.id

            val historyRecord = CaseHistoryRecord(
                id = "hist_${System.currentTimeMillis()}",
                caseId = state.currentCase.id,
                caseNumber = state.currentCase.caseNumber,
                patientProfile = state.currentCase.patientProfile,
                chiefComplaintHeadline = state.currentCase.chiefComplaintHeadline,
                specialty = state.currentCase.specialty,
                correctDiagnosis = state.currentCase.correctDiagnosis,
                status = CaseStatus.SOLVED,
                wasRevealed = false,
                scoreEarned = earnedScore,
                attemptsCount = newAttemptNumber,
                tabsExplored = state.highestTabExplored,
                hintsUsedCount = state.hintsUsedTabIds.size,
                clinicalPearls = state.currentCase.clinicalPearls,
                differentialDiagnoses = state.currentCase.differentialDiagnoses
            )

            _uiState.update {
                it.copy(
                    caseStatus = CaseStatus.SOLVED,
                    attemptsCount = newAttemptNumber,
                    scoreEarnedThisCase = earnedScore,
                    totalScore = it.totalScore + earnedScore,
                    casesSolved = it.casesSolved + 1,
                    currentStreak = newStreak,
                    bestStreak = updatedBestStreak,
                    hintTokens = newTokens,
                    daily10SolvedCaseIds = newDailySolved,
                    currentInputText = "",
                    suggestedDiagnoses = emptyList(),
                    feedbackMessage = "Diagnostic Match! Confirmed: ${state.currentCase.correctDiagnosis} (+${earnedScore} pts, +1 💡 Hint Token earned!)",
                    isFeedbackError = false,
                    guessHistory = it.guessHistory + GuessAttempt(
                        attemptNumber = newAttemptNumber,
                        guessedText = rawInput,
                        isCorrect = true
                    ),
                    caseHistory = listOf(historyRecord) + it.caseHistory
                )
            }
        } else {
            val totalTabs = state.currentCase.tabs.size
            val nextExplored = (state.highestTabExplored + 1).coerceAtMost(totalTabs)

            _uiState.update {
                val feedback = if (nextExplored > it.highestTabExplored) {
                    "Diagnosis '$rawInput' not confirmed. Tab $nextExplored unlocked as clue!"
                } else {
                    "Diagnosis '$rawInput' not confirmed. Review clinical clues or use a hint!"
                }

                it.copy(
                    attemptsCount = newAttemptNumber,
                    highestTabExplored = nextExplored,
                    selectedTabIndex = (nextExplored - 1).coerceAtLeast(0),
                    currentInputText = "",
                    suggestedDiagnoses = emptyList(),
                    caseStatus = CaseStatus.IN_PROGRESS,
                    feedbackMessage = feedback,
                    isFeedbackError = true,
                    guessHistory = it.guessHistory + GuessAttempt(
                        attemptNumber = newAttemptNumber,
                        guessedText = rawInput,
                        isCorrect = false
                    )
                )
            }
        }
    }

    fun revealCaseSolution() {
        val state = _uiState.value
        val historyRecord = CaseHistoryRecord(
            id = "hist_${System.currentTimeMillis()}",
            caseId = state.currentCase.id,
            caseNumber = state.currentCase.caseNumber,
            patientProfile = state.currentCase.patientProfile,
            chiefComplaintHeadline = state.currentCase.chiefComplaintHeadline,
            specialty = state.currentCase.specialty,
            correctDiagnosis = state.currentCase.correctDiagnosis,
            status = CaseStatus.EXHAUSTED,
            wasRevealed = true,
            scoreEarned = 0,
            attemptsCount = state.attemptsCount,
            tabsExplored = state.currentCase.tabs.size,
            hintsUsedCount = state.hintsUsedTabIds.size,
            clinicalPearls = state.currentCase.clinicalPearls,
            differentialDiagnoses = state.currentCase.differentialDiagnoses
        )

        _uiState.update {
            it.copy(
                answerRevealed = true,
                highestTabExplored = it.currentCase.tabs.size,
                unlockedCluesCount = it.currentCase.tabs.size,
                feedbackMessage = "Case solution revealed: ${it.currentCase.correctDiagnosis}",
                isFeedbackError = false,
                currentStreak = 0,
                caseHistory = listOf(historyRecord) + it.caseHistory
            )
        }
    }

    fun loadNewCase() {
        val state = _uiState.value
        val nextIndex = (state.currentCaseIndex + 1) % CaseRepository.cases.size
        val nextCase = CaseRepository.getCaseByIndex(nextIndex)

        _uiState.update {
            it.copy(
                currentCase = nextCase,
                currentCaseIndex = nextIndex,
                highestTabExplored = 1,
                unlockedCluesCount = 7,
                selectedTabIndex = 0,
                attemptsCount = 0,
                currentInputText = "",
                suggestedDiagnoses = emptyList(),
                caseStatus = CaseStatus.IN_PROGRESS,
                answerRevealed = false,
                scoreEarnedThisCase = 0,
                feedbackMessage = null,
                isFeedbackError = false,
                guessHistory = emptyList(),
                hintsUsedWithPointsTabIds = emptySet(),
                hintsUsedWithTokenTabIds = emptySet(),
                activeHintDialogTabId = null
            )
        }
    }

    fun loadSpecificCase(index: Int) {
        val nextCase = CaseRepository.getCaseByIndex(index)
        _uiState.update {
            it.copy(
                currentCase = nextCase,
                currentCaseIndex = index,
                highestTabExplored = 1,
                unlockedCluesCount = 7,
                selectedTabIndex = 0,
                attemptsCount = 0,
                currentInputText = "",
                suggestedDiagnoses = emptyList(),
                caseStatus = CaseStatus.IN_PROGRESS,
                answerRevealed = false,
                scoreEarnedThisCase = 0,
                feedbackMessage = null,
                isFeedbackError = false,
                guessHistory = emptyList(),
                hintsUsedWithPointsTabIds = emptySet(),
                hintsUsedWithTokenTabIds = emptySet(),
                activeHintDialogTabId = null
            )
        }
    }

    private fun evaluateMatch(userGuess: String, currentCase: MedicalCase): Boolean {
        val normGuess = normalize(userGuess)
        val normCorrect = normalize(currentCase.correctDiagnosis)

        if (normGuess == normCorrect) return true

        for (alias in currentCase.acceptedAliases) {
            val normAlias = normalize(alias)
            if (normGuess == normAlias) return true
            if (normGuess.length >= 4 && normAlias.contains(normGuess)) return true
            if (normAlias.length >= 4 && normGuess.contains(normAlias)) return true
        }

        if (normGuess.length >= 4 && (normCorrect.contains(normGuess) || normGuess.contains(normCorrect))) {
            return true
        }

        val correctWords = normCorrect.split(" ").filter { it.length > 3 }
        val guessWords = normGuess.split(" ").filter { it.length > 3 }
        if (correctWords.isNotEmpty() && guessWords.isNotEmpty()) {
            val common = correctWords.intersect(guessWords.toSet())
            if (common.size >= correctWords.size) {
                return true
            }
        }

        return false
    }

    private fun normalize(str: String): String {
        return str.lowercase(Locale.ROOT)
            .replace("-", " ")
            .replace("'", "")
            .replace(",", "")
            .replace(".", "")
            .replace(Regex("\\s+"), " ")
            .trim()
    }
}
