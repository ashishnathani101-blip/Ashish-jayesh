package com.example.data

import com.example.model.CompendiumCategory
import com.example.model.CompendiumEntry

object CompendiumRepository {

    val entries: List<CompendiumEntry> = listOf(
        // TRIADS
        CompendiumEntry(
            id = "triad_01",
            title = "Charcot's Triad & Reynolds' Pentad",
            category = CompendiumCategory.CLASSIC_TRIADS,
            subtitle = "Acute Ascending Cholangitis",
            specialty = "Gastroenterology / Surgery",
            description = "Pathognomonic constellation of signs signifying biliary tree obstruction and bacterial superinfection.",
            keyPoints = listOf(
                "Charcot's Triad: Jaundice, Fever/chills, and Right Upper Quadrant pain.",
                "Reynolds' Pentad: Charcot's Triad PLUS Hypotension and Altered Mental Status (indicates toxic septic shock).",
                "Primary Organisms: E. coli, Klebsiella, Enterobacter."
            ),
            clinicalSignificance = "Medical emergency requiring immediate broad-spectrum intravenous antibiotics, ICU resuscitation, and urgent biliary decompression via ERCP."
        ),
        CompendiumEntry(
            id = "triad_02",
            title = "Beck's Triad",
            category = CompendiumCategory.CLASSIC_TRIADS,
            subtitle = "Cardiac Tamponade",
            specialty = "Cardiology / Emergency",
            description = "Three physical examination findings identifying elevated intrapericardial pressure restricting diastolic ventricular filling.",
            keyPoints = listOf(
                "1. Hypotension (narrowed pulse pressure).",
                "2. Jugular Venous Distension (elevated central venous pressure).",
                "3. Muffled / Distant Heart Sounds."
            ),
            clinicalSignificance = "Look also for Pulsus Paradoxus (>10 mmHg drop in SBP during inspiration). Requires emergency ultrasound-guided pericardiocentesis."
        ),
        CompendiumEntry(
            id = "triad_03",
            title = "Cushing's Triad",
            category = CompendiumCategory.CLASSIC_TRIADS,
            subtitle = "Elevated Intracranial Pressure (ICP)",
            specialty = "Neurosurgery / Critical Care",
            description = "Late physiological response to imminent brain herniation due to extreme intracranial hypertension.",
            keyPoints = listOf(
                "1. Hypertension with Widened Pulse Pressure (systolic rises much higher than diastolic).",
                "2. Bradycardia (reflex response to baroreceptor stimulation).",
                "3. Irregular / Cheyne-Stokes Respirations (brainstem compression)."
            ),
            clinicalSignificance = "Signs of impending uncal/tonsillar herniation. Requires emergent head elevation, hyperosmolar therapy (Mannitol / 3% saline), and neurosurgical decompression."
        ),
        CompendiumEntry(
            id = "triad_04",
            title = "Virchow's Triad",
            category = CompendiumCategory.CLASSIC_TRIADS,
            subtitle = "Thrombogenesis (DVT / PE)",
            specialty = "Hematology / Vascular",
            description = "The fundamental triad of pathophysiological factors contributing to venous thromboembolism.",
            keyPoints = listOf(
                "1. Stasis of Blood Flow: Prolonged immobilization, atrial fibrillation, venous insufficiency.",
                "2. Endothelial Injury: Vessel trauma, central lines, surgical manipulation, smoking.",
                "3. Hypercoagulability: Factor V Leiden, malignancy, oral contraceptives, pregnancy, antiphospholipid syndrome."
            ),
            clinicalSignificance = "Directs risk assessment, DVT prophylaxis decisions, and workup for provoked vs unprovoked thrombosis."
        ),

        // DIAGNOSTIC PEARLS
        CompendiumEntry(
            id = "pearl_01",
            title = "Acute Appendicitis Clinical Signs",
            category = CompendiumCategory.DIAGNOSTIC_PEARLS,
            subtitle = "Peritoneal Exam Signs & Hamburger Test",
            specialty = "General Surgery",
            description = "Classic maneuvers and signs identifying acute appendiceal inflammation and local peritonitis.",
            keyPoints = listOf(
                "Hamburger Sign: If patient is hungry and asks for food, appendicitis is extremely unlikely (markedly positive anorexia).",
                "McBurney's Point: Tenderness 1/3 distance from ASIS to umbilicus.",
                "Rovsing's Sign: RLQ pain evoked upon deep palpation of the LLQ.",
                "Psoas Sign: RLQ pain on passive right hip extension (retrocecal appendix).",
                "Obturator Sign: RLQ pain on internal rotation of flexed right hip (pelvic appendix)."
            ),
            clinicalSignificance = "Migrating pain (periumbilical -> RLQ) with anorexia and low-grade leukocytosis carries 90%+ diagnostic accuracy in young adults."
        ),
        CompendiumEntry(
            id = "pearl_02",
            title = "Albuminocytologic Dissociation",
            category = CompendiumCategory.DIAGNOSTIC_PEARLS,
            subtitle = "Guillain-Barré Syndrome (AIDP)",
            specialty = "Neurology",
            description = "Hallmark CSF laboratory finding distinguishing inflammatory demyelinating polyneuropathy from infectious meningitis/encephalitis.",
            keyPoints = listOf(
                "CSF Protein: Markedly elevated (often >100-200 mg/dL).",
                "CSF White Blood Cells: Normal (<5 cells/µL).",
                "Mechanism: Breakdown of blood-nerve barrier at the spinal roots without active cellular inflammation in the subarachnoid space."
            ),
            clinicalSignificance = "Typically peaks at 1-2 weeks after motor weakness onset. Treatment is IVIG or Plasma Exchange (plasmapheresis); Corticosteroids are ineffective."
        ),
        CompendiumEntry(
            id = "pearl_03",
            title = "Aortic Dissection BP Differential",
            category = CompendiumCategory.DIAGNOSTIC_PEARLS,
            subtitle = "Stanford Type A vs Type B Dissection",
            specialty = "Cardiology / Cardiothoracic Surgery",
            description = "High-sensitivity bedside clinical clue when evaluating acute tearing anterior or interscapular chest pain.",
            keyPoints = listOf(
                "Blood Pressure Asymmetry: A difference of >20 mmHg systolic between the right and left arms.",
                "Pulse Deficits: Diminished radial or femoral pulses due to false lumen compressing branch arteries.",
                "New Diastolic Murmur: Aortic valve regurgitation indicates Type A proximal dissection."
            ),
            clinicalSignificance = "Immediate CTA Chest/Abdomen or Transesophageal Echocardiogram (TEE). Type A requires emergency sternotomy and surgical repair; Type B is medical BP control (esmolol)."
        ),

        // EMERGENCY RED FLAGS
        CompendiumEntry(
            id = "flag_01",
            title = "Thunderclap Headache",
            category = CompendiumCategory.RED_FLAGS,
            subtitle = "Subarachnoid Hemorrhage (SAH)",
            specialty = "Neurology / Emergency",
            description = "Sudden-onset severe headache reaching peak excruciating intensity in less than 60 seconds.",
            keyPoints = listOf(
                "Patient Quote: 'The worst headache of my entire life.'",
                "Etiology: Ruptured intracranial saccular (berry) aneurysm (85%), cerebral venous thrombosis, RCVS.",
                "Diagnostic Algorithm: Non-contrast Head CT within 6 hours (high sensitivity). If negative and suspicion remains high, perform Lumbar Puncture for xanthochromia."
            ),
            clinicalSignificance = "Never dismiss sudden maximal intensity headaches as migraines without formal evaluation. Missing SAH carries high mortality."
        ),
        CompendiumEntry(
            id = "flag_02",
            title = "Cauda Equina Syndrome Red Flags",
            category = CompendiumCategory.RED_FLAGS,
            subtitle = "Surgical Lumbar Spine Emergency",
            specialty = "Neurosurgery / Orthopedics",
            description = "Compression of multiple lumbosacral nerve roots below the conus medullaris.",
            keyPoints = listOf(
                "1. Saddle Anesthesia: Sensory numbness over perineum, buttocks, and inner thighs (S3-S5 distribution).",
                "2. Bladder Dysfunction: Urinary retention with overflow incontinence (check post-void residual >200 mL).",
                "3. Bowel Incontinence: Lax anal sphincter tone on digital rectal examination.",
                "4. Progressive Bilateral Lower Extremity Weakness / foot drop."
            ),
            clinicalSignificance = "Requires stat emergent Lumbar Spine MRI and decompressive laminectomy within 24-48 hours to prevent irreversible permanent paralysis and sphincter dysfunction."
        ),
        CompendiumEntry(
            id = "flag_03",
            title = "Pain Out of Proportion to Exam",
            category = CompendiumCategory.RED_FLAGS,
            subtitle = "Acute Mesenteric Ischemia & Necrotizing Fasciitis",
            specialty = "Vascular Surgery / Emergency",
            description = "Severe agony in a patient whose physical exam appears relatively benign on initial superficial palpation.",
            keyPoints = listOf(
                "Abdomen: Severe diffuse agony, soft on palpation without early peritoneal signs. Suggests acute mesenteric artery occlusion or non-occlusive mesenteric ischemia.",
                "Extremity: Agony with wooden induration, swelling, or pain on passive movement. Suggests Compartment Syndrome or early Necrotizing Soft Tissue Infection.",
                "Lab Indicator: Elevated serum Lactate and profound leukocytosis."
            ),
            clinicalSignificance = "Do not wait for frank peritonitis or crepitus (which signify transmural bowel infarction or advanced gangrene). Order CTA Abdomen or surgical exploration immediately."
        ),

        // LAB RANGES
        CompendiumEntry(
            id = "lab_01",
            title = "Complete Blood Count (CBC) Reference",
            category = CompendiumCategory.LAB_RANGES,
            subtitle = "Standard Adult Reference Intervals",
            specialty = "Laboratory Medicine / Hematology",
            description = "Core hematologic indices essential for evaluating infection, anemia, and bleeding diathesis.",
            keyPoints = listOf(
                "WBC (White Blood Cells): 4,500 – 11,000 / µL. Left shift (>10% bands) indicates acute severe bacterial infection.",
                "Hemoglobin: Males 13.5 – 17.5 g/dL; Females 12.0 – 15.5 g/dL.",
                "Hematocrit: Males 41% – 50%; Females 36% – 46%.",
                "Platelets: 150,000 – 450,000 / µL. Spontaneous intracranial hemorrhage risk rises below 10,000 – 20,000 / µL.",
                "Mean Corpuscular Volume (MCV): 80 – 100 fL (Microcytic <80, Normocytic 80-100, Macrocytic >100)."
            ),
            clinicalSignificance = "Rapidly identifies inflammatory systemic responses, microcytic iron deficiency/thalassemia, macrocytic B12/folate deficiency, and thrombocytopenia."
        ),
        CompendiumEntry(
            id = "lab_02",
            title = "Basic Metabolic Panel (BMP) & Electrolytes",
            category = CompendiumCategory.LAB_RANGES,
            subtitle = "Electrolytes, Renal Function & Acid-Base",
            specialty = "Internal Medicine / Nephrology",
            description = "Serum biochemical concentrations evaluating hydration, renal clearance, and electrolyte homeostasis.",
            keyPoints = listOf(
                "Sodium (Na+): 135 – 145 mEq/L. Correct hyper/hyponatremia slowly to prevent ODS or cerebral edema.",
                "Potassium (K+): 3.5 – 5.0 mEq/L. Hyperkalemia (>5.5) carries severe arrhythmia risk (peaked T-waves, sine waves).",
                "Chloride (Cl-): 96 – 106 mEq/L.",
                "Bicarbonate (HCO3-): 22 – 28 mEq/L. Used to calculate Anion Gap: [Na+] - ([Cl-] + [HCO3-]); Normal = 8-12.",
                "BUN (Blood Urea Nitrogen): 7 – 20 mg/dL.",
                "Creatinine: 0.7 – 1.3 mg/dL (Males); 0.6 – 1.1 mg/dL (Females). BUN:Cr ratio > 20:1 suggests prerenal azotemia.",
                "Glucose (Fasting): 70 – 99 mg/dL. Fasting > 126 mg/dL diagnostic of Diabetes Mellitus."
            ),
            clinicalSignificance = "Critical for recognizing metabolic acidosis (MUDPILES / GOLDMARK), acute kidney injury (KDIGO stages), and electrolyte emergencies."
        )
    )

    fun search(query: String, category: CompendiumCategory = CompendiumCategory.ALL): List<CompendiumEntry> {
        val q = query.trim().lowercase()
        return entries.filter { entry ->
            val matchesCategory = category == CompendiumCategory.ALL || entry.category == category
            val matchesQuery = if (q.isBlank()) true else {
                entry.title.lowercase().contains(q) ||
                entry.subtitle.lowercase().contains(q) ||
                entry.specialty.lowercase().contains(q) ||
                entry.description.lowercase().contains(q) ||
                entry.clinicalSignificance.lowercase().contains(q) ||
                entry.keyPoints.any { it.lowercase().contains(q) }
            }
            matchesCategory && matchesQuery
        }
    }
}
