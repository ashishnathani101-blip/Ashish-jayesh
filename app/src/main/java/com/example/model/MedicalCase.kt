package com.example.model

enum class ParamStatus {
    NORMAL,
    HIGH,
    LOW,
    ABNORMAL
}

data class VitalItem(
    val name: String,
    val value: String,
    val unit: String = "",
    val status: ParamStatus = ParamStatus.NORMAL
)

data class LabItem(
    val testName: String,
    val result: String,
    val unit: String,
    val normalRange: String,
    val status: ParamStatus = ParamStatus.NORMAL
)

data class RadiologyReport(
    val modality: String, // e.g., "Chest Radiograph (PA & Lateral)", "Chest CT"
    val projection: String = "Posteroanterior (PA) Erect View",
    val findings: List<Pair<String, String>> = emptyList(),
    val impression: String,
    val keySigns: List<String> = emptyList(), // e.g. ["Visceral Pleural Line", "Deep Sulcus", "Hyperlucency"]
    val imageDrawableRes: Int? = null, // e.g. R.drawable.img_chest_xray
    val isUrgent: Boolean = false
)

data class CaseTab(
    val id: Int, // 1 to 7
    val title: String,
    val shortTitle: String,
    val iconType: String,
    val description: String,
    val details: List<Pair<String, String>> = emptyList(),
    val vitals: List<VitalItem>? = null,
    val labs: List<LabItem>? = null,
    val radiology: RadiologyReport? = null,
    val isXrayStudy: Boolean = false,
    val diagnosticNote: String? = null,
    val hint: String? = null // Specific clinical hint for this step/tab
)

data class MedicalCase(
    val id: String,
    val caseNumber: Int,
    val patientProfile: String, // e.g. "24-year-old female"
    val chiefComplaintHeadline: String, // e.g. "Sudden severe right lower quadrant abdominal pain"
    val specialty: String, // e.g. "Emergency / General Surgery"
    val difficulty: String, // e.g. "Core Case", "Intermediate", "Advanced"
    val tabs: List<CaseTab>, // 7 horizontal tabs
    val correctDiagnosis: String,
    val acceptedAliases: List<String>,
    val clinicalPearls: String,
    val differentialDiagnoses: List<String>
)

data class GuessAttempt(
    val attemptNumber: Int,
    val guessedText: String,
    val isCorrect: Boolean,
    val timestampMs: Long = System.currentTimeMillis()
)

enum class CaseStatus {
    IN_PROGRESS,
    SOLVED,
    EXHAUSTED
}

enum class AppSection(val label: String) {
    DIAGNOSIS("Diagnosis"),
    DAILY_10("Daily 10"),
    HISTORY("History"),
    CATEGORIES("Categories"),
    PROFILE("Profile");

    companion object {
        val COMPENDIUM = CATEGORIES
    }
}

data class DailyCheckInDay(
    val dayNumber: Int, // 1 to 8
    val xpReward: Int,
    val hintTokensReward: Int,
    val isGrandJackpot: Boolean = false,
    val specialRewardTitle: String? = null,
    val isClaimed: Boolean = false,
    val isAvailable: Boolean = false
)

data class Daily10CaseStatus(
    val caseNumber: Int, // 1 to 10
    val caseId: String,
    val title: String,
    val patientProfile: String,
    val specialty: String,
    val difficulty: String,
    val isSolved: Boolean = false,
    val scoreEarned: Int = 0
)

data class CaseHistoryRecord(
    val id: String,
    val caseId: String,
    val caseNumber: Int,
    val patientProfile: String,
    val chiefComplaintHeadline: String,
    val specialty: String,
    val correctDiagnosis: String,
    val status: CaseStatus,
    val wasRevealed: Boolean,
    val scoreEarned: Int,
    val attemptsCount: Int,
    val tabsExplored: Int,
    val hintsUsedCount: Int,
    val clinicalPearls: String,
    val differentialDiagnoses: List<String>,
    val timestampMs: Long = System.currentTimeMillis()
)

enum class UserRole(val label: String) {
    STUDENT("Medical Student"),
    DOCTOR("Doctor / Physician")
}

data class ClinicianProfile(
    val name: String = "Dr. Ashish Nathani",
    val role: UserRole = UserRole.DOCTOR,
    // Student specifics
    val studentYear: String = "3rd Year",
    val studentBranch: String = "MBBS / Clinical Medicine",
    val studentUniversity: String = "State Medical University",
    // Doctor specifics
    val doctorField: String = "Emergency & Internal Medicine",
    val doctorExperience: String = "5+ Years",
    val hospitalAffiliation: String = "Metropolitan General Hospital",
    // Avatar picture or preset
    val avatarUri: String? = null,
    val avatarPreset: String = "doctor_male", // "doctor_male", "doctor_female", "student_male", "student_female", "stethoscope"
    // Display labels
    val title: String = "Attending Physician",
    val department: String = "Emergency & Internal Medicine",
    val institution: String = "Metropolitan General Hospital"
) {
    val displayTitle: String
        get() = if (role == UserRole.STUDENT) "Student ($studentYear)" else "Physician ($doctorExperience)"

    val displayDepartment: String
        get() = if (role == UserRole.STUDENT) studentBranch else doctorField

    val displayInstitution: String
        get() = if (role == UserRole.STUDENT) studentUniversity else hospitalAffiliation
}

data class ClinicianAchievement(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val isUnlocked: Boolean,
    val progressText: String = ""
)

enum class CompendiumCategory(val label: String) {
    ALL("All"),
    DIAGNOSTIC_PEARLS("Pearls"),
    CLASSIC_TRIADS("Triads"),
    RED_FLAGS("Red Flags"),
    LAB_RANGES("Lab Ranges")
}

data class CompendiumEntry(
    val id: String,
    val title: String,
    val category: CompendiumCategory,
    val subtitle: String,
    val specialty: String,
    val description: String,
    val keyPoints: List<String>,
    val clinicalSignificance: String
)
