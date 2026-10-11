package com.example.data

import com.example.R
import com.example.model.CaseTab
import com.example.model.LabItem
import com.example.model.MedicalCase
import com.example.model.ParamStatus
import com.example.model.RadiologyReport
import com.example.model.VitalItem

object CaseRepository {

    val cases: List<MedicalCase> = listOf(
        // CASE 1 (DEFAULT): Spontaneous Pneumothorax (Chest Radiograph / X-Ray Case)
        MedicalCase(
            id = "case_01",
            caseNumber = 101,
            patientProfile = "21-year-old male (runner)",
            chiefComplaintHeadline = "Sudden-onset right pleuritic chest pain and dyspnea after feeling a sharp 'pop'",
            specialty = "Pulmonology / Emergency Radiology",
            difficulty = "Core X-Ray Case",
            tabs = listOf(
                CaseTab(
                    id = 1,
                    title = "Chief Complaint & Triage",
                    shortTitle = "Complaint",
                    iconType = "triage",
                    description = "A 21-year-old college student presents to the emergency department clutching his right chest, reporting sudden-onset stabbing thoracic pain and breathlessness while studying at his desk.",
                    details = listOf(
                        "Presenting Symptom" to "Sharp right-sided pleuritic chest pain and abrupt shortness of breath",
                        "Onset" to "Abrupt onset 50 minutes ago; felt a sudden internal 'pop' sensation",
                        "Severity" to "8/10 on inspiration, unable to complete full sentences without pausing",
                        "Associated Symptoms" to "Dry cough, diaphoresis, lightheadedness on standing"
                    ),
                    diagnosticNote = "Triage note: Asthenic tall male (6'3\", 145 lbs), seated upright, splinting right hemithorax with rapid shallow breathing."
                ),
                CaseTab(
                    id = 2,
                    title = "History of Present Illness (HPI)",
                    shortTitle = "HPI",
                    iconType = "history",
                    description = "Pain began without trauma while sitting. The patient experienced acute unilateral pleuritic pain that worsens sharply with deep inspiration and cough.",
                    details = listOf(
                        "Pain Character" to "Sharp, knife-like pleuritic catch localized to right mid-to-upper chest",
                        "Radiation" to "Radiates to right shoulder tip and trapezius ridge",
                        "Aggravating Factors" to "Deep breathing, coughing, turning torso",
                        "Alleviating Factors" to "Shallow breaths, sitting upright leaning slightly forward",
                        "Cardiovascular Review" to "Denies crushing substernal pressure, diaphoresis, or left arm radiation"
                    )
                ),
                CaseTab(
                    id = 3,
                    title = "Past Medical History & Habitus",
                    shortTitle = "PMH",
                    iconType = "medical_history",
                    description = "Asthenic body habitus without preceding lung disease. Active smoker.",
                    details = listOf(
                        "Body Habitus" to "Height 191 cm (6'3\"), weight 66 kg (BMI 18.1 kg/m² - tall, slender build)",
                        "Past Medical History" to "No asthma, no prior pneumonia or chronic lung pathology",
                        "Social History" to "Cigarette smoking (0.5 pack/day for 3 years, 20x relative risk for bleb rupture); occasional vaping",
                        "Family History" to "Denies spontaneous pneumothorax or connective tissue disorders in first-degree relatives",
                        "Allergies & Meds" to "NKDA; takes no prescribed medications"
                    )
                ),
                CaseTab(
                    id = 4,
                    title = "Physical Examination",
                    shortTitle = "Exam",
                    iconType = "physical_exam",
                    description = "Focused thoracic examination reveals dramatic asymmetry in breath sounds and percussion note.",
                    details = listOf(
                        "Inspection" to "Reduced right chest wall excursion; accessory muscle usage during inspiration",
                        "Auscultation" to "Markedly diminished to absent breath sounds over right upper and middle lung fields; normal vesicular sounds on left",
                        "Percussion" to "Marked hyperresonance to percussion across the entire right hemithorax",
                        "Tactile Fremitus" to "Absent tactile vocal fremitus over right anterior and lateral lung fields",
                        "Tracheal Position" to "Trachea is midline; no jugular venous distention (reassuring: no tension pneumothorax currently)"
                    )
                ),
                CaseTab(
                    id = 5,
                    title = "Vital Signs & Hemodynamics",
                    shortTitle = "Vitals",
                    iconType = "vitals",
                    description = "Bedside monitoring shows hypoxemia, tachypnea, and compensatory sinus tachycardia.",
                    details = listOf(
                        "Oxygen Therapy" to "Placed on 4 L/min supplemental oxygen via nasal cannula (SpO2 rose from 91% to 97%)",
                        "Hemodynamic Status" to "Normotensive without pulsus paradoxus or hypotension"
                    ),
                    vitals = listOf(
                        VitalItem("Heart Rate", "116", "bpm (Sinus Tachycardia)", ParamStatus.HIGH),
                        VitalItem("Respiratory Rate", "26", "breaths/min (Tachypneic)", ParamStatus.HIGH),
                        VitalItem("SpO2 (Room Air)", "91", "% (Mild Hypoxemia)", ParamStatus.LOW),
                        VitalItem("Blood Pressure", "118/74", "mmHg", ParamStatus.NORMAL),
                        VitalItem("Temperature", "36.8", "°C (98.2°F - Afebrile)", ParamStatus.NORMAL),
                        VitalItem("Pain Score", "8 / 10", "Pleuritic catch", ParamStatus.HIGH)
                    )
                ),
                CaseTab(
                    id = 6,
                    title = "Laboratory Diagnostics & ABG",
                    shortTitle = "Labs",
                    iconType = "labs",
                    description = "Arterial blood gas confirms acute ventilation-perfusion mismatch; biomarkers rule out ACS and PE.",
                    details = listOf(
                        "Arterial Blood Gas (ABG)" to "Acute respiratory alkalosis with mild hypoxemia on ambient room air",
                        "D-Dimer" to "195 ng/mL (Normal < 500 ng/mL; strongly rules out acute pulmonary embolism)",
                        "Cardiac Troponin I" to "< 0.01 ng/mL (Undetectable; excludes acute myocardial infarction)"
                    ),
                    labs = listOf(
                        LabItem("Arterial pH", "7.48", "", "7.35 - 7.45", ParamStatus.HIGH),
                        LabItem("PaO2", "68", "mmHg", "80 - 100", ParamStatus.LOW),
                        LabItem("PaCO2", "31", "mmHg", "35 - 45", ParamStatus.LOW),
                        LabItem("Serum HCO3-", "23", "mEq/L", "22 - 28", ParamStatus.NORMAL),
                        LabItem("White Blood Cells (WBC)", "7.2", "x10^9/L", "4.5 - 11.0", ParamStatus.NORMAL),
                        LabItem("Hemoglobin", "15.4", "g/dL", "13.5 - 17.5", ParamStatus.NORMAL)
                    )
                ),
                CaseTab(
                    id = 7,
                    title = "Chest Radiograph & Radiological Report",
                    shortTitle = "X-Ray",
                    iconType = "imaging",
                    description = "High-resolution upright PA Chest Radiograph provides definitive diagnostic confirmation.",
                    isXrayStudy = true,
                    radiology = RadiologyReport(
                        modality = "Chest Radiograph (PA & Lateral Erect)",
                        projection = "Posteroanterior (PA) Upright Full Expiratory/Inspiratory View",
                        imageDrawableRes = R.drawable.img_chest_xray,
                        isUrgent = true,
                        findings = listOf(
                            "Pleural Space" to "Sharply demarcated thin visceral pleural line identified in the right apex and lateral hemithorax, separated by 3.4 cm from the thoracic wall.",
                            "Lung Parenchyma" to "Complete absence of broncho-vascular lung markings peripheral to the right visceral pleural line (hyperlucent pleural space). Left lung parenchyma is fully aerated and clear.",
                            "Mediastinum & Trachea" to "Trachea and cardiac silhouette remain midline. No gross tension-induced contralateral shift observed at current rest.",
                            "Cardiac Silhouette & Diaphragm" to "Normal cardiothoracic ratio (CTR < 0.5). Normal right diaphragmatic contour; no deep sulcus depression. No pleural effusion."
                        ),
                        keySigns = listOf("Visceral Pleural Line", "Peripheral Hyperlucency", "Absent Vascular Markings", "Deep Sulcus Sign"),
                        impression = "Large Primary Spontaneous Pneumothorax of the right lung with ~35-40% volume collapse. Subpleural apical bleb rupture in tall, asthenic male. Immediate needle aspiration or small-bore (14 Fr) chest tube thoracostomy indicated."
                    ),
                    details = listOf(
                        "Radiographic Modality" to "Digital Posteroanterior (PA) Erect Chest Radiograph (X-Ray)",
                        "Key Diagnostic Sign" to "Visceral pleural line with complete absence of peripheral lung vascular markings",
                        "Pneumothorax Size" to "Interpleural distance >2 cm at hilum (3.4 cm apical) meets BTS criteria for Large Pneumothorax",
                        "Recommended Intervention" to "High-flow supplemental oxygen (accelerates air resorption 4x) + needle aspiration or chest drain"
                    ),
                    diagnosticNote = "Definitive Diagnostic Finding: Upright Chest Radiograph shows classic visceral pleural line with absent peripheral lung markings confirming Spontaneous Pneumothorax."
                )
            ),
            correctDiagnosis = "Spontaneous Pneumothorax",
            acceptedAliases = listOf(
                "pneumothorax",
                "spontaneous pneumothorax",
                "primary spontaneous pneumothorax",
                "right pneumothorax",
                "tension pneumothorax",
                "collapsed lung"
            ),
            clinicalPearls = "Typically affects young, tall, thin males (smokers have a 20-fold increased risk) due to rupture of apical subpleural blebs. Absence of lung markings outside a sharply defined visceral pleural line on PA chest radiography is diagnostic. 100% FiO2 accelerates pleural air resorption up to 4-fold.",
            differentialDiagnoses = listOf("Tension Pneumothorax", "Pleurisy", "Costochondritis", "Pulmonary Embolism", "Boerhaave Syndrome")
        ),

        // CASE 2: Pulmonary Embolism
        MedicalCase(
            id = "case_02",
            caseNumber = 102,
            patientProfile = "58-year-old female",
            chiefComplaintHeadline = "Sudden-onset pleuritic chest pain and breathlessness 10 days post-hip arthroplasty",
            specialty = "Pulmonology / Critical Care",
            difficulty = "High-Yield Clinical Case",
            tabs = listOf(
                CaseTab(
                    id = 1,
                    title = "Chief Complaint & Triage",
                    shortTitle = "Complaint",
                    iconType = "triage",
                    description = "A 58-year-old woman is brought to the ED by ambulance reporting abrupt, sharp right-sided chest pain and sudden air hunger that woke her from sleep.",
                    details = listOf(
                        "Presenting Symptom" to "Acute dyspnea and sharp right hemithorax chest pain",
                        "Onset" to "Abrupt, 2 hours prior to arrival",
                        "Severity" to "8/10 on inspiration, lightheaded on standing",
                        "Associated Symptoms" to "Persistent dry cough, diaphoresis, sudden feeling of impending doom"
                    ),
                    diagnosticNote = "Triage: Patient is tachypneic and sitting upright, clutching her right lateral chest."
                ),
                CaseTab(
                    id = 2,
                    title = "History of Present Illness (HPI)",
                    shortTitle = "HPI",
                    iconType = "history",
                    description = "Patient describes the pain as a knife-like catch aggravated by deep inspiration or coughing. She denies prodromal fevers, chills, or prior similar episodes.",
                    details = listOf(
                        "Pain Description" to "Severe pleuritic right-sided chest pain, sharp and localized",
                        "Respiratory Status" to "Rapid, shallow breathing to avoid pleural stretch pain",
                        "Extremity Symptoms" to "Noticed mild swelling and aching in her left calf starting 3 days ago during recovery",
                        "Hemoptysis" to "Small streak of blood-tinged sputum with cough this morning",
                        "Mobility" to "Largely bed-to-chair bound since hospital discharge"
                    )
                ),
                CaseTab(
                    id = 3,
                    title = "Past Medical History & Meds",
                    shortTitle = "PMH",
                    iconType = "medical_history",
                    description = "Recent major orthopedic procedure; stopped enoxaparin prophylaxis 4 days early due to mild bruising.",
                    details = listOf(
                        "Surgical History" to "Elective Left Total Hip Arthroplasty (THA) 10 days ago",
                        "Past Medical History" to "Hypertension, mild osteoarthritis, BMI 31 kg/m²",
                        "Medications" to "Amlodipine 5mg daily; prescribed Enoxaparin 40mg SC daily but self-discontinued on postoperative day 6",
                        "Allergies" to "NKDA",
                        "Family History" to "Maternal aunt had deep vein thrombosis in her 60s"
                    )
                ),
                CaseTab(
                    id = 4,
                    title = "Physical Examination",
                    shortTitle = "Exam",
                    iconType = "physical_exam",
                    description = "Cardiopulmonary examination reveals marked tachypnea, tachycardia, and unilateral lower extremity signs.",
                    details = listOf(
                        "Cardiovascular" to "Tachycardic, regular rhythm, prominent pulmonary component of second heart sound (loud P2), no murmurs",
                        "Pulmonary" to "Clear to auscultation bilaterally; faint pleural friction rub auscultated at right lung base",
                        "Extremities" to "Left calf demonstrates 3.5 cm greater circumference than right, tender along deep posterior compartment, positive Homans sign",
                        "Neurological" to "Alert, oriented x4, anxious, mild postural dizziness"
                    )
                ),
                CaseTab(
                    id = 5,
                    title = "Vital Signs & Bedside Findings",
                    shortTitle = "Vitals",
                    iconType = "vitals",
                    description = "Significant hypoxemia refractory to room air, profound sinus tachycardia.",
                    details = listOf(
                        "Oxygen Delivery" to "Improved to 94% on 4 L/min nasal cannula",
                        "Bedside ECG" to "Sinus tachycardia at 116 bpm, S1Q3T3 pattern (deep S wave in lead I, Q wave and inverted T in lead III)"
                    ),
                    vitals = listOf(
                        VitalItem("Heart Rate", "116", "bpm (Sinus tachycardia)", ParamStatus.HIGH),
                        VitalItem("Respiratory Rate", "28", "breaths/min (Tachypnea)", ParamStatus.HIGH),
                        VitalItem("SpO2", "88", "% on room air", ParamStatus.LOW),
                        VitalItem("Blood Pressure", "102/64", "mmHg", ParamStatus.NORMAL),
                        VitalItem("Temperature", "37.4", "°C", ParamStatus.NORMAL)
                    )
                ),
                CaseTab(
                    id = 6,
                    title = "Laboratory Diagnostics",
                    shortTitle = "Labs",
                    iconType = "labs",
                    description = "Marked elevation of fibrin degradation products and arterial blood gas demonstrating acute respiratory alkalosis.",
                    details = listOf(
                        "Arterial Blood Gas (ABG)" to "pH 7.49, PaCO2 30 mmHg (hyperventilation), PaO2 58 mmHg, A-a gradient widened at 38 mmHg",
                        "Troponin I" to "0.08 ng/mL (Mild secondary myocardial strain)",
                        "BNP" to "280 pg/mL (Mild right ventricular strain elevation)"
                    ),
                    labs = listOf(
                        LabItem("D-Dimer (FEU)", "4,200", "ng/mL", "< 500", ParamStatus.HIGH),
                        LabItem("White Blood Cells", "9.6", "x10^9/L", "4.5 - 11.0", ParamStatus.NORMAL),
                        LabItem("Hemoglobin", "12.1", "g/dL", "12.0 - 15.5", ParamStatus.NORMAL),
                        LabItem("Platelet Count", "260", "x10^9/L", "150 - 400", ParamStatus.NORMAL),
                        LabItem("Serum Lactate", "1.8", "mmol/L", "0.5 - 2.0", ParamStatus.NORMAL)
                    )
                ),
                CaseTab(
                    id = 7,
                    title = "Imaging & Confirmatory Tests",
                    shortTitle = "CT-PA",
                    iconType = "imaging",
                    description = "Contrast CT Pulmonary Angiography (CT-PA) demonstrates saddle/lobar arterial occlusion.",
                    radiology = RadiologyReport(
                        modality = "CT Pulmonary Angiography (CT-PA)",
                        projection = "Volumetric Thin-Slice Axial with Multiplanar Reconstructions",
                        findings = listOf(
                            "Main Pulmonary Trunk" to "Pulmonary trunk measures 31 mm (mild pulmonary hypertension). Intraluminal filling defect at bifurcation.",
                            "Right Pulmonary Artery" to "Extensive occlusive embolus in main right pulmonary artery extending into descending lobar branches.",
                            "Right Ventricle" to "Right ventricular dilatation with RV/LV ratio of 1.3 (>1.0 indicates right heart strain). Interventricular septum flattening.",
                            "Lung Fields" to "Peripheral wedge-shaped pleural-based opacity at right lateral lung base consistent with pulmonary infarction (Hampton's hump)."
                        ),
                        impression = "Acute high-risk submassive Pulmonary Embolism with right ventricular strain and associated pulmonary infarction. Urgent anticoagulation indicated.",
                        keySigns = listOf("Intraluminal Filling Defect", "RV/LV Ratio >1.0", "Hampton's Hump", "Westermark Sign"),
                        isUrgent = true
                    ),
                    details = listOf(
                        "Chest Radiograph (CXR)" to "Near normal lung parenchyma with subtle wedge-shaped peripheral density at the right lateral base (Hampton's hump)",
                        "CT Pulmonary Angiography" to "Extensive occlusive intraluminal filling defects in the main right pulmonary artery extending into the right middle and lower lobe branches; RV/LV diameter ratio > 1.0 (RV strain)",
                        "Lower Extremity Venous US" to "Non-compressible left popliteal and superficial femoral vein with echogenic intraluminal thrombus"
                    ),
                    diagnosticNote = "Definitive Diagnostic Finding: Acute massive-submassive Pulmonary Thromboembolism originating from left lower extremity Deep Vein Thrombosis."
                )
            ),
            correctDiagnosis = "Pulmonary Embolism",
            acceptedAliases = listOf(
                "pulmonary embolism",
                "pe",
                "pulmonary thromboembolism",
                "acute pulmonary embolism",
                "pulmonary embolus"
            ),
            clinicalPearls = "Wells score in this patient was high (>6: clinical signs of DVT, tachycardia >100, immobilization/recent surgery, lack of better alternative diagnosis). Hallmarks include acute pleuritic pain, sudden hypoxemia with widened A-a gradient, S1Q3T3 on ECG, and filling defects on CT-PA.",
            differentialDiagnoses = listOf("Acute Myocardial Infarction", "Tension Pneumothorax", "Aortic Dissection", "Pneumonia", "Pericarditis")
        ),

        // CASE 3: Diabetic Ketoacidosis (DKA)
        MedicalCase(
            id = "case_03",
            caseNumber = 103,
            patientProfile = "19-year-old female",
            chiefComplaintHeadline = "Profound fatigue, diffuse abdominal pain, nausea, and deep rapid breathing",
            specialty = "Endocrinology / Acute Medicine",
            difficulty = "Core Clinical Case",
            tabs = listOf(
                CaseTab(
                    id = 1,
                    title = "Chief Complaint & Triage",
                    shortTitle = "Complaint",
                    iconType = "triage",
                    description = "A 19-year-old college student is brought in by roommates after 2 days of progressive weakness, excessive thirst, and repeated bouts of vomiting.",
                    details = listOf(
                        "Presenting Complaints" to "Severe lethargy, intractable nausea, unquenchable thirst",
                        "Onset" to "Progressive over 48-72 hours following a viral gastrointestinal bug",
                        "Mental Status" to "Somnolent but rousable, oriented to name and city only",
                        "Odor" to "Distinct sweet, fruity (acetone) breath noticed by nurse"
                    ),
                    diagnosticNote = "Triage: Noticeably hyperventilating with deep, regular sighs (Kussmaul respiration)."
                ),
                CaseTab(
                    id = 2,
                    title = "History of Present Illness (HPI)",
                    shortTitle = "HPI",
                    iconType = "history",
                    description = "Started with polydipsia and nocturia 5 days ago. Stopped eating solids 2 days ago due to nausea. Mistakenly omitted her daily insulin because she was not eating.",
                    details = listOf(
                        "Fluid Balance" to "Drinking 4-5 liters of water daily; voiding large volumes every hour",
                        "Abdominal Pain" to "Generalized dull ache across all quadrants; no radiation",
                        "Weight Loss" to "Noticed 4 kg weight loss over the past 2 weeks",
                        "Insulin Adherence" to "Omitted her basal insulin injection for the past 36 hours thinking 'no food means no insulin'"
                    )
                ),
                CaseTab(
                    id = 3,
                    title = "Past Medical History & Meds",
                    shortTitle = "PMH",
                    iconType = "medical_history",
                    description = "Diagnosed with autoimmune endocrine disorder 3 years ago.",
                    details = listOf(
                        "Past Medical History" to "Type 1 Diabetes Mellitus (diagnosed age 16), Hashimoto's thyroiditis",
                        "Prescribed Medications" to "Insulin Glargine 22 units nightly; Insulin Lispro 1 unit per 10g carbohydrate with meals; Levothyroxine 75 mcg daily",
                        "Allergies" to "NKDA",
                        "Recent Events" to "Upper respiratory viral prodrome with low-grade fever 4 days ago"
                    )
                ),
                CaseTab(
                    id = 4,
                    title = "Physical Examination",
                    shortTitle = "Exam",
                    iconType = "physical_exam",
                    description = "Signs of profound intravascular dehydration and metabolic compensation.",
                    details = listOf(
                        "General" to "Flushed face, sunken eyes, dry oral mucous membranes, poor skin turgor",
                        "Respiratory" to "Hyperpnea with deep, rapid Kussmaul respirations; clear lung fields bilaterally",
                        "Abdomen" to "Diffuse mild epigastric and periumbilical tenderness without peritoneal rebound or involuntary rigidity",
                        "Neurological" to "Glasgow Coma Scale 13 (E4, V4, M5); sluggish deep tendon reflexes"
                    )
                ),
                CaseTab(
                    id = 5,
                    title = "Vital Signs & Bedside Findings",
                    shortTitle = "Vitals",
                    iconType = "vitals",
                    description = "Orthostatic hypotension, sinus tachycardia, and hyperventilation.",
                    details = listOf(
                        "Bedside Glucometer" to "> 500 mg/dL ('HIGH' reading)",
                        "Bedside Urine Dipstick" to "Ketones ++++ (strong purple), Glucose ++++, Protein negative"
                    ),
                    vitals = listOf(
                        VitalItem("Blood Glucose", "542", "mg/dL", ParamStatus.HIGH),
                        VitalItem("Heart Rate", "122", "bpm", ParamStatus.HIGH),
                        VitalItem("Blood Pressure", "92/58", "mmHg (Orthostatic drop to 82/50)", ParamStatus.LOW),
                        VitalItem("Respiratory Rate", "30", "breaths/min (Deep Kussmaul)", ParamStatus.HIGH),
                        VitalItem("Temperature", "37.1", "°C", ParamStatus.NORMAL),
                        VitalItem("SpO2", "98", "% on room air", ParamStatus.NORMAL)
                    )
                ),
                CaseTab(
                    id = 6,
                    title = "Laboratory Diagnostics",
                    shortTitle = "Labs",
                    iconType = "labs",
                    description = "Severe high anion gap metabolic acidosis with pseudohyponatremia and marked ketonemia.",
                    details = listOf(
                        "Venous Blood Gas (VBG)" to "pH 7.12, HCO3- 8.2 mmol/L, pCO2 24 mmHg",
                        "Serum Beta-hydroxybutyrate" to "6.8 mmol/L (Normal < 0.5 mmol/L)",
                        "Calculated Anion Gap" to "28 mEq/L (Na - (Cl + HCO3) = 132 - (96 + 8) = 28; Normal 8-12)"
                    ),
                    labs = listOf(
                        LabItem("Serum Glucose", "542", "mg/dL", "70 - 100", ParamStatus.HIGH),
                        LabItem("Sodium", "132", "mEq/L (Corrected: 139)", "135 - 145", ParamStatus.LOW),
                        LabItem("Potassium", "5.4", "mEq/L (Shifting out of cells)", "3.5 - 5.0", ParamStatus.HIGH),
                        LabItem("Chloride", "96", "mEq/L", "96 - 106", ParamStatus.NORMAL),
                        LabItem("Bicarbonate", "8.2", "mEq/L", "22 - 29", ParamStatus.LOW),
                        LabItem("BUN", "38", "mg/dL (Prerenal azotemia)", "7 - 20", ParamStatus.HIGH)
                    )
                ),
                CaseTab(
                    id = 7,
                    title = "Imaging & Confirmatory Tests",
                    shortTitle = "Imaging",
                    iconType = "imaging",
                    description = "Chest radiograph and ECG exclude acute occult infection or ischemic trigger.",
                    details = listOf(
                        "Electrocardiogram (ECG)" to "Sinus tachycardia, peaked T waves in leads V2-V4 secondary to extracellular hyperkalemia; no ST elevations",
                        "Chest Radiograph" to "No focal consolidations or infiltrate; heart size normal; clear costophrenic angles",
                        "Toxicology Screen" to "Ethanol negative; salicylates and acetaminophen undetectable"
                    ),
                    diagnosticNote = "Definitive Diagnostic Finding: Triad of Hyperglycemia (>500 mg/dL), High Anion Gap Acidosis (pH 7.12, HCO3 8), and marked Ketonemia/Ketonuria."
                )
            ),
            correctDiagnosis = "Diabetic Ketoacidosis",
            acceptedAliases = listOf(
                "diabetic ketoacidosis",
                "dka",
                "ketoacidosis",
                "diabetic acidosis",
                "type 1 diabetes ketoacidosis"
            ),
            clinicalPearls = "Characterized by absolute insulin deficiency, precipitating lipolysis and hepatic ketogenesis. Hallmarks are hyperglycemia, hyperketonemia (beta-hydroxybutyrate), high anion gap metabolic acidosis, and compensatory Kussmaul respirations. Even with high serum K+, total body potassium is depleted.",
            differentialDiagnoses = listOf("Hyperosmolar Hyperglycemic State (HHS)", "Starvation Ketoacidosis", "Alcoholic Ketoacidosis", "Lactic Acidosis", "Salicylate Toxicity")
        ),

        // CASE 4: Acute Cholecystitis
        MedicalCase(
            id = "case_04",
            caseNumber = 104,
            patientProfile = "46-year-old female",
            chiefComplaintHeadline = "Severe steady right upper quadrant pain radiating to right scapula following a fried dinner",
            specialty = "Gastroenterology / Surgery",
            difficulty = "Core Clinical Case",
            tabs = listOf(
                CaseTab(
                    id = 1,
                    title = "Chief Complaint & Triage",
                    shortTitle = "Complaint",
                    iconType = "triage",
                    description = "A 46-year-old female presents to the Emergency Department at 2 AM with severe persistent pain beneath her right ribcage.",
                    details = listOf(
                        "Presenting Symptom" to "Right upper quadrant (RUQ) abdominal pain",
                        "Onset" to "Began 6 hours ago, 1 hour after eating fried chicken",
                        "Intensity" to "8/10, constant and unremitting (not colicky)",
                        "Associated Symptoms" to "Nausea, three episodes of bilious vomiting, subjective chills"
                    ),
                    diagnosticNote = "Triage: Patient lies still, as deep inspirations exacerbate her subcostal pain."
                ),
                CaseTab(
                    id = 2,
                    title = "History of Present Illness (HPI)",
                    shortTitle = "HPI",
                    iconType = "history",
                    description = "Pain radiates to the right infrascapular area and right shoulder tip. She has had shorter 1-hour episodes of post-prandial cramping in the past, but this episode failed to resolve.",
                    details = listOf(
                        "Radiation" to "Right infrascapular angle and right shoulder (phrenic nerve irritation)",
                        "Prior Episodes" to "Multiple episodes over past 6 months lasting 45-90 minutes (biliary colic), resolving spontaneously",
                        "Current Episode" to "Has lasted over 6 hours continuously, accompanied by feverish feeling",
                        "Review of Systems" to "No acholic (pale) stools, no tea-colored dark urine, no generalized pruritus"
                    )
                ),
                CaseTab(
                    id = 3,
                    title = "Past Medical History & Meds",
                    shortTitle = "PMH",
                    iconType = "medical_history",
                    description = "Multipara female with mild dyslipidemia.",
                    details = listOf(
                        "Obstetric History" to "Gravida 4, Para 4 (4 children delivered vaginally)",
                        "Past Medical History" to "Hypercholesterolemia, BMI 32 kg/m²",
                        "Current Medications" to "Atorvastatin 20mg nightly, oral contraceptive pills",
                        "Allergies" to "NKDA",
                        "Social History" to "Non-smoker, drinks 1-2 glasses of wine weekly"
                    )
                ),
                CaseTab(
                    id = 4,
                    title = "Physical Examination",
                    shortTitle = "Exam",
                    iconType = "physical_exam",
                    description = "Exquisite focal tenderness in the right hypochondrium with inspiratory arrest.",
                    details = listOf(
                        "Abdomen" to "Soft, nondistended; palpation in right subcostal margin during deep inspiration results in sudden gasp and halt in inspiration (Positive Murphy's Sign)",
                        "Involuntary Guarding" to "Localized voluntary and mild involuntary guarding in right upper quadrant",
                        "Sclera / Mucosa" to "Anicteric sclerae; moist pink mucosa",
                        "Skin" to "No jaundice, no spider angiomas, warm and dry"
                    )
                ),
                CaseTab(
                    id = 5,
                    title = "Vital Signs & Bedside Findings",
                    shortTitle = "Vitals",
                    iconType = "vitals",
                    description = "Mild pyrexia and sinus tachycardia reflecting acute gallbladder inflammation.",
                    details = listOf(
                        "Pain Score" to "7 / 10 at rest",
                        "Bedside Point-of-Care US" to "Gallbladder visualized with sonographic Murphy's sign reproduced by probe pressure"
                    ),
                    vitals = listOf(
                        VitalItem("Temperature", "38.4", "°C (101.1°F)", ParamStatus.HIGH),
                        VitalItem("Heart Rate", "102", "bpm", ParamStatus.HIGH),
                        VitalItem("Blood Pressure", "136/84", "mmHg", ParamStatus.NORMAL),
                        VitalItem("Respiratory Rate", "18", "breaths/min", ParamStatus.NORMAL),
                        VitalItem("SpO2", "99", "% on room air", ParamStatus.NORMAL)
                    )
                ),
                CaseTab(
                    id = 6,
                    title = "Laboratory Diagnostics",
                    shortTitle = "Labs",
                    iconType = "labs",
                    description = "Leukocytosis with bandemia; normal to borderline liver enzymes preserving biliary patency.",
                    details = listOf(
                        "Lipase" to "32 U/L (Normal 10-140 U/L, ruling out acute pancreatitis)",
                        "Alkaline Phosphatase (ALP)" to "110 U/L (Normal 44-147 U/L, no ductal obstruction)",
                        "Total Bilirubin" to "1.3 mg/dL (Normal 0.2 - 1.2 mg/dL, mild reactive elevation)"
                    ),
                    labs = listOf(
                        LabItem("WBC", "14.2", "x10^9/L", "4.5 - 11.0", ParamStatus.HIGH),
                        LabItem("Neutrophils", "82", "%", "40 - 70", ParamStatus.HIGH),
                        LabItem("ALT (SGPT)", "38", "U/L", "7 - 56", ParamStatus.NORMAL),
                        LabItem("AST (SGOT)", "34", "U/L", "10 - 40", ParamStatus.NORMAL),
                        LabItem("CRP", "38", "mg/L", "< 5.0", ParamStatus.HIGH)
                    )
                ),
                CaseTab(
                    id = 7,
                    title = "Imaging & Confirmatory Tests",
                    shortTitle = "Imaging",
                    iconType = "imaging",
                    description = "Right upper quadrant ultrasound demonstrates classic features of cystic duct impaction and cholecystitis.",
                    details = listOf(
                        "Gallbladder Wall" to "Thickened at 5.2 mm (normal < 3 mm) with wall edema/stratification",
                        "Cholelithiasis" to "Multiple mobile echogenic foci with posterior acoustic shadowing; a 1.4 cm gallstone impacted in the neck of the gallbladder/cystic duct",
                        "Pericholecystic Fluid" to "Anechoic rim of pericholecystic fluid present",
                        "Common Bile Duct (CBD)" to "Diameter 4.5 mm (normal < 6 mm); no choledocholithiasis or intrahepatic duct dilatation"
                    ),
                    diagnosticNote = "Definitive Diagnostic Finding: Acute calculous cholecystitis with impacted neck stone, wall thickening >5mm, pericholecystic fluid, and positive sonographic Murphy's sign."
                )
            ),
            correctDiagnosis = "Acute Cholecystitis",
            acceptedAliases = listOf(
                "acute cholecystitis",
                "cholecystitis",
                "calculous cholecystitis",
                "gallbladder infection",
                "acute calculous cholecystitis"
            ),
            clinicalPearls = "Classic 4 'F's risk factors (Female, Fat, Forty, Fertile). Unlike transient biliary colic (<4-6 hours), acute cholecystitis is persistent inflammation caused by cystic duct obstruction. Hallmark physical finding is Murphy's sign, confirmed by RUQ ultrasound showing wall thickening >4mm and pericholecystic fluid.",
            differentialDiagnoses = listOf("Biliary Colic", "Acute Cholangitis", "Acute Pancreatitis", "Perforated Peptic Ulcer", "Right Lower Lobe Pneumonia")
        ),

        // CASE 5: Acute Appendicitis
        MedicalCase(
            id = "case_05",
            caseNumber = 105,
            patientProfile = "22-year-old male",
            chiefComplaintHeadline = "Migrating abdominal pain and anorexia for 20 hours",
            specialty = "General Surgery / Emergency",
            difficulty = "Core Clinical Case",
            tabs = listOf(
                CaseTab(
                    id = 1,
                    title = "Chief Complaint & Triage",
                    shortTitle = "Complaint",
                    iconType = "triage",
                    description = "A 22-year-old college student presents to the Emergency Department with progressively worsening abdominal pain that began yesterday evening.",
                    details = listOf(
                        "Presenting Symptom" to "Abdominal pain, dull and constant",
                        "Onset" to "20 hours ago, gradually escalating in intensity",
                        "Severity" to "7/10 at rest, 9/10 with bumps in wheelchair",
                        "Associated Symptoms" to "Marked anorexia, low-grade nausea, one episode of non-bilious emesis"
                    ),
                    diagnosticNote = "Triage note: Patient walks curled forward, guarding his abdomen with every step."
                ),
                CaseTab(
                    id = 2,
                    title = "History of Present Illness (HPI)",
                    shortTitle = "HPI",
                    iconType = "history",
                    description = "Pain began periumbilically as an aching, non-localized discomfort. Over the past 8 hours, it migrated to the right lower quadrant and became sharp and focal.",
                    details = listOf(
                        "Pain Character" to "Initially vague periumbilical cramping; migrated to right lower quadrant",
                        "Radiation" to "No radiation to flank, back, or groin",
                        "Aggravating Factors" to "Coughing, walking, laughing, deep breaths",
                        "Alleviating Factors" to "Lying still with right hip flexed",
                        "GI Review" to "No diarrhea; last bowel movement 2 days ago; unable to eat dinner (hamburger sign positive)"
                    )
                ),
                CaseTab(
                    id = 3,
                    title = "Past Medical History & Meds",
                    shortTitle = "PMH",
                    iconType = "medical_history",
                    description = "Generally healthy young adult with no major chronic medical conditions.",
                    details = listOf(
                        "Past Medical History" to "Childhood asthma (well-controlled, no recent flares)",
                        "Surgical History" to "None (has not had prior abdominal operations)",
                        "Current Medications" to "Albuterol inhaler PRN (not used in 6 months), took Acetaminophen 500mg 4 hours ago with zero relief",
                        "Allergies" to "No known drug allergies (NKDA)",
                        "Social History" to "Non-smoker, occasional social alcohol on weekends, college athlete"
                    )
                ),
                CaseTab(
                    id = 4,
                    title = "Physical Examination",
                    shortTitle = "Exam",
                    iconType = "physical_exam",
                    description = "Focused abdominal exam reveals localized peritoneal irritation in the right iliac fossa.",
                    details = listOf(
                        "General Appearance" to "Uncomfortable, lying on right side with knees pulled up",
                        "Abdomen" to "Focal tenderness and involuntary muscle guarding at McBurney's point (1/3 from ASIS to umbilicus)",
                        "Rovsing's Sign" to "Positive: palpation of left lower quadrant reproduces pain in right lower quadrant",
                        "Psoas Sign" to "Positive: passive extension of right hip causes sharp RLQ pain",
                        "Obturator Sign" to "Equivocal / mild right pelvic discomfort upon internal rotation of flexed right hip",
                        "Bowel Sounds" to "Hypoactive in all four quadrants"
                    )
                ),
                CaseTab(
                    id = 5,
                    title = "Vital Signs & Bedside Findings",
                    shortTitle = "Vitals",
                    iconType = "vitals",
                    description = "Bedside examination demonstrates low-grade pyrexia and tachycardia consistent with localized infection.",
                    details = listOf(
                        "Pain Scale" to "8 / 10 in RLQ",
                        "Capillary Refill" to "< 2 seconds"
                    ),
                    vitals = listOf(
                        VitalItem("Temperature", "38.2", "°C (100.8°F)", ParamStatus.HIGH),
                        VitalItem("Heart Rate", "104", "bpm (Sinus tachycardia)", ParamStatus.HIGH),
                        VitalItem("Blood Pressure", "124/78", "mmHg", ParamStatus.NORMAL),
                        VitalItem("Respiratory Rate", "18", "breaths/min", ParamStatus.NORMAL),
                        VitalItem("SpO2", "99", "% on ambient room air", ParamStatus.NORMAL)
                    )
                ),
                CaseTab(
                    id = 6,
                    title = "Laboratory Diagnostics",
                    shortTitle = "Labs",
                    iconType = "labs",
                    description = "Blood analysis reveals marked polymorphonuclear leukocytosis and elevated inflammatory markers.",
                    details = listOf(
                        "Urinalysis" to "Clear yellow, trace ketones, 2-3 WBCs/hpf, negative for nitrites and leukocyte esterase (rules out acute UTI)"
                    ),
                    labs = listOf(
                        LabItem("White Blood Cells (WBC)", "15.8", "x10^9/L", "4.5 - 11.0", ParamStatus.HIGH),
                        LabItem("Neutrophil %", "86", "% (Left shift)", "40 - 70", ParamStatus.HIGH),
                        LabItem("C-Reactive Protein (CRP)", "42", "mg/L", "< 5.0", ParamStatus.HIGH),
                        LabItem("Hemoglobin", "14.8", "g/dL", "13.5 - 17.5", ParamStatus.NORMAL),
                        LabItem("Serum Creatinine", "0.9", "mg/dL", "0.7 - 1.2", ParamStatus.NORMAL),
                        LabItem("Total Bilirubin", "0.7", "mg/dL", "0.2 - 1.2", ParamStatus.NORMAL)
                    )
                ),
                CaseTab(
                    id = 7,
                    title = "Imaging & Confirmatory Tests",
                    shortTitle = "Imaging",
                    iconType = "imaging",
                    description = "Graded compression ultrasound and IV contrast-enhanced abdominopelvic CT confirm appendiceal inflammation.",
                    details = listOf(
                        "Abdominal Ultrasound" to "Non-compressible, blind-ending tubular structure in the right lower quadrant measuring 8.5 mm in outer diameter",
                        "CT Abdomen & Pelvis" to "Dilated appendix (>8 mm) with hyperenhancing, thickened wall, periappendiceal fat stranding, and a calcified appendicolith at the base",
                        "Free Fluid" to "Small volume of reactive fluid in the right paracolic gutter; no retroperitoneal air or perforation seen"
                    ),
                    radiology = RadiologyReport(
                        modality = "CT Abdomen & Pelvis with IV Contrast",
                        projection = "Axial & Coronal Contrast-Enhanced Reconstruction",
                        findings = listOf(
                            "Appendix" to "Thickened, aperistaltic, blind-ending tubular structure measuring 8.5 mm in outer diameter.",
                            "Appendicolith" to "Dense calcified appendicolith (fecalith) lodged in proximal appendiceal lumen.",
                            "Surrounding Tissue" to "Marked inflammatory fat stranding in the right lower quadrant and pericecal region.",
                            "Perforation / Collection" to "No extraluminal free air; no organized abscess collection identified."
                        ),
                        impression = "Acute uncomplicated appendicitis with luminal appendicolith and localized periappendiceal inflammation. Recommend urgent surgical consultation for appendectomy.",
                        keySigns = listOf("Target Sign", "Periappendiceal Fat Stranding", "Calcified Appendicolith", "Diameter >6mm"),
                        isUrgent = true
                    ),
                    diagnosticNote = "Definitive Diagnostic Finding: Acute luminal obstruction by appendicolith leading to transmural inflammation and localized peritonitis."
                )
            ),
            correctDiagnosis = "Acute Appendicitis",
            acceptedAliases = listOf(
                "appendicitis",
                "acute appendicitis",
                "appendicular peritonitis",
                "acute inflamed appendix"
            ),
            clinicalPearls = "Classic presentation begins with visceral periumbilical pain (T10 dermatome via autonomic fibers) followed by somatic right lower quadrant focal pain (parietal peritoneal irritation) after 12-24 hours. McBurney's sign, Rovsing's sign, and leukocytosis with left shift are hallmarks.",
            differentialDiagnoses = listOf("Mesenteric Adenitis", "Meckel's Diverticulitis", "Crohn's Disease (Terminal Ileitis)", "Nephrolithiasis", "Testicular Torsion")
        ),

        // CASE 6: Bacterial Meningitis
        MedicalCase(
            id = "case_06",
            caseNumber = 106,
            patientProfile = "24-year-old male",
            chiefComplaintHeadline = "Excruciating headache, high fever, photophobia, and severe neck stiffness",
            specialty = "Infectious Disease / Neurology",
            difficulty = "High-Yield Clinical Case",
            tabs = listOf(
                CaseTab(
                    id = 1,
                    title = "Chief Complaint & Triage",
                    shortTitle = "Complaint",
                    iconType = "triage",
                    description = "A 24-year-old military recruit is brought to the ED by colleagues due to acute disorientation, severe headache, and intolerance to light.",
                    details = listOf(
                        "Presenting Triad" to "High fever, neck stiffness, and progressive lethargy",
                        "Onset" to "Severe headache began 16 hours ago, rapidly worsening",
                        "Photophobia" to "Shielding eyes from room lighting; requests lights turned off immediately",
                        "Emesis" to "Multiple episodes of projectile vomiting"
                    ),
                    diagnosticNote = "Triage: Patient lies curled up in bed facing away from all light sources."
                ),
                CaseTab(
                    id = 2,
                    title = "History of Present Illness (HPI)",
                    shortTitle = "HPI",
                    iconType = "history",
                    description = "Headache is holocephalic, throbbing, and described as the 'worst of his life'. Complains of sharp pain whenever bending his neck forward.",
                    details = listOf(
                        "Timeline" to "Felt mildly unwell with sore throat yesterday, followed by sudden shivers and spiking temperature",
                        "Cognitive State" to "Colleagues noticed he became confused about room numbers and forgot breakfast",
                        "Rash" to "Noticed tiny red pinpoint spots appearing on his lower extremities in the last 2 hours",
                        "Contacts" to "Lives in crowded military barracks"
                    )
                ),
                CaseTab(
                    id = 3,
                    title = "Past Medical History & Meds",
                    shortTitle = "PMH",
                    iconType = "medical_history",
                    description = "No prior chronic medical conditions; missed scheduled booster immunizations.",
                    details = listOf(
                        "Immunization Status" to "Missed adolescent Meningococcal ACWY/B boosters due to overseas travel",
                        "Medical History" to "Unremarkable; active athlete",
                        "Medications" to "None",
                        "Allergies" to "NKDA"
                    )
                ),
                CaseTab(
                    id = 4,
                    title = "Physical Examination",
                    shortTitle = "Exam",
                    iconType = "physical_exam",
                    description = "Prominent meningeal signs and non-blanching cutaneous lesions.",
                    details = listOf(
                        "Meningeal Irritation" to "Marked nuchal rigidity (inability to flex chin to chest without severe pain)",
                        "Brudzinski's Sign" to "Positive: passive flexion of patient's neck elicits involuntary flexion of knees and hips",
                        "Kernig's Sign" to "Positive: with hip flexed at 90 degrees, passive extension of the knee beyond 135 degrees is severely restricted by hamstring spasm and back pain",
                        "Dermatology" to "Scattered non-blanching petechial and purpuric macules on lower shins, ankles, and flanks (petechial rash)",
                        "Fundoscopy" to "No papilledema seen (safe for lumbar puncture)"
                    )
                ),
                CaseTab(
                    id = 5,
                    title = "Vital Signs & Bedside Findings",
                    shortTitle = "Vitals",
                    iconType = "vitals",
                    description = "High septic fever, tachycardia, borderline perfusion.",
                    details = listOf(
                        "Glasgow Coma Scale" to "12 (E3, V4, M5)",
                        "Glass Test" to "Petechial rash does not fade or blanch under glass tumbler pressure"
                    ),
                    vitals = listOf(
                        VitalItem("Temperature", "39.6", "°C (103.3°F)", ParamStatus.HIGH),
                        VitalItem("Heart Rate", "128", "bpm (Sinus tachycardia)", ParamStatus.HIGH),
                        VitalItem("Blood Pressure", "98/58", "mmHg", ParamStatus.LOW),
                        VitalItem("Respiratory Rate", "22", "breaths/min", ParamStatus.HIGH),
                        VitalItem("SpO2", "97", "% on room air", ParamStatus.NORMAL)
                    )
                ),
                CaseTab(
                    id = 6,
                    title = "Laboratory Diagnostics",
                    shortTitle = "Labs",
                    iconType = "labs",
                    description = "Severe systemic leukocytosis and systemic inflammatory response.",
                    details = listOf(
                        "Serum Lactate" to "3.4 mmol/L (Elevated, indicating hypoperfusion)",
                        "Procalcitonin" to "18.5 ng/mL (Markedly elevated, specific for severe bacterial infection)",
                        "Blood Cultures" to "Drawn prior to antibiotics, pending incubation"
                    ),
                    labs = listOf(
                        LabItem("WBC", "21.4", "x10^9/L", "4.5 - 11.0", ParamStatus.HIGH),
                        LabItem("Neutrophils", "92", "% (Left shift)", "40 - 70", ParamStatus.HIGH),
                        LabItem("CRP", "185", "mg/L", "< 5.0", ParamStatus.HIGH),
                        LabItem("Platelets", "135", "x10^9/L (Consumptive)", "150 - 400", ParamStatus.LOW),
                        LabItem("Serum Glucose", "110", "mg/dL", "70 - 100", ParamStatus.NORMAL)
                    )
                ),
                CaseTab(
                    id = 7,
                    title = "Imaging & Confirmatory Tests",
                    shortTitle = "Imaging",
                    iconType = "imaging",
                    description = "Emergency Lumbar Puncture (LP) cerebrospinal fluid analysis is diagnostic of acute bacterial infection.",
                    details = listOf(
                        "CSF Appearance" to "Turbid / cloudy with opening pressure 340 mm H2O (elevated)",
                        "CSF White Cell Count" to "2,400 /mm³ with 95% polymorphonuclear neutrophils (PMNs)",
                        "CSF Protein" to "240 mg/dL (Markedly elevated, normal 15-45)",
                        "CSF Glucose" to "18 mg/dL (CSF to serum glucose ratio 0.16; severely depressed < 0.4)",
                        "CSF Gram Stain" to "Abundant intracellular and extracellular Gram-negative diplococci (Neisseria meningitidis)"
                    ),
                    diagnosticNote = "Definitive Diagnostic Finding: Acute Bacterial Meningitis (Meningococcal Meningitis) with turbid CSF, neutrophil predominance, low glucose, and Gram-negative diplococci."
                )
            ),
            correctDiagnosis = "Bacterial Meningitis",
            acceptedAliases = listOf(
                "bacterial meningitis",
                "meningitis",
                "meningococcal meningitis",
                "acute bacterial meningitis",
                "meningococcemia"
            ),
            clinicalPearls = "Classic clinical triad: fever, altered mental status, and nuchal rigidity (present in >95% of patients with at least 2 of 4 signs including headache). Kernig's and Brudzinski's signs confirm meningeal irritation. CSF shows high opening pressure, elevated neutrophils, elevated protein, and low CSF/serum glucose ratio (<0.4).",
            differentialDiagnoses = listOf("Viral Meningoencephalitis", "Subarachnoid Hemorrhage", "Brain Abscess", "Fungal / Tubercular Meningitis", "Sepsis secondary to Pyelonephritis")
        ),

        // CASE 7: Acute Pancreatitis
        MedicalCase(
            id = "case_07",
            caseNumber = 107,
            patientProfile = "49-year-old male",
            chiefComplaintHeadline = "Boring epigastric pain radiating straight to the mid-back, partially relieved by bending forward",
            specialty = "Gastroenterology / Internal Medicine",
            difficulty = "Core Clinical Case",
            tabs = listOf(
                CaseTab(
                    id = 1,
                    title = "Chief Complaint & Triage",
                    shortTitle = "Complaint",
                    iconType = "triage",
                    description = "A 49-year-old man presents with intense, agonizing mid-epigastric abdominal pain and repeated dry heaving that started 12 hours ago.",
                    details = listOf(
                        "Presenting Symptom" to "Agonizing upper abdominal pain, constant and deep",
                        "Onset" to "Began rapidly 12 hours ago after an evening banquet",
                        "Severity" to "10/10, unrelenting",
                        "Relieving Position" to "Leaning forward in a tripod position offers slight comfort"
                    ),
                    diagnosticNote = "Triage: Patient sits bent over his knees on the stretcher, groaning in distress."
                ),
                CaseTab(
                    id = 2,
                    title = "History of Present Illness (HPI)",
                    shortTitle = "HPI",
                    iconType = "history",
                    description = "The pain is described as a 'boring, piercing' drill sensation passing straight through the epigastrium to the thoracic spine (T10-T12).",
                    details = listOf(
                        "Radiation" to "Piercing band-like radiation straight through to the back",
                        "Aggravating Factors" to "Lying flat on his back (supine) makes pain intolerable; eating/drinking worsens nausea immediately",
                        "Emesis" to "Profuse retching and vomiting without relief of abdominal pain",
                        "Alcohol History" to "Reports drinking 6-8 beers daily for the past 15 years, with heavier binge drinking over the weekend"
                    )
                ),
                CaseTab(
                    id = 3,
                    title = "Past Medical History & Meds",
                    shortTitle = "PMH",
                    iconType = "medical_history",
                    description = "Longstanding history of alcohol consumption and tobacco use.",
                    details = listOf(
                        "Past Medical History" to "Hypertension, chronic alcohol use disorder, mild gastroesophageal reflux disease (GERD)",
                        "Medications" to "Omeprazole 20mg daily, Lisinopril 10mg daily",
                        "Allergies" to "NKDA",
                        "Social History" to "20 pack-year cigarette smoking, heavy alcohol intake"
                    )
                ),
                CaseTab(
                    id = 4,
                    title = "Physical Examination",
                    shortTitle = "Exam",
                    iconType = "physical_exam",
                    description = "Significant upper abdominal tenderness, hypoactive bowel sounds, and mild distension.",
                    details = listOf(
                        "General" to "Diaphoretic, distressed, leaning forward in bed",
                        "Abdomen" to "Severe tenderness to palpation in epigastrium and periumbilical regions; voluntary guarding without generalized board-like rigidity",
                        "Signs of Retroperitoneal Hemorrhage" to "Cullen's sign (periumbilical ecchymosis) and Grey Turner's sign (flank ecchymosis) are currently absent",
                        "Chest" to "Decreased breath sounds at left lung base (reactive sympathetic pleural effusion)"
                    )
                ),
                CaseTab(
                    id = 5,
                    title = "Vital Signs & Bedside Findings",
                    shortTitle = "Vitals",
                    iconType = "vitals",
                    description = "Tachycardia and low-grade inflammatory temperature due to retroperitoneal cytokine release.",
                    details = listOf(
                        "Fluid Resuscitation Need" to "Third-spacing of fluids into retroperitoneum",
                        "Capillary Refill" to "2.5 seconds, borderline"
                    ),
                    vitals = listOf(
                        VitalItem("Heart Rate", "112", "bpm", ParamStatus.HIGH),
                        VitalItem("Blood Pressure", "108/68", "mmHg", ParamStatus.NORMAL),
                        VitalItem("Temperature", "37.9", "°C", ParamStatus.HIGH),
                        VitalItem("Respiratory Rate", "22", "breaths/min", ParamStatus.HIGH),
                        VitalItem("SpO2", "96", "% on room air", ParamStatus.NORMAL)
                    )
                ),
                CaseTab(
                    id = 6,
                    title = "Laboratory Diagnostics",
                    shortTitle = "Labs",
                    iconType = "labs",
                    description = "Serum lipase elevation exceeding 8 times the upper limit of normal.",
                    details = listOf(
                        "Serum Lipase" to "1,180 U/L (Normal 10 - 140 U/L; > 3x upper limit is diagnostic)",
                        "Serum Amylase" to "640 U/L (Normal 30 - 110 U/L)",
                        "Triglycerides" to "185 mg/dL (Normal < 150 mg/dL, ruling out severe hypertriglyceridemic pancreatitis > 1,000 mg/dL)",
                        "Serum Calcium" to "8.1 mg/dL (Borderline low due to saponification)"
                    ),
                    labs = listOf(
                        LabItem("Serum Lipase", "1,180", "U/L", "10 - 140", ParamStatus.HIGH),
                        LabItem("WBC", "16.4", "x10^9/L", "4.5 - 11.0", ParamStatus.HIGH),
                        LabItem("Hematocrit", "47", "% (Hemoconcentration)", "40 - 52", ParamStatus.HIGH),
                        LabItem("ALT", "36", "U/L (Normal, rules out gallstone etiology)", "7 - 56", ParamStatus.NORMAL),
                        LabItem("BUN", "24", "mg/dL", "7 - 20", ParamStatus.HIGH)
                    )
                ),
                CaseTab(
                    id = 7,
                    title = "Imaging & Confirmatory Tests",
                    shortTitle = "Imaging",
                    iconType = "imaging",
                    description = "Contrast-enhanced abdominal CT demonstrates diffuse glandular enlargement and peripancreatic stranding.",
                    details = listOf(
                        "Abdominal Ultrasound" to "Pancreas obscured by overlying bowel gas; gallbladder without stones, normal common bile duct diameter (4 mm)",
                        "Contrast CT Abdomen" to "Diffuse swelling and enlargement of the pancreatic body and tail with hazy peripancreatic fat stranding and fluid collection; no necrotizing non-enhancing parenchymal areas",
                        "Balthazar Score" to "Grade C Acute Interstitial Edematous Pancreatitis",
                        "Left Pleural Space" to "Small sympathetic left-sided pleural effusion"
                    ),
                    diagnosticNote = "Definitive Diagnostic Finding: Acute Pancreatitis (Alcohol-induced interstitial edematous) meeting Atlanta Criteria (typical pain, lipase >3x ULN, CT changes)."
                )
            ),
            correctDiagnosis = "Acute Pancreatitis",
            acceptedAliases = listOf(
                "pancreatitis",
                "acute pancreatitis",
                "interstitial pancreatitis",
                "acute alcoholic pancreatitis",
                "edematous pancreatitis"
            ),
            clinicalPearls = "Atlanta classification requires 2 of 3 criteria: (1) Characteristic epigastric pain radiating to back, (2) Serum lipase or amylase >3x upper limit of normal, (3) Characteristic findings on contrast-enhanced CT or MRI. Lipase is more sensitive and specific than amylase.",
            differentialDiagnoses = listOf("Perforated Peptic Ulcer", "Acute Myocardial Infarction", "Aortic Dissection", "Biliary Colic / Cholecystitis", "Mesenteric Ischemia")
        ),

        // CASE 8: Guillain-Barré Syndrome
        MedicalCase(
            id = "case_08",
            caseNumber = 108,
            patientProfile = "34-year-old male",
            chiefComplaintHeadline = "Symmetrical ascending leg weakness and absent reflexes 2 weeks after diarrheal illness",
            specialty = "Neurology / Physical Medicine",
            difficulty = "Advanced Clinical Case",
            tabs = listOf(
                CaseTab(
                    id = 1,
                    title = "Chief Complaint & Triage",
                    shortTitle = "Complaint",
                    iconType = "triage",
                    description = "A 34-year-old software engineer presents with progressive difficulty standing up from a chair and stumbling while walking.",
                    details = listOf(
                        "Chief Complaint" to "Bilateral lower extremity weakness and tingling in toes",
                        "Onset" to "Began 4 days ago with numbness in feet; weakness has crept up to knees and thighs",
                        "Progression" to "Ascending symmetrically over the past 96 hours; now experiencing finger paresthesias",
                        "Bulbar Symptoms" to "Mild difficulty clearing throat; no diplopia"
                    ),
                    diagnosticNote = "Triage: Requires assistance of two nurses to transfer from wheelchair to bed."
                ),
                CaseTab(
                    id = 2,
                    title = "History of Present Illness (HPI)",
                    shortTitle = "HPI",
                    iconType = "history",
                    description = "Patient reports 'rubbery legs' and loss of balance. Denies prior episodes. Had a bout of bloody diarrhea and stomach cramps 14 days ago after a barbecue.",
                    details = listOf(
                        "Prior Infection" to "Acute diarrheal illness 2 weeks ago (Campylobacter jejuni suspected), resolved spontaneously after 4 days",
                        "Weakness Pattern" to "Symmetrical, starting distal (feet/legs), now involving proximal hip flexors and mild distal hand grip",
                        "Sensory Symptoms" to "Glove-and-stocking paresthesia ('pins and needles') in toes and fingertips",
                        "Autonomic" to "Reports mild palpitations and transient orthostatic lightheadedness"
                    )
                ),
                CaseTab(
                    id = 3,
                    title = "Past Medical History & Meds",
                    shortTitle = "PMH",
                    iconType = "medical_history",
                    description = "Previously healthy; marathon runner.",
                    details = listOf(
                        "Past Medical History" to "None",
                        "Surgical History" to "None",
                        "Medications" to "None",
                        "Allergies" to "NKDA"
                    )
                ),
                CaseTab(
                    id = 4,
                    title = "Physical Examination",
                    shortTitle = "Exam",
                    iconType = "physical_exam",
                    description = "Flaccid symmetrical quadriparesis with complete areflexia.",
                    details = listOf(
                        "Motor Strength" to "Lower extremities: Hip flexion 3/5, knee extension 3/5, ankle dorsiflexion 2/5 (bilaterally symmetrical); Upper extremities: 4/5 distal grip",
                        "Deep Tendon Reflexes" to "Patellar (L3-L4): 0/4 (Absent); Achilles (S1): 0/4 (Absent); Biceps/Brachioradialis: 1/4 (Markedly diminished)",
                        "Plantar Response" to "Bilateral downgoing (flexor / normal)",
                        "Sensory Exam" to "Mild loss of vibration and light touch sensation in distal toes bilaterally; intact pinprick on trunk",
                        "Cranial Nerves" to "Intact; mild bilateral facial weakness (unable to bury eyelashes completely)"
                    )
                ),
                CaseTab(
                    id = 5,
                    title = "Vital Signs & Bedside Findings",
                    shortTitle = "Vitals",
                    iconType = "vitals",
                    description = "Bedside spirometry monitoring for diaphragm involvement.",
                    details = listOf(
                        "Forced Vital Capacity (FVC)" to "2.2 L (Predicted 4.5 L, demonstrating declining inspiratory capacity)",
                        "Negative Inspiratory Force (NIF)" to "-28 cm H2O (Normal < -60; impaired)",
                        "Autonomic Stability" to "Sinus arrhythmia and labile blood pressure noted on cardiac monitor"
                    ),
                    vitals = listOf(
                        VitalItem("Heart Rate", "94", "bpm (Labile)", ParamStatus.NORMAL),
                        VitalItem("Blood Pressure", "144/92", "mmHg (Labile autonomic tone)", ParamStatus.HIGH),
                        VitalItem("Respiratory Rate", "18", "breaths/min", ParamStatus.NORMAL),
                        VitalItem("SpO2", "98", "% on room air", ParamStatus.NORMAL),
                        VitalItem("Temperature", "37.0", "°C", ParamStatus.NORMAL)
                    )
                ),
                CaseTab(
                    id = 6,
                    title = "Laboratory Diagnostics",
                    shortTitle = "Labs",
                    iconType = "labs",
                    description = "Electrolytes, thyroid, and muscle enzymes normal, excluding metabolic or inflammatory myopathy.",
                    details = listOf(
                        "Creatine Kinase (CK)" to "110 U/L (Normal, rules out polymyositis / rhabdomyolysis)",
                        "Serum Potassium" to "4.2 mEq/L (Normal, rules out hypokalemic periodic paralysis)",
                        "Campylobacter Serology" to "Positive IgG/IgM antibodies to Campylobacter jejuni"
                    ),
                    labs = listOf(
                        LabItem("WBC", "6.8", "x10^9/L", "4.5 - 11.0", ParamStatus.NORMAL),
                        LabItem("Hemoglobin", "14.4", "g/dL", "13.5 - 17.5", ParamStatus.NORMAL),
                        LabItem("ESR", "14", "mm/hr", "< 20", ParamStatus.NORMAL),
                        LabItem("TSH", "1.8", "mIU/L", "0.4 - 4.0", ParamStatus.NORMAL)
                    )
                ),
                CaseTab(
                    id = 7,
                    title = "Imaging & Confirmatory Tests",
                    shortTitle = "Imaging",
                    iconType = "imaging",
                    description = "Lumbar puncture demonstrates pathognomonic albuminocytologic dissociation; Nerve conduction study reveals acute demyelination.",
                    details = listOf(
                        "Cerebrospinal Fluid (LP)" to "Albuminocytologic Dissociation: CSF Protein elevated at 165 mg/dL (Normal 15-45) with normal CSF White Blood Cell count (2 cells/µL; <5)",
                        "Nerve Conduction Studies (NCS)" to "Slowing of conduction velocities, prolonged distal latencies, dispersed compound muscle action potentials (CMAP), and absent F-waves consistent with Acute Inflammatory Demyelinating Polyradiculoneuropathy (AIDP)",
                        "Spinal MRI" to "Smooth enhancement of the cauda equina nerve roots on post-contrast T1-weighted images"
                    ),
                    diagnosticNote = "Definitive Diagnostic Finding: Guillain-Barré Syndrome (Acute Inflammatory Demyelinating Polyneuropathy) triggered by molecular mimicry post-Campylobacter enteritis."
                )
            ),
            correctDiagnosis = "Guillain-Barre Syndrome",
            acceptedAliases = listOf(
                "guillain-barre syndrome",
                "guillain barre syndrome",
                "gbs",
                "guillain barre",
                "aidp",
                "acute inflammatory demyelinating polyneuropathy"
            ),
            clinicalPearls = "Hallmark is symmetrical ascending flaccid paralysis with hyporeflexia/areflexia, frequently preceded by a gastrointestinal (Campylobacter) or respiratory infection. Lumbar puncture characteristically shows 'albuminocytologic dissociation' (high protein, normal cell count). Close monitoring of FVC and NIF is essential.",
            differentialDiagnoses = listOf("Myasthenia Gravis", "Botulism", "Tick Paralysis", "Spinal Cord Compression", "Chronic Inflammatory Demyelinating Polyneuropathy (CIDP)")
        ),

        // CASE 9: Acute Pancreatitis
        MedicalCase(
            id = "case_09",
            caseNumber = 109,
            patientProfile = "42-year-old male",
            chiefComplaintHeadline = "Excruciating epigastric pain radiating to back and profuse vomiting",
            specialty = "Gastroenterology / Emergency",
            difficulty = "Core Clinical Case",
            tabs = listOf(
                CaseTab(
                    id = 1,
                    title = "Chief Complaint & Triage",
                    shortTitle = "Complaint",
                    iconType = "triage",
                    description = "A 42-year-old male is brought to the ED by ambulance clutching his upper abdomen and writhing in severe distress.",
                    details = listOf(
                        "Chief Complaint" to "Severe epigastric abdominal pain and repeated vomiting",
                        "Onset" to "Began suddenly 10 hours ago; reached 10/10 intensity",
                        "Radiation" to "Piercing radiation directly straight through to the mid-back",
                        "Aggravating / Alleviating" to "Worse lying supine; partially relieved by sitting upright and leaning forward (fetal position)"
                    ),
                    diagnosticNote = "Triage: Patient is diaphoretic, tachycardic, and retching into an emesis basin."
                ),
                CaseTab(
                    id = 2,
                    title = "History of Present Illness (HPI)",
                    shortTitle = "HPI",
                    iconType = "history",
                    description = "Patient describes deep, constant, boring epigastric agony that began following an evening of heavy binge drinking.",
                    details = listOf(
                        "Pain Character" to "Constant, deep, non-colicky boring epigastric pain",
                        "Trigger Event" to "Heavy alcohol intake (8-10 whiskey drinks) at a celebration last night",
                        "GI Review" to "Inability to keep water down; 6 episodes of bilious non-bloody emesis",
                        "Constitutional" to "Profuse cold sweats, profound weakness, and dry mouth"
                    )
                ),
                CaseTab(
                    id = 3,
                    title = "Past Medical History & Meds",
                    shortTitle = "PMH",
                    iconType = "medical_history",
                    description = "History of hypertension and frequent heavy social alcohol consumption.",
                    details = listOf(
                        "Past Medical History" to "Hypertension, chronic alcohol use (15-20 drinks/week), mild hypertriglyceridemia",
                        "Surgical History" to "None",
                        "Medications" to "Lisinopril 10mg daily",
                        "Social History" to "1 pack/day smoking for 15 years; heavy alcohol use"
                    )
                ),
                CaseTab(
                    id = 4,
                    title = "Vital Signs & Hemodynamics",
                    shortTitle = "Vitals",
                    iconType = "vitals",
                    description = "Hypovolemic vital signs secondary to third-space retroperitoneal fluid sequestration.",
                    vitals = listOf(
                        VitalItem("Blood Pressure", "102/64", "mmHg", ParamStatus.LOW),
                        VitalItem("Heart Rate", "118", "bpm", ParamStatus.HIGH),
                        VitalItem("Temperature", "38.1", "°C", ParamStatus.HIGH),
                        VitalItem("Respiratory Rate", "22", "breaths/min", ParamStatus.HIGH),
                        VitalItem("Oxygen Saturation", "96%", "room air", ParamStatus.NORMAL)
                    ),
                    diagnosticNote = "Systemic Inflammatory Response Syndrome (SIRS) positive (HR >90, RR >20, Temp >38.0°C)."
                ),
                CaseTab(
                    id = 5,
                    title = "Physical Examination",
                    shortTitle = "Exam",
                    iconType = "exam",
                    description = "Marked epigastric tenderness with hypoactive bowel sounds; faint periumbilical bluish discoloration.",
                    details = listOf(
                        "General" to "Restless, acutely ill-appearing, leaning forward on gurney",
                        "Abdomen" to "Marked epigastric tenderness, voluntary guarding, hypoactive bowel sounds, no rigid board-like abdomen",
                        "Skin" to "Faint bluish ecchymosis noted around the umbilicus (early Cullen's sign indicative of retroperitoneal hemorrhage)",
                        "Cardiopulmonary" to "Tachycardia without murmurs, breath sounds clear bilaterally"
                    )
                ),
                CaseTab(
                    id = 6,
                    title = "Laboratory Investigations",
                    shortTitle = "Labs",
                    iconType = "labs",
                    description = "Diagnostic hallmark: Serum lipase elevated to over 20-fold the upper reference limit.",
                    labs = listOf(
                        LabItem("Serum Lipase", "1,480", "U/L", "10 - 60", ParamStatus.HIGH),
                        LabItem("Serum Amylase", "620", "U/L", "30 - 110", ParamStatus.HIGH),
                        LabItem("WBC", "16.8", "k/µL", "4.5 - 11.0", ParamStatus.HIGH),
                        LabItem("Hematocrit", "47.8", "%", "38 - 50", ParamStatus.HIGH),
                        LabItem("Blood Urea Nitrogen", "28", "mg/dL", "7 - 20", ParamStatus.HIGH),
                        LabItem("Serum Calcium", "8.2", "mg/dL", "8.5 - 10.5", ParamStatus.LOW)
                    ),
                    diagnosticNote = "Lipase >3x upper limit of normal satisfies the revised Atlanta diagnostic criteria for Acute Pancreatitis."
                ),
                CaseTab(
                    id = 7,
                    title = "Imaging & Clinical Debrief",
                    shortTitle = "Imaging",
                    iconType = "imaging",
                    description = "Abdominal CT demonstrates diffuse pancreatic parenchymal enlargement and peripancreatic fluid collections.",
                    details = listOf(
                        "Contrast-Enhanced CT Abdomen" to "Diffusely enlarged, edematous pancreas with ill-defined borders and extensive peripancreatic inflammatory fluid stranding in the retroperitoneum",
                        "Right Upper Quadrant Ultrasound" to "No cholelithiasis or common bile duct dilatation (confirms alcohol-induced rather than biliary etiology)"
                    ),
                    diagnosticNote = "Definitive Diagnosis: Acute Interstitial Edematous Pancreatitis secondary to acute alcohol toxicity."
                )
            ),
            correctDiagnosis = "Acute Pancreatitis",
            acceptedAliases = listOf(
                "acute pancreatitis",
                "pancreatitis",
                "alcoholic pancreatitis",
                "acute alcoholic pancreatitis"
            ),
            clinicalPearls = "Diagnosis requires 2 of 3 Atlanta criteria: (1) Characteristic epigastric pain radiating to back, (2) Serum lipase or amylase >3x upper limit of normal, (3) Characteristic findings on cross-sectional abdominal imaging. Lipase is more sensitive and specific than amylase. Aggressive early isotonic IV fluid resuscitation is paramount.",
            differentialDiagnoses = listOf("Perforated Peptic Ulcer", "Acute Cholecystitis", "Mesenteric Ischemia", "Myocardial Infarction", "Aortic Dissection")
        ),

        // CASE 10: Anaphylaxis
        MedicalCase(
            id = "case_10",
            caseNumber = 110,
            patientProfile = "28-year-old female",
            chiefComplaintHeadline = "Sudden throat tightness, generalized hives, and collapse after taking oral antibiotic",
            specialty = "Immunology / Emergency",
            difficulty = "Emergency Triage Case",
            tabs = listOf(
                CaseTab(
                    id = 1,
                    title = "Chief Complaint & Triage",
                    shortTitle = "Complaint",
                    iconType = "triage",
                    description = "A 28-year-old woman is rushed to resuscitation bay in acute respiratory distress and severe lightheadedness.",
                    details = listOf(
                        "Chief Complaint" to "Throat constriction, diffuse itchy red rash, and near-syncope",
                        "Onset" to "Started 15 minutes after ingesting her first dose of oral amoxicillin",
                        "Progression" to "Began with palmar pruritus and facial flushing, rapidly progressing to airway tightness and dizziness",
                        "Associated" to "Abdominal cramps and sudden watery diarrhea in ambulance"
                    ),
                    diagnosticNote = "Triage: High-acuity code sepsis/anaphylaxis activation. Rapid Response team assembled."
                ),
                CaseTab(
                    id = 2,
                    title = "History of Present Illness (HPI)",
                    shortTitle = "HPI",
                    iconType = "history",
                    description = "Prescribed Amoxicillin 500mg today for acute maxillary sinusitis. No prior reported adverse penicillin reactions.",
                    details = listOf(
                        "Drug Exposure" to "Amoxicillin 500mg oral capsule taken 20 minutes prior to symptom onset",
                        "Airway Symptoms" to "Sensation of lump in throat (globus), hoarseness, stridor, and severe dyspnea",
                        "Cutaneous" to "Intensely pruritic raised erythematous wheals covering face, trunk, and arms",
                        "Gastrointestinal" to "Severe crampy lower abdominal pain and nausea"
                    )
                ),
                CaseTab(
                    id = 3,
                    title = "Past Medical History & Meds",
                    shortTitle = "PMH",
                    iconType = "medical_history",
                    description = "Mild allergic rhinitis and asthma; never previously treated with aminopenicillins.",
                    details = listOf(
                        "Past Medical History" to "Mild intermittent asthma (albuterol inhaler), seasonal allergic rhinitis",
                        "Prior Drug Allergies" to "No documented drug allergies on record",
                        "Family History" to "Mother has severe penicillin allergy"
                    )
                ),
                CaseTab(
                    id = 4,
                    title = "Vital Signs & Hemodynamics",
                    shortTitle = "Vitals",
                    iconType = "vitals",
                    description = "Profound distributive shock with severe hypotension and hypoxia.",
                    vitals = listOf(
                        VitalItem("Blood Pressure", "76/44", "mmHg", ParamStatus.LOW),
                        VitalItem("Heart Rate", "134", "bpm", ParamStatus.HIGH),
                        VitalItem("Oxygen Saturation", "86%", "room air", ParamStatus.LOW),
                        VitalItem("Respiratory Rate", "32", "breaths/min", ParamStatus.HIGH),
                        VitalItem("Temperature", "36.8", "°C", ParamStatus.NORMAL)
                    ),
                    diagnosticNote = "Critical finding: Severe hypotension with narrow pulse pressure indicating distributive vasodilatory collapse."
                ),
                CaseTab(
                    id = 5,
                    title = "Physical Examination",
                    shortTitle = "Exam",
                    iconType = "exam",
                    description = "Periorbital angioedema, inspiratory stridor, diffuse expiratory wheezing, and urticaria.",
                    details = listOf(
                        "HEENT" to "Marked lip and uvular edema (angioedema), swollen tongue, audible high-pitched inspiratory stridor",
                        "Lungs" to "Diffuse high-pitched expiratory wheezing throughout all lung fields with intercostal retractions",
                        "Skin" to "Diffuse coalescent blanching urticarial wheals with intense erythematous erythema",
                        "Cardiovascular" to "Profound tachycardia, weak thready radial pulses, cool extremities"
                    )
                ),
                CaseTab(
                    id = 6,
                    title = "Laboratory Investigations",
                    shortTitle = "Labs",
                    iconType = "labs",
                    description = "Elevated lactic acid indicative of hypoperfusion; acute mast cell degranulation markers.",
                    labs = listOf(
                        LabItem("Serum Lactate", "3.9", "mmol/L", "0.5 - 2.0", ParamStatus.HIGH),
                        LabItem("Blood pH (ABG)", "7.28", "", "7.35 - 7.45", ParamStatus.LOW),
                        LabItem("pCO2", "48", "mmHg", "35 - 45", ParamStatus.HIGH),
                        LabItem("Serum Total Tryptase", "38.5", "ng/mL", "1.0 - 11.5", ParamStatus.HIGH),
                        LabItem("Hematocrit", "49.0", "%", "36 - 46", ParamStatus.HIGH)
                    ),
                    diagnosticNote = "Serum tryptase elevated >3x baseline, confirming systemic IgE-mediated mast cell degranulation."
                ),
                CaseTab(
                    id = 7,
                    title = "Emergency Intervention & Debrief",
                    shortTitle = "Emergency",
                    iconType = "triage",
                    description = "Immediate intramuscular Epinephrine 0.3 mg (1:1000) administered in the mid-anterolateral thigh.",
                    details = listOf(
                        "First-Line Resuscitation" to "Intramuscular Epinephrine 0.3 mg into mid-outer thigh (vastus lateralis) immediately; rapid crystalloid IV bolus 2 Liters normal saline",
                        "Secondary Interventions" to "Supplemental high-flow oxygen via non-rebreather mask, IV diphenhydramine 50mg, IV famotidine 20mg, and IV methylprednisolone 125mg",
                        "Post-Treatment Response" to "BP rebounded to 112/68 mmHg, stridor resolved within 5 minutes, SpO2 climbed to 98%"
                    ),
                    diagnosticNote = "Definitive Diagnosis: Severe IgE-Mediated Anaphylaxis (Anaphylactic Shock) triggered by oral beta-lactam antibiotic."
                )
            ),
            correctDiagnosis = "Anaphylaxis",
            acceptedAliases = listOf(
                "anaphylaxis",
                "anaphylactic shock",
                "severe allergic reaction",
                "acute anaphylaxis"
            ),
            clinicalPearls = "Epinephrine IM (1:1000) into the anterolateral thigh is the first and only life-saving medication for anaphylaxis; there are NO absolute contraindications to epinephrine in anaphylaxis. Antihistamines and steroids are secondary adjuncts and do NOT treat airway edema or hypotension. Watch for biphasic reactions up to 72 hours.",
            differentialDiagnoses = listOf("Severe Vasovagal Syncope", "Acute Severe Asthma Exacerbation", "Foreign Body Airway Obstruction", "Scombroid Poisoning", "Hereditary Angioedema")
        )
    )

    fun getCaseByIndex(index: Int): MedicalCase {
        return cases[index % cases.size]
    }

    fun getRandomCase(excludeId: String? = null): MedicalCase {
        val filtered = cases.filter { it.id != excludeId }
        return if (filtered.isNotEmpty()) filtered.random() else cases.first()
    }
}
