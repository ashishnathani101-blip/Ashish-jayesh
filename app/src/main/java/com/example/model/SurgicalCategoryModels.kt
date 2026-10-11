package com.example.model

/**
 * Data models for the Category tab in Medflix.
 * Featuring General Surgery and all major clinical subjects,
 * structured into specialized chapters with interactive mini case solving.
 */
data class MiniCaseOption(
    val id: Int,
    val text: String,
    val isCorrect: Boolean,
    val rationale: String
)

data class MiniCase(
    val id: String,
    val chapterId: String,
    val subjectId: String = "general_surgery",
    val title: String,
    val patientProfile: String,
    val chiefComplaint: String,
    val vignette: String,
    val vitals: List<Pair<String, String>> = emptyList(),
    val physicalExam: List<Pair<String, String>> = emptyList(),
    val keyInvestigation: String,
    val promptQuestion: String,
    val options: List<MiniCaseOption>,
    val explanation: String,
    val operativePearls: List<String> = emptyList(),
    val redFlags: List<String> = emptyList(),
    val difficulty: String = "Core", // "Core", "Intermediate", "Advanced"
    val pointsReward: Int = 100,
    val tokenReward: Int = 1,
    val linkedFullCaseId: String? = null // Optional link to full 7-tab Medflix case
)

data class SubjectChapter(
    val id: String,
    val subjectId: String,
    val name: String,
    val shortName: String,
    val subtitle: String,
    val iconType: String, // e.g. "breast", "trauma", "gi", "endocrine", etc.
    val highYieldPearl: String,
    val cases: List<MiniCase>
) {
    val totalCasesCount: Int
        get() = cases.size
}

data class MedicalSubject(
    val id: String,
    val name: String,
    val shortName: String,
    val description: String,
    val iconType: String,
    val chapters: List<SubjectChapter>
) {
    val totalCasesCount: Int
        get() = chapters.sumOf { it.cases.size }
}
