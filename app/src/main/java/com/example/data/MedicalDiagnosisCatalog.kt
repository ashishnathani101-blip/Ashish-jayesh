package com.example.data

import java.util.Locale
import kotlin.math.min

object MedicalDiagnosisCatalog {

    val diagnoses: List<String> = listOf(
        // Core Case Diagnoses & Aliases
        "Acute Appendicitis",
        "Appendicitis",
        "ST-Elevation Myocardial Infarction (STEMI)",
        "Myocardial Infarction",
        "Acute Coronary Syndrome",
        "Non-ST-Elevation Myocardial Infarction (NSTEMI)",
        "Acute Pancreatitis",
        "Pancreatitis",
        "Pulmonary Embolism",
        "Deep Vein Thrombosis (DVT)",
        "Diabetic Ketoacidosis (DKA)",
        "Hyperosmolar Hyperglycemic State (HHS)",
        "Bacterial Meningitis",
        "Viral Meningitis",
        "Meningitis",
        "Guillain-Barré Syndrome",
        "Acute Inflammatory Demyelinating Polyneuropathy (AIDP)",

        // Differential Diagnoses from Cases
        "Acute Cholecystitis",
        "Cholecystitis",
        "Cholelithiasis",
        "Biliary Colic",
        "Diverticulitis",
        "Nephrolithiasis",
        "Kidney Stones",
        "Ovarian Torsion",
        "Testicular Torsion",
        "Ectopic Pregnancy",
        "Mesenteric Adenitis",
        "Mesenteric Ischemia",
        "Crohn's Disease",
        "Ulcerative Colitis",
        "Gastroenteritis",
        "Aortic Dissection",
        "Pericarditis",
        "Cardiac Tamponade",
        "Tension Pneumothorax",
        "Pneumothorax",
        "Esophageal Rupture (Boerhaave Syndrome)",
        "Angina Pectoris",
        "Unstable Angina",
        "Peptic Ulcer Disease",
        "Perforated Peptic Ulcer",
        "Small Bowel Obstruction",
        "Large Bowel Obstruction",
        "Hepatitis",
        "Acute Liver Failure",
        "Community-Acquired Pneumonia",
        "Pneumonia",
        "Costochondritis",
        "Congestive Heart Failure",
        "COPD Exacerbation",
        "Asthma Exacerbation",
        "Alcoholic Ketoacidosis",
        "Starvation Ketoacidosis",
        "Uremic Encephalopathy",
        "Sepsis",
        "Septic Shock",
        "Encephalitis",
        "Subarachnoid Hemorrhage",
        "Intracranial Hemorrhage",
        "Brain Abscess",
        "Listeria Meningitis",
        "Myasthenia Gravis",
        "Botulism",
        "Tick Paralysis",
        "Spinal Cord Compression",
        "Chronic Inflammatory Demyelinating Polyneuropathy (CIDP)",

        // Broad Emergency & Clinical Diagnoses
        "Acute Ischemic Stroke",
        "Transient Ischemic Attack (TIA)",
        "Atrial Fibrillation",
        "Ventricular Tachycardia",
        "Anaphylaxis",
        "Acute Tubular Necrosis",
        "Acute Kidney Injury",
        "Pyelonephritis",
        "Cirrhosis",
        "Hepatic Encephalopathy",
        "Adrenal Insufficiency (Addison's Disease)",
        "Adrenal Crisis",
        "Thyroid Storm",
        "Myxedema Coma",
        "Graves' Disease",
        "Multiple Sclerosis",
        "Amyotrophic Lateral Sclerosis (ALS)",
        "Parkinson's Disease",
        "Epilepsy / Seizure Disorder",
        "Status Epilepticus",
        "Cellulitis",
        "Necrotizing Fasciitis",
        "Infective Endocarditis",
        "Systemic Lupus Erythematosus (SLE)",
        "Rheumatoid Arthritis",
        "Temporal Arteritis (Giant Cell Arteritis)",
        "Polymyalgia Rheumatica",
        "Rhabdomyolysis",
        "Acute Compartment Syndrome",
        "Spontaneous Bacterial Peritonitis (SBP)",
        "Celiac Disease",
        "Immune Thrombocytopenic Purpura (ITP)",
        "Thrombotic Thrombocytopenic Purpura (TTP)",
        "Hemolytic Uremic Syndrome (HUS)",
        "Disseminated Intravascular Coagulation (DIC)",
        "Pheochromocytoma",
        "Cushing's Syndrome"
    ).distinct()

    /**
     * Search medical diagnoses matching or close to user input.
     * Returns ranked list of near or exact matches.
     */
    fun findMatches(rawQuery: String, maxResults: Int = 6): List<String> {
        val query = normalize(rawQuery)
        if (query.length < 2) return emptyList()

        val queryTokens = query.split(" ").filter { it.isNotBlank() }

        // Score candidates
        val scored = diagnoses.mapNotNull { diagnosis ->
            val normDiag = normalize(diagnosis)
            val diagTokens = normDiag.split(" ").filter { it.isNotBlank() }

            val score: Double? = when {
                // Exact full match
                normDiag == query -> 100.0

                // Diagnosis starts with query
                normDiag.startsWith(query) -> 90.0

                // Any word in diagnosis starts with query (e.g. query "append" in "Acute Appendicitis")
                diagTokens.any { it.startsWith(query) } -> 85.0

                // Diagnosis contains query
                normDiag.contains(query) -> 75.0

                // All query tokens are contained in diagnosis
                queryTokens.isNotEmpty() && queryTokens.all { qToken ->
                    diagTokens.any { it.startsWith(qToken) || it.contains(qToken) }
                } -> 70.0

                // Acronym match (e.g. "dka" -> "Diabetic Ketoacidosis (DKA)")
                extractAcronym(diagnosis).equals(query, ignoreCase = true) -> 80.0

                // Fuzzy match on tokens or whole string if length is reasonable
                query.length >= 3 -> {
                    val minTokenDistance = diagTokens.minOfOrNull { token ->
                        levenshteinDistance(query, token)
                    } ?: 99
                    val wholeDistance = levenshteinDistance(query, normDiag)

                    when {
                        minTokenDistance <= 2 -> 50.0 - (minTokenDistance * 10).toDouble()
                        wholeDistance <= 2 -> 45.0 - (wholeDistance * 10).toDouble()
                        else -> null
                    }
                }

                else -> null
            }

            if (score != null) diagnosis to score else null
        }

        return scored
            .sortedByDescending { it.second }
            .map { it.first }
            .distinct()
            .take(maxResults)
    }

    private fun normalize(text: String): String {
        return text.lowercase(Locale.ROOT)
            .replace(Regex("[^a-z0-9\\s]"), "")
            .replace(Regex("\\s+"), " ")
            .trim()
    }

    private fun extractAcronym(text: String): String {
        val insideParens = Regex("\\((.*?)\\)").find(text)?.groupValues?.getOrNull(1)
        if (!insideParens.isNullOrBlank()) {
            return insideParens.trim().lowercase(Locale.ROOT)
        }
        return text.split(" ")
            .filter { it.isNotBlank() }
            .map { it.first().lowercaseChar() }
            .joinToString("")
    }

    private fun levenshteinDistance(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }
        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j

        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                dp[i][j] = min(
                    dp[i - 1][j] + 1,
                    min(dp[i][j - 1] + 1, dp[i - 1][j - 1] + cost)
                )
            }
        }
        return dp[s1.length][s2.length]
    }
}
