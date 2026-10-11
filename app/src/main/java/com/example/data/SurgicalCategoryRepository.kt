package com.example.data

import com.example.model.MedicalSubject
import com.example.model.MiniCase
import com.example.model.MiniCaseOption
import com.example.model.SubjectChapter

/**
 * Repository providing clinical subject chapters and interactive mini cases for Medflix.
 * General Surgery is the flagship subject with chapters including Breast, Trauma,
 * Gastroenterology/GI, Endocrine, HPB, Hernia, Vascular, Pediatric, and Urology.
 *
 * Designed to easily accept additional case data from future clinical datasets.
 */
object SurgicalCategoryRepository {

    // Registered list of custom cases that can be appended dynamically
    private val customCases = mutableListOf<MiniCase>()

    fun registerCustomCase(case: MiniCase) {
        customCases.add(case)
    }

    val generalSurgeryChapters: List<SubjectChapter> = listOf(
        // CHAPTER 1: BREAST SURGERY
        SubjectChapter(
            id = "surg_breast",
            subjectId = "general_surgery",
            name = "Breast Surgery",
            shortName = "Breast",
            subtitle = "Palpable masses, carcinomas, phyllodes & benign pathology",
            iconType = "breast",
            highYieldPearl = "Triple Assessment: Clinical Exam + Imaging (US/Mammogram) + Pathology (Core Needle Biopsy) confirms diagnosis.",
            cases = listOf(
                MiniCase(
                    id = "mini_breast_01",
                    chapterId = "surg_breast",
                    subjectId = "general_surgery",
                    title = "24F with Painless Mobile Breast Mass",
                    patientProfile = "24-year-old female",
                    chiefComplaint = "Painless, solitary, highly movable lump in right upper outer breast quadrant",
                    vignette = "A 24-year-old healthy nulliparous woman presents with an incidental breast lump discovered during a self-exam 2 weeks ago. She reports no changes during her menstrual cycle. No skin tethering, nipple retraction, or discharge. No family history of breast or ovarian cancer.",
                    vitals = listOf(
                        "BP" to "116/72 mmHg",
                        "HR" to "68 bpm",
                        "Temp" to "36.7°C"
                    ),
                    physicalExam = listOf(
                        "Inspection" to "Symmetrical breasts; no erythema, peau d'orange, or nipple retraction",
                        "Palpation" to "Smooth, firm, rubbery, well-circumscribed, non-tender, highly mobile mass measuring 2.0 cm in right upper outer quadrant",
                        "Lymph Nodes" to "No axillary or supraclavicular lymphadenopathy palpable bilaterally"
                    ),
                    keyInvestigation = "Targeted Breast Ultrasound: Well-demarcated, oval, hypoechoic mass with smooth circumscribed margins and gentle posterior acoustic enhancement. Aspect ratio wider-than-tall (BIRADS 3).",
                    promptQuestion = "What is the most likely diagnosis and appropriate clinical management?",
                    options = listOf(
                        MiniCaseOption(1, "Fibroadenoma; reassure and routine clinical surveillance or follow-up ultrasound in 6 months", true, "Fibroadenoma ('breast mouse') is the most common benign breast neoplasm in women <30. Rubbery, mobile, well-circumscribed; follow-up ultrasound is standard."),
                        MiniCaseOption(2, "Invasive Ductal Carcinoma; immediate stereotactic wide local excision", false, "Patient is 24, mass is mobile and circumscribed with no malignant features. Invasive carcinoma typically presents with hard, irregular, fixed mass."),
                        MiniCaseOption(3, "Phyllodes Tumor; emergent modified radical mastectomy", false, "Benign fibroadenoma is vastly more prevalent; phyllodes tumors are rapid-growing and usually seen in women aged 40-50."),
                        MiniCaseOption(4, "Mammary Duct Ectasia; prophylactic bilateral subcutaneous mastectomy", false, "Duct ectasia presents with periareolar inflammation and multicolored green/brown nipple discharge, not a distinct discrete mobile rubbery mass.")
                    ),
                    explanation = "Fibroadenoma is the most common benign breast mass in young women (15-35 years). Characteristically described as a 'breast mouse' because it slips easily under the examining fingers. In young patients with typical benign sonographic features, reassurance and conservative observation with interval ultrasound in 6 months is safe.",
                    operativePearls = listOf(
                        "Triple assessment: clinical exam, ultrasound (for age < 30), and biopsy if suspicious or growing >3 cm.",
                        "Excision is indicated if diameter > 3 cm, rapid enlargement, or patient preference."
                    ),
                    redFlags = listOf(
                        "Rapid growth > 20% in size suggests potential giant fibroadenoma or Phyllodes tumor.",
                        "Skin dimpling or nipple retraction warrants immediate core biopsy."
                    ),
                    difficulty = "Core",
                    pointsReward = 100
                ),
                MiniCase(
                    id = "mini_breast_02",
                    chapterId = "surg_breast",
                    subjectId = "general_surgery",
                    title = "58F with Hard Fixed Upper-Outer Quadrant Mass",
                    patientProfile = "58-year-old postmenopausal female",
                    chiefComplaint = "Firm, ill-defined right breast mass with subtle skin dimpling",
                    vignette = "A 58-year-old postmenopausal woman attends clinic after noticing a persistent non-tender fullness in her right breast. Her mother had breast cancer at age 52. On examination, the mass is stony-hard, poorly circumscribed, and tethered to the pectoral fascia.",
                    vitals = listOf(
                        "BP" to "134/84 mmHg",
                        "HR" to "74 bpm",
                        "Temp" to "36.8°C"
                    ),
                    physicalExam = listOf(
                        "Inspection" to "Subtle skin dimpling over right 10 o'clock position when patient raises arms",
                        "Palpation" to "3.2 cm stony-hard, irregular, fixed mass tethered to deep tissue",
                        "Axilla" to "Two firm, non-tender, mobile lymph nodes palpable in Level I right axilla"
                    ),
                    keyInvestigation = "Diagnostic Mammography & US: High-density spiculate lesion with clustered microcalcifications (BIRADS 5). Ultrasound confirms a taller-than-wide mass with acoustic shadowing and suspicious Level I axillary node.",
                    promptQuestion = "What is the definitive next step for tissue diagnosis and receptor analysis?",
                    options = listOf(
                        MiniCaseOption(1, "Ultrasound-guided core needle biopsy of mass and axillary node for histology, ER/PR, and HER2 testing", true, "Core needle biopsy is gold standard over FNAC because it preserves architecture, distinguishes in-situ from invasive carcinoma, and provides receptor status."),
                        MiniCaseOption(2, "Fine needle aspiration cytology (FNAC) only", false, "FNAC provides cytology only and cannot differentiate between ductal carcinoma in situ (DCIS) and invasive carcinoma."),
                        MiniCaseOption(3, "Immediate modified radical mastectomy without preoperative biopsy", false, "Operating without tissue confirmation and molecular profiling violates modern oncologic principles."),
                        MiniCaseOption(4, "Empiric tamoxifen oral therapy for 3 months then re-examine", false, "Hormone receptor status must be proven first; delaying definitive tissue diagnosis risks disease progression.")
                    ),
                    explanation = "Core needle biopsy (CNB) is the mandatory investigation in suspicious breast lesions (BIRADS 4/5). It provides histologic subtype (most commonly Invasive Ductal Carcinoma / NST) and enables immunohistochemistry for Estrogen Receptor (ER), Progesterone Receptor (PR), HER2/neu, and Ki-67 index.",
                    operativePearls = listOf(
                        "Core biopsy confirms invasiveness vs DCIS and tests ER/PR/HER2 status to guide neoadjuvant vs upfront surgery.",
                        "Sentinel lymph node biopsy (SLNB) avoids axillary lymph node dissection in clinically node-negative early cases."
                    ),
                    redFlags = listOf(
                        "Peau d'orange skin texture signifies dermal lymphatic invasion (inflammatory breast cancer, T4d).",
                        "Supraclavicular lymph node enlargement indicates distant regional involvement (N3)."
                    ),
                    difficulty = "Intermediate",
                    pointsReward = 150
                ),
                MiniCase(
                    id = "mini_breast_03",
                    chapterId = "surg_breast",
                    subjectId = "general_surgery",
                    title = "42F with Rapidly Enlarging 6cm Lobulated Breast Tumor",
                    patientProfile = "42-year-old premenopausal female",
                    chiefComplaint = "Fast-growing large breast lump that doubled in size over 3 months",
                    vignette = "A 42-year-old multiparous woman presents with a 6 cm painless mass in her left breast. She previously had a small 2 cm nodule for two years, but over the last 3 months it enlarged rapidly. The overlying skin has prominent dilated veins but no ulceration.",
                    vitals = listOf(
                        "BP" to "122/76 mmHg",
                        "HR" to "72 bpm",
                        "Temp" to "36.6°C"
                    ),
                    physicalExam = listOf(
                        "Inspection" to "Enlarged left breast contour with superficial venous engorgement; no peau d'orange",
                        "Palpation" to "6.5 cm smooth, lobulated, fleshy-firm mass; mobile over chest wall; non-tender",
                        "Axilla" to "No axillary lymphadenopathy detected (lymphatic spread is rare)"
                    ),
                    keyInvestigation = "Ultrasound & Mammogram: Well-circumscribed, multilobular, heterogeneous solid-cystic mass with cleft-like cystic spaces. Core needle biopsy: Stroma hypercellularity with leaf-like (phyllodic) architecture.",
                    promptQuestion = "What is the diagnosis and required surgical margin for excision?",
                    options = listOf(
                        MiniCaseOption(1, "Phyllodes Tumor; wide local excision with ≥ 1 cm clear surgical margins", true, "Phyllodes tumors require wide local excision with clear margins (≥1 cm) to prevent local recurrence, even for benign variants. Axillary dissection is rarely needed because metastases are hematogenous."),
                        MiniCaseOption(2, "Fibroadenoma; simple enucleation within capsule", false, "Simple enucleation leads to high rates of recurrence for phyllodes tumors due to microscopic stromal infiltration."),
                        MiniCaseOption(3, "Inflammatory Carcinoma; neoadjuvant chemotherapy followed by mastectomy", false, "Inflammatory cancer presents with diffuse erythema, warmth, and peau d'orange, not a discrete lobulated mobile mass."),
                        MiniCaseOption(4, "Lactational Galactocele; bedside needle aspiration", false, "Galactocele occurs in lactating women and contains milky fluid; it does not present as a solid lobulated growing tumor.")
                    ),
                    explanation = "Phyllodes tumors are fibroepithelial neoplasms characterized by hypercellular stroma and cleft-like spaces lined by epithelium ('phyllodes' means leaf-like in Greek). They can be benign, borderline, or malignant. Standard treatment is wide local excision with a clean margin of at least 1 cm. Axillary clearance is NOT indicated as lymphatic metastasis is exceptionally rare (<1%).",
                    operativePearls = listOf(
                        "Ensure at least 1 cm margin to avoid recurrent local tumor.",
                        "Metastasis when malignant is hematogenous (lungs, bone), not lymphatic."
                    ),
                    redFlags = listOf(
                        "Stromal overgrowth and marked nuclear atypia suggest malignant phyllodes.",
                        "Recurrence can occur even years later; close post-op follow-up is necessary."
                    ),
                    difficulty = "Advanced",
                    pointsReward = 200
                )
            )
        ),

        // CHAPTER 2: TRAUMA & CRITICAL CARE SURGERY
        SubjectChapter(
            id = "surg_trauma",
            subjectId = "general_surgery",
            name = "Trauma & Acute Care",
            shortName = "Trauma",
            subtitle = "ATLS primary survey, thoracic injuries, hemoperitoneum & damage control",
            iconType = "trauma",
            highYieldPearl = "ATLS ABCDE: Airway with C-spine, Breathing, Circulation & hemorrhage control, Disability, Exposure.",
            cases = listOf(
                MiniCase(
                    id = "mini_trauma_01",
                    chapterId = "surg_trauma",
                    subjectId = "general_surgery",
                    title = "26M Blunt Chest Trauma with Hypotension & Tracheal Deviation",
                    patientProfile = "26-year-old male driver (MVA)",
                    chiefComplaint = "Severe dyspnea, cyanosis, and hypotension following steering wheel impact",
                    vignette = "A 26-year-old unbelted driver arrives via EMS after a high-speed frontal motor vehicle collision. In trauma resuscitation, he is agitated and gasping for breath. The trachea is visibly deviated to the left. Neck veins are distended. Breath sounds are completely absent over the right hemithorax with hyperresonance.",
                    vitals = listOf(
                        "BP" to "74/42 mmHg (Profound shock)",
                        "HR" to "142 bpm (Severe tachycardia)",
                        "RR" to "38 breaths/min",
                        "SpO2" to "82% on 15L non-rebreather mask"
                    ),
                    physicalExam = listOf(
                        "Airway" to "Patent; trachea markedly displaced to the left side",
                        "Breathing" to "Absent right breath sounds; tympanitic hyperresonance across right chest",
                        "Circulation" to "Distended jugular veins; weak thready radial pulses; cold clammy extremities"
                    ),
                    keyInvestigation = "Clinical diagnosis! Sending an unstable patient with tension pneumothorax to the radiology suite for a chest X-ray is contraindicated.",
                    promptQuestion = "What is the immediate, life-saving intervention?",
                    options = listOf(
                        MiniCaseOption(1, "Immediate needle thoracostomy (2nd intercostal space midclavicular or 5th intercostal anterior axillary line) followed promptly by tube thoracostomy", true, "Tension pneumothorax is a pure clinical emergency. Decompress immediately before any imaging to restore venous return and cardiac output."),
                        MiniCaseOption(2, "Urgent portable upright chest radiograph to confirm pleural line", false, "Never delay decompression for X-rays in suspected tension pneumothorax. Waiting causes cardiac arrest."),
                        MiniCaseOption(3, "Immediate rapid sequence intubation with positive pressure ventilation first", false, "Positive pressure ventilation will increase intrathoracic pressure and worsen tension, triggering immediate cardiovascular collapse."),
                        MiniCaseOption(4, "Infuse 2 liters of normal saline bolus and reassess hemodynamics", false, "Shock is obstructive from vena cava compression, not hypovolemic alone. Decompression must precede volume resuscitation.")
                    ),
                    explanation = "Tension Pneumothorax is an obstructive shock emergency where air enters the pleural cavity on inspiration but cannot escape. Intrapleural pressure builds up, compressing the ipsilateral lung, shifting the mediastinum, kinking the inferior vena cava, and abolishing venous return to the heart. Immediate needle decompression converts the closed tension into an open pneumothorax, instantly restoring cardiac preload.",
                    operativePearls = listOf(
                        "ATLS 10th edition recommends 5th intercostal space anterior axillary line for needle thoracostomy in adults.",
                        "Always follow needle decompression immediately with a large-bore (28-32 Fr) chest tube attached to underwater seal."
                    ),
                    redFlags = listOf(
                        "PEA arrest can ensue in seconds if decompression is delayed.",
                        "Persistent massive air leak and inability to re-expand lung suggests major tracheobronchial rupture."
                    ),
                    difficulty = "Core",
                    pointsReward = 120,
                    linkedFullCaseId = "case_01"
                ),
                MiniCase(
                    id = "mini_trauma_02",
                    chapterId = "surg_trauma",
                    subjectId = "general_surgery",
                    title = "31F Blunt Abdominal Trauma with Kehr Sign & Hemoperitoneum",
                    patientProfile = "31-year-old female cyclist",
                    chiefComplaint = "Left upper quadrant and left shoulder tip pain after bicycle handlebar impact",
                    vignette = "A 31-year-old cyclist was struck by an automobile and thrown onto her left side, striking the handlebars against her left upper quadrant. In the trauma bay, she complains of severe left flank pain and sharp pain in her left shoulder when lying flat (Kehr sign).",
                    vitals = listOf(
                        "BP" to "92/58 mmHg (Transient response to 1L IV crystalloid)",
                        "HR" to "118 bpm",
                        "RR" to "22 breaths/min",
                        "SpO2" to "97% on room air"
                    ),
                    physicalExam = listOf(
                        "Abdomen" to "Severe tenderness, guarding, and localized rigidity over left hypochondrium; left lower rib cage tenderness (10th-11th ribs)",
                        "Pelvis" to "Pelvic ring stable to compression",
                        "FAST Exam" to "Positive anechoic fluid stripe in splenorenal recess and Morrison's pouch"
                    ),
                    keyInvestigation = "Focused Assessment with Sonography for Trauma (FAST): Significant free intraperitoneal fluid in left upper quadrant (perisplenic) and pelvis. Patient is hemodynamically marginal.",
                    promptQuestion = "What organ is most likely injured, and what determines operative vs non-operative management?",
                    options = listOf(
                        MiniCaseOption(1, "Splenic Rupture; ongoing hemodynamic instability despite fluid resuscitation warrants emergent exploratory laparotomy", true, "The spleen is the most commonly injured visceral organ in blunt abdominal trauma. Hemodynamic instability with FAST-confirmed free fluid is a class I indication for laparotomy."),
                        MiniCaseOption(2, "Duodenal Hematoma; immediate exploratory duodenostomy", false, "Duodenal hematoma usually presents with high bowel obstruction days later; splenic injury matches left 10th-11th rib fractures and Kehr's sign."),
                        MiniCaseOption(3, "Renal Pelvis Laceration; observation with oral fluid challenge", false, "Left shoulder pain (Kehr sign) from phrenic nerve irritation by subdiaphragmatic blood is classic for splenic bleeding."),
                        MiniCaseOption(4, "Hepatic laceration only; conservative discharge after 6 hours", false, "Free fluid on FAST plus hypotension requires close inpatient ICU monitoring or emergent surgery, never early discharge.")
                    ),
                    explanation = "The spleen is the most frequently injured abdominal solid organ in blunt trauma. Lower left rib fractures (ribs 9-11) should always raise high index of suspicion. Kehr's sign (referred pain to the left shoulder) is caused by irritation of the left hemidiaphragm by blood. Hemodynamically unstable patients with positive FAST require prompt exploratory laparotomy for splenectomy or splenorrhaphy.",
                    operativePearls = listOf(
                        "Hemodynamic stability is the cornerstone: stable patients undergo contrast-enhanced CT with angioembolization if contrast blush is seen.",
                        "Post-splenectomy patients require vaccination against encapsulated organisms (Streptococcus pneumoniae, Neisseria meningitidis, Haemophilus influenzae type b)."
                    ),
                    redFlags = listOf(
                        "Delayed splenic rupture can occur up to 1-2 weeks later from a subcapsular hematoma.",
                        "Overwhelming Post-Splenectomy Infection (OPSI) carries up to 50% mortality."
                    ),
                    difficulty = "Intermediate",
                    pointsReward = 150
                )
            )
        ),

        // CHAPTER 3: GASTROENTEROLOGY & GI SURGERY
        SubjectChapter(
            id = "surg_gi",
            subjectId = "general_surgery",
            name = "Gastroenterology / GI Surgery",
            shortName = "GI Surgery",
            subtitle = "Acute abdomen, appendicitis, peptic ulcer, diverticulitis & bowel obstruction",
            iconType = "gi",
            highYieldPearl = "Alvarado score & peritonitis signs (guarding, rebound, rigidity) dictate urgent surgical intervention.",
            cases = listOf(
                MiniCase(
                    id = "mini_gi_01",
                    chapterId = "surg_gi",
                    subjectId = "general_surgery",
                    title = "19M Migrating Periumbilical to RLQ Pain with Rovsing Sign",
                    patientProfile = "19-year-old male",
                    chiefComplaint = "Periumbilical abdominal pain migrating to right lower quadrant with anorexia and low-grade fever",
                    vignette = "A 19-year-old college student presents with 18 hours of worsening abdominal pain. It initially started as a dull, crampy ache around the navel, but over the last 6 hours has shifted to the right iliac fossa, becoming sharp and constant. He has had 2 episodes of non-bilious vomiting and complete loss of appetite ('hamburger sign' positive).",
                    vitals = listOf(
                        "BP" to "124/76 mmHg",
                        "HR" to "98 bpm",
                        "Temp" to "38.1°C (Low-grade fever)",
                        "RR" to "18 breaths/min"
                    ),
                    physicalExam = listOf(
                        "Abdomen" to "Focal tenderness and involuntary muscle guarding localized precisely at McBurney's point (1/3 distance from ASIS to umbilicus)",
                        "Rovsing's Sign" to "Deep palpation in left lower quadrant reproduces pain in right lower quadrant",
                        "Psoas Sign" to "Passive extension of right hip reproduces retrocecal pain"
                    ),
                    keyInvestigation = "Labs: WBC 14.8 x 10^9/L (85% neutrophils, toxic granulations), CRP 64 mg/L. Ultrasound: Non-compressible, blind-ending tubular structure measuring 8.2 mm in outer diameter with appendicolith and periappendiceal fat stranding.",
                    promptQuestion = "What is the diagnosis and definitive surgical treatment?",
                    options = listOf(
                        MiniCaseOption(1, "Acute Appendicitis; laparoscopic appendectomy with perioperative prophylactic antibiotics", true, "Migrating visceral pain to somatic RLQ tenderness, leukocytosis, and non-compressible blind loop >6 mm confirm acute appendicitis. Laparoscopic appendectomy is standard of care."),
                        MiniCaseOption(2, "Meckel's Diverticulitis; emergent total colectomy", false, "Appendicitis is vastly more common; total colectomy is grossly overtreating simple right iliac fossa pathology."),
                        MiniCaseOption(3, "Mesenteric Adenitis; outpatient observation with oral analgesics only", false, "Ultrasonic non-compressible appendix > 8 mm and peritoneal signs exclude benign adenitis and require surgery to prevent perforation."),
                        MiniCaseOption(4, "Crohn's Ileitis; start high-dose intravenous infliximab", false, "Acute surgical abdomen must be operated to avert generalized peritonitis, not immunosuppressed with biologics.")
                    ),
                    explanation = "Acute appendicitis results from appendiceal lumen obstruction (commonly by a fecalith or lymphoid hyperplasia). Luminal distension stimulates visceral afferent nerve fibers (T10 dermatome, periumbilical pain). As inflammation spreads to the parietal peritoneum, somatic pain localizes sharply to McBurney's point. Laparoscopic appendectomy provides low morbidity, faster recovery, and direct visualization.",
                    operativePearls = listOf(
                        "Laparoscopy allows thorough inspection of pelvis, terminal ileum (Meckel's diverticulum), and cecum.",
                        "Administer broad-spectrum IV antibiotics (e.g. Cefoxitin or Ceftriaxone + Metronidazole) within 60 minutes of incision."
                    ),
                    redFlags = listOf(
                        "Sudden temporary relief of pain followed by diffuse peritonitis heralds appendiceal perforation.",
                        "Appendiceal abscess or phlegmon may require initial CT drainage and interval appendectomy in 6-8 weeks."
                    ),
                    difficulty = "Core",
                    pointsReward = 100,
                    linkedFullCaseId = "case_05"
                ),
                MiniCase(
                    id = "mini_gi_02",
                    chapterId = "surg_gi",
                    subjectId = "general_surgery",
                    title = "62M Sudden Epigastric Agony with Subdiaphragmatic Free Air",
                    patientProfile = "62-year-old male on chronic NSAIDs",
                    chiefComplaint = "Sudden catastrophic epigastric pain that began 2 hours ago while drinking coffee",
                    vignette = "A 62-year-old man with chronic osteoarthritic knee pain treated with daily high-dose ibuprofen presents with abrupt, explosive epigastric pain. He clutches his abdomen and lies motionless on the gurney because any movement or coughing causes unbearable distress. Abdominal wall is rigid as a board.",
                    vitals = listOf(
                        "BP" to "102/64 mmHg",
                        "HR" to "122 bpm",
                        "RR" to "28 breaths/min (shallow splinted breathing)",
                        "Temp" to "37.9°C"
                    ),
                    physicalExam = listOf(
                        "Inspection" to "Scaphoid abdomen, minimal breathing movement; patient terrified to change position",
                        "Palpation" to "'Board-like' generalized abdominal rigidity with universal rebound tenderness across all 4 quadrants",
                        "Percussion" to "Loss of liver dullness due to free subdiaphragmatic peritoneal gas"
                    ),
                    keyInvestigation = "Erect Chest Radiograph: Crisp crescentic translucent gas line underneath the right hemidiaphragm (pneumoperitoneum).",
                    promptQuestion = "What is the diagnosis and urgent operative approach?",
                    options = listOf(
                        MiniCaseOption(1, "Perforated Peptic Ulcer; emergent exploratory laparotomy / laparoscopy with Graham patch (omental patch) repair", true, "Sudden severe pain, board-like rigidity, and subdiaphragmatic free air signify perforated viscus (duodenal or gastric ulcer). Resuscitation and omental patch closure is lifesaving."),
                        MiniCaseOption(2, "Acute Pancreatitis; urgent endoscopic retrograde cholangiopancreatography (ERCP)", false, "Free intraperitoneal air indicates perforated hollow organ, not pancreatitis. Pancreatitis does not produce subdiaphragmatic free gas."),
                        MiniCaseOption(3, "Acute Aortic Dissection; start esmolol infusion and obtain CT angiogram", false, "Subdiaphragmatic air on erect X-ray confirms GI perforation, not dissection."),
                        MiniCaseOption(4, "Bowel Ischemia; medical observation with low-dose heparin infusion", false, "Peritonitis and free air mandate emergency surgical repair, not passive medical observation.")
                    ),
                    explanation = "Perforated peptic ulcer (typically anterior duodenal bulb or prepyloric) spills caustic acidic gastric juice and bile into the peritoneal cavity, producing severe chemical peritonitis and immediate involuntary 'board-like' rigidity. Erect chest X-ray reveals pneumoperitoneum in over 75% of cases. Treatment involves vigorous IV fluid resuscitation, IV PPI, broad-spectrum antibiotics, and emergency surgical closure with a pedicled omental plug (Graham patch).",
                    operativePearls = listOf(
                        "The Graham patch uses a vascularized tongue of greater omentum secured over the perforation with 3-4 interrupted absorbable sutures.",
                        "Biopsy the edge of gastric ulcers to rule out underlying malignancy (duodenal ulcers are rarely malignant)."
                    ),
                    redFlags = listOf(
                        "Septic shock, anuria, and metabolic acidosis develop quickly if operative delay exceeds 12 hours.",
                        "Elderly or immunocompromised patients on steroids may have blunted peritoneal signs."
                    ),
                    difficulty = "Intermediate",
                    pointsReward = 150
                ),
                MiniCase(
                    id = "mini_gi_03",
                    chapterId = "surg_gi",
                    subjectId = "general_surgery",
                    title = "54F with Bilious Vomiting, Obstipation & Air-Fluid Levels",
                    patientProfile = "54-year-old female with prior hysterectomy",
                    chiefComplaint = "Colicky abdominal pain, profuse green bilious vomiting, and failure to pass flatus for 48 hours",
                    vignette = "A 54-year-old woman who underwent an open total abdominal hysterectomy 4 years ago presents with increasing abdominal distension and crampy waves of central abdominal pain every few minutes. She has vomited large volumes of bilious fluid and has not passed stool or flatus for 2 days.",
                    vitals = listOf(
                        "BP" to "110/68 mmHg",
                        "HR" to "108 bpm",
                        "Temp" to "37.4°C",
                        "RR" to "20 breaths/min"
                    ),
                    physicalExam = listOf(
                        "Inspection" to "Distended abdomen with a midline infraumbilical surgical scar",
                        "Auscultation" to "High-pitched, metallic 'tinkling' bowel sounds during pain spasms; rushes of borborygmi",
                        "Palpation" to "Generalized fullness; soft without focal guarding or rebound tenderness",
                        "Rectal Exam" to "Empty rectal vault with no hard fecal impaction"
                    ),
                    keyInvestigation = "Abdominal X-Ray & CT Abdomen/Pelvis: Multiple dilated loops of small bowel (> 3 cm diameter) with central valvulae conniventes and staircase air-fluid levels. Transition point in mid-ileum with no closed-loop or bowel wall pneumatosis.",
                    promptQuestion = "What is the primary etiology and initial management protocol?",
                    options = listOf(
                        MiniCaseOption(1, "Adhesive Small Bowel Obstruction (SBO); initial non-operative trial with NPO, IV crystalloids, and nasogastric decompression", true, "Postoperative peritoneal adhesions are the #1 cause of SBO (60-70%). Without signs of strangulation (fever, peritonitis, tachycardia, ischemia), a trial of NG decompression and Gastrografin challenge is successful in 70-80%."),
                        MiniCaseOption(2, "Sigmoid Volvulus; immediate flexible sigmoidoscopic detorsion", false, "Sigmoid volvulus shows massive 'coffee bean' colonic loop originating in pelvis, not central small bowel valvulae conniventes."),
                        MiniCaseOption(3, "Acute Mesenteric Ischemia; urgent catheter embolectomy", false, "Patient has classic adhesive obstruction signs with history of pelvic surgery; initial management is NG tube decompression."),
                        MiniCaseOption(4, "Paralytic ileus; prescribe high-dose oral metoclopramide and laxatives", false, "Mechanical transition point on CT excludes ileus. Giving laxatives or prokinetics against a mechanical obstruction risks perforation.")
                    ),
                    explanation = "Adhesive small bowel obstruction is the most frequent surgical cause of acute intestinal blockage. Mechanical obstruction leads to proximal accumulation of swallowed air and digestive secretions, causing bowel distension, colicky spasms, and high-pitched tinkling rushes. Initial treatment for uncomplicated adhesive SBO is non-operative: bowel rest (NPO), fluid resuscitation, and nasogastric tube decompression.",
                    operativePearls = listOf(
                        "Water-soluble contrast (Gastrografin) serves both as diagnostic test (reaches cecum within 24h rules out complete obstruction) and therapeutic hyperosmolar agent.",
                        "Failure to resolve within 48-72 hours or development of closed-loop obstruction dictates laparotomy."
                    ),
                    redFlags = listOf(
                        "Strangulation signs: persistent tachycardia, localized peritonitis, fever, leukocytosis, and elevated serum lactate.",
                        "Closed-loop obstruction can rapidly progress to necrosis without massive distension."
                    ),
                    difficulty = "Core",
                    pointsReward = 100
                )
            )
        ),

        // CHAPTER 4: ENDOCRINE SURGERY
        SubjectChapter(
            id = "surg_endocrine",
            subjectId = "general_surgery",
            name = "Endocrine Surgery",
            shortName = "Endocrine",
            subtitle = "Thyroid nodules, parathyroid adenomas, pheochromocytoma & adrenal masses",
            iconType = "endocrine",
            highYieldPearl = "Always rule out pheochromocytoma with plasma metanephrines before biopsy or surgery to avert hypertensive crisis.",
            cases = listOf(
                MiniCase(
                    id = "mini_endo_01",
                    chapterId = "surg_endocrine",
                    subjectId = "general_surgery",
                    title = "36F with Solitary Thyroid Nodule & Psammoma Bodies",
                    patientProfile = "36-year-old female",
                    chiefComplaint = "Asymptomatic firm anterior neck lump discovered by her hair stylist",
                    vignette = "A 36-year-old female presents for evaluation of a painless nodule in the right side of her neck. She has no dysphagia, dysphonia, or dyspnea. She is clinically euthyroid (TSH is 1.8 mIU/L, normal). No history of neck irradiation. On exam, a solitary 2.2 cm firm, non-tender nodule in the right thyroid lobe moves with deglutition.",
                    vitals = listOf(
                        "BP" to "118/74 mmHg",
                        "HR" to "72 bpm",
                        "Temp" to "36.8°C"
                    ),
                    physicalExam = listOf(
                        "Neck" to "Solitary 2.2 cm nodule in right lower pole of thyroid; smooth, firm, moves smoothly on swallowing",
                        "Lymph Nodes" to "One 1.2 cm firm, non-tender level III cervical lymph node palpable on right side",
                        "Vocal Cords" to "Normal phonation and bilateral symmetrical vocal cord mobility on indirect laryngoscopy"
                    ),
                    keyInvestigation = "High-Resolution Thyroid Ultrasound: Solid hypoechoic nodule, taller-than-wide, irregular microlobulated margins with punctate microcalcifications (TI-RADS 5). FNA Biopsy: Nuclear grooving, pseudoinclusions, 'Orphan Annie eye' nuclei, and psammoma bodies.",
                    promptQuestion = "What is the diagnosis and standard surgical management?",
                    options = listOf(
                        MiniCaseOption(1, "Papillary Thyroid Carcinoma (PTC); total thyroidectomy (or lobectomy) with central compartment lymph node dissection", true, "Papillary carcinoma is the most common thyroid cancer (>80%). Features include Orphan Annie nuclei, psammoma bodies, and lymphatic spread. Surgical resection followed by radioactive iodine (RAI) if high-risk is standard."),
                        MiniCaseOption(2, "Follicular Adenoma; suppressive levothyroxine therapy for 1 year", false, "Cytology with Orphan Annie eyes and psammoma bodies is pathognomonic for papillary thyroid cancer, not a benign follicular adenoma."),
                        MiniCaseOption(3, "Subacute Granulomatous Thyroiditis; oral prednisone for 4 weeks", false, "Subacute thyroiditis presents with tender neck pain, elevated ESR, and transient hyperthyroidism, not a discrete malignant nodule."),
                        MiniCaseOption(4, "Medullary Thyroid Carcinoma; immediate neck radiation only", false, "Medullary cancer is derived from parafollicular C-cells (calcitonin positive) with amyloid stroma, not Orphan Annie nuclei."),
                        MiniCaseOption(5, "Anaplastic Thyroid Cancer; palliative tracheostomy only", false, "Anaplastic cancer occurs in elderly patients with aggressive rapidly invading compressive mass over weeks.")
                    ),
                    explanation = "Papillary Thyroid Carcinoma (PTC) is the most prevalent malignant thyroid tumor. It predominantly spreads via lymphatics to cervical lymph nodes. Classic cytologic features on fine-needle aspiration include enlarged overlapping nuclei with ground-glass chromatin ('Orphan Annie eyes'), longitudinal nuclear grooves, intranuclear cytoplasmic inclusions, and concentric laminated microcalcifications ('psammoma bodies').",
                    operativePearls = listOf(
                        "Identify and preserve the recurrent laryngeal nerve (RLN) and at least two functional parathyroid glands with their blood supply.",
                        "Serum thyroglobulin serves as a sensitive postoperative tumor marker for monitoring recurrence after total thyroidectomy."
                    ),
                    redFlags = listOf(
                        "Involvement of external branch of superior laryngeal nerve causes loss of high-pitch vocal timbre.",
                        "Postoperative hypocalcemia from parathyroid devascularization can cause perioral numbness, Chvostek's, and Trousseau's signs."
                    ),
                    difficulty = "Intermediate",
                    pointsReward = 150
                ),
                MiniCase(
                    id = "mini_endo_02",
                    chapterId = "surg_endocrine",
                    subjectId = "general_surgery",
                    title = "48F with Recurrent Kidney Stones, Osteopenia & Hypercalcemia",
                    patientProfile = "48-year-old female",
                    chiefComplaint = "Recurrent calcium oxalate renal colic, fatigue, and bone aches ('bones, stones, groans, psychiatric overtones')",
                    vignette = "A 48-year-old woman is referred by nephrology after her second episode of nephrolithiasis in 18 months. She reports chronic constipation, generalized lethargy, depressed mood, and diffuse joint aches. Routine chemistry shows serum calcium 11.8 mg/dL (high) and serum phosphate 2.1 mg/dL (low).",
                    vitals = listOf(
                        "BP" to "138/88 mmHg",
                        "HR" to "78 bpm",
                        "Temp" to "36.7°C"
                    ),
                    physicalExam = listOf(
                        "General" to "Mildly dehydrated, flat affect",
                        "Neck" to "Supple; no palpable thyroid or neck masses (normal parathyroid glands and adenomas are usually non-palpable)",
                        "Musculoskeletal" to "Mild diffuse tenderness over proximal long bones and ribs"
                    ),
                    keyInvestigation = "Serum Intact Parathyroid Hormone (iPTH): 185 pg/mL (marked inappropriate elevation in setting of hypercalcemia). 24h Urine Calcium: > 350 mg (rules out Familial Hypocalciuric Hypercalcemia). Sestamibi SPECT scan: Focal intense persistent uptake in right inferior parathyroid gland.",
                    promptQuestion = "What is the diagnosis and definitive surgical cure?",
                    options = listOf(
                        MiniCaseOption(1, "Primary Hyperparathyroidism due to solitary parathyroid adenoma; focused minimally invasive parathyroidectomy with intraoperative PTH monitoring", true, "Over 85% of primary hyperparathyroidism cases are caused by a single adenoma. Minimally invasive parathyroidectomy with >50% drop in intraoperative PTH at 10 minutes confirms cure."),
                        MiniCaseOption(2, "Secondary Hyperparathyroidism; subtotal parathyroidectomy and renal transplant", false, "Secondary hyperparathyroidism occurs in chronic renal failure with hypocalcemia or normal calcium, not hypercalcemia."),
                        MiniCaseOption(3, "Humoral Hypercalcemia of Malignancy; start denosumab and hospice care", false, "Hypercalcemia of malignancy has suppressed PTH (< 10 pg/mL) due to PTHrP secretion; this patient has elevated intact PTH."),
                        MiniCaseOption(4, "Familial Hypocalciuric Hypercalcemia (FHH); total thyroidectomy", false, "High 24h urine calcium (>350 mg) and single adenoma on Sestamibi exclude FHH (FHH has urine calcium-to-creatinine clearance ratio < 0.01).")
                    ),
                    explanation = "Primary Hyperparathyroidism (PHPT) is the most common cause of asymptomatic and symptomatic hypercalcemia in outpatients. Autonomous PTH secretion stimulates osteoclast bone resorption ('bones'), increases renal calcium reabsorption and stone formation ('stones'), causes constipation/peptic ulcers ('groans'), and neurocognitive disturbances ('psychiatric overtones'). Focused parathyroidectomy offers >95% cure rate.",
                    operativePearls = listOf(
                        "Miami criterion: A > 50% fall in circulating intact PTH from baseline at 10 minutes post-excision predicts surgical cure.",
                        "Sestamibi scan combined with 4D-CT provides precise anatomical localization."
                    ),
                    redFlags = listOf(
                        "Severe hypercalcemic crisis (calcium > 14 mg/dL) can precipitate fatal cardiac arrhythmias and coma.",
                        "'Hungry bone syndrome' with severe post-op hypocalcemia can develop in patients with extensive preoperative osteopenia."
                    ),
                    difficulty = "Core",
                    pointsReward = 120
                )
            )
        ),

        // CHAPTER 5: HEPATOBILIARY & PANCREATIC (HPB) SURGERY
        SubjectChapter(
            id = "surg_hpb",
            subjectId = "general_surgery",
            name = "Hepatobiliary & Pancreatic (HPB)",
            shortName = "HPB Surgery",
            subtitle = "Cholecystitis, choledocholithiasis, cholangitis, pancreatitis & liver lesions",
            iconType = "hpb",
            highYieldPearl = "Sonographic Murphy's sign + Gallbladder wall thickening (>3mm) + Pericholecystic fluid = Acute Cholecystitis.",
            cases = listOf(
                MiniCase(
                    id = "mini_hpb_01",
                    chapterId = "surg_hpb",
                    subjectId = "general_surgery",
                    title = "45F with RUQ Pain After Fatty Meal & Murphy Sign",
                    patientProfile = "45-year-old female (BMI 32)",
                    chiefComplaint = "Severe right upper quadrant pain radiating to right shoulder blade following cheeseburger dinner",
                    vignette = "A 45-year-old multiparous female presents with 12 hours of constant, agonizing pain under her right rib cage that radiates around to the right subscapular region. She experienced recurrent episodes of self-limiting postprandial dyspepsia in the past, but this episode failed to resolve. She has vomited twice.",
                    vitals = listOf(
                        "BP" to "136/82 mmHg",
                        "HR" to "102 bpm",
                        "Temp" to "38.3°C",
                        "RR" to "20 breaths/min"
                    ),
                    physicalExam = listOf(
                        "Abdomen" to "Marked tenderness on palpation of right upper quadrant",
                        "Murphy's Sign" to "Inspiratory arrest upon deep palpation of right hypochondrium as inflamed gallbladder contacts examiner's fingers (Positive Murphy sign)",
                        "Jaundice" to "Sclerae anicteric; no yellow discoloration"
                    ),
                    keyInvestigation = "Abdominal Ultrasound: Gallbladder distension with multiple acoustic-shadowing stones, an impacted calculus in Hartmann's pouch, gallbladder wall thickening of 4.8 mm, pericholecystic fluid halo, and positive sonographic Murphy sign.",
                    promptQuestion = "What is the diagnosis and recommended surgical approach?",
                    options = listOf(
                        MiniCaseOption(1, "Acute Calculous Cholecystitis; early index-admission laparoscopic cholecystectomy within 72 hours", true, "Early laparoscopic cholecystectomy (within 72-96 hours of symptom onset) is superior to delayed interval surgery, reducing total hospital stay, complication rates, and recurrence."),
                        MiniCaseOption(2, "Biliary Colic; prescribe oral ursodeoxycholic acid and outpatient follow-up in 6 months", false, "Fever, leukocytosis, wall thickening (>3 mm), and pericholecystic fluid indicate true acute inflammation, not simple uncomplicated biliary colic."),
                        MiniCaseOption(3, "Ascending Cholangitis; emergent bedside transhepatic biliary stent", false, "Patient has no jaundice or hyperbilirubinemia, so common bile duct is not obstructed with ascending sepsis."),
                        MiniCaseOption(4, "Acute Viral Hepatitis; supportive hydration and bed rest", false, "Impacted gallstone with positive Murphy's sign and wall thickening is an ultrasound-confirmed surgical emergency.")
                    ),
                    explanation = "Acute calculous cholecystitis occurs when a gallstone becomes impacted in the cystic duct or Hartmann's pouch, causing luminal distension, mucosal ischemia, chemical irritation by trapped bile salts, and secondary bacterial infection. Tokyo Guidelines recommend early laparoscopic cholecystectomy within the index admission for Tokyo Grade I and II cholecystitis.",
                    operativePearls = listOf(
                        "Critical View of Safety (Strasberg): Clear hepatocystic triangle to visualize exactly two structures entering the gallbladder (cystic duct and cystic artery) before clipping.",
                        "Laparoscopic subtotal cholecystectomy is a safe bailout strategy in severe 'frozen' Calot's triangle."
                    ),
                    redFlags = listOf(
                        "Emphysematous cholecystitis (gas in gallbladder wall) has high gangrene risk, especially in diabetic patients.",
                        "Conversion to open surgery is always a safe clinical judgment, not a failure."
                    ),
                    difficulty = "Core",
                    pointsReward = 100,
                    linkedFullCaseId = "case_04"
                )
            )
        ),

        // CHAPTER 6: HERNIA & ABDOMINAL WALL SURGERY
        SubjectChapter(
            id = "surg_hernia",
            subjectId = "general_surgery",
            name = "Hernia & Abdominal Wall",
            shortName = "Hernia",
            subtitle = "Inguinal, femoral, umbilical, incisional & strangulated bowel hernias",
            iconType = "hernia",
            highYieldPearl = "Femoral hernia is inferior and lateral to pubic tubercle; highest strangulation rate among all groin hernias.",
            cases = listOf(
                MiniCase(
                    id = "mini_hernia_01",
                    chapterId = "surg_hernia",
                    subjectId = "general_surgery",
                    title = "68M with Irreducible Painful Groin Lump & Erythema",
                    patientProfile = "68-year-old male",
                    chiefComplaint = "Sudden excruciating pain in right groin swelling that cannot be pushed back inside",
                    vignette = "A 68-year-old retired carpenter with a 5-year history of a reducible right groin hernia notices that while lifting heavy lumber, the bulge suddenly popped out, became extremely painful, and cannot be pushed back. Over the last 4 hours, the swelling turned purplish-red. He has vomited once and feels sick.",
                    vitals = listOf(
                        "BP" to "148/92 mmHg",
                        "HR" to "114 bpm",
                        "Temp" to "38.2°C (Fever)",
                        "RR" to "22 breaths/min"
                    ),
                    physicalExam = listOf(
                        "Groin Exam" to "Tense, exquisitely tender, non-reducible, non-pulsatile 5 cm mass in right groin descending into the upper scrotum (above and medial to pubic tubercle)",
                        "Skin" to "Overlying cutaneous erythema, warmth, and purplish discoloration",
                        "Cough Impulse" to "Absent cough impulse (hernia sac neck is tightly occluded)"
                    ),
                    keyInvestigation = "Bedside Clinical Assessment: Leukocytosis (WBC 16.2 x 10^9/L) and elevated serum lactate (2.8 mmol/L). Signs of strangulation (ischemia of entrapped bowel).",
                    promptQuestion = "What is the diagnosis and mandatory clinical management?",
                    options = listOf(
                        MiniCaseOption(1, "Strangulated Inguinal Hernia; immediate emergency surgical exploration without attempting forceful taxis", true, "Forceful manual reduction (taxis) of a strangulated hernia is strictly contraindicated because it risks reducing non-viable gangrenous bowel into the abdomen ('reduction en masse'). Emergency operative intervention is mandatory."),
                        MiniCaseOption(2, "Incarcerated hernia; administer IV sedation and perform forceful manual compression", false, "Forceful manual reduction of strangulated necrotic bowel can rupture the bowel or cause reduction en masse with fatal peritonitis."),
                        MiniCaseOption(3, "Direct inguinal hernia; prescribe supportive truss and schedule outpatient repair", false, "Erythema, fever, severe tenderness, and tachycardia represent strangulation which is an immediate surgical emergency."),
                        MiniCaseOption(4, "Acute epididymo-orchitis; start oral ciprofloxacin and scrotal support", false, "Mass descends from groin ring and cannot be separated from cord above, unlike isolated epididymitis.")
                    ),
                    explanation = "A hernia is strangulated when the blood supply to its contents is compromised, leading to ischemia, gangrene, and eventual perforation. Clinical hallmarks are severe unremitting pain, localized erythema, tenderness, systemic toxicity (fever, tachycardia, leukocytosis), and an absent cough impulse. Immediate groin exploration, assessment of bowel viability, resection of gangrenous tissue, and hernia repair are essential.",
                    operativePearls = listOf(
                        "Never reduce a strangulated hernia under sedation; opening the sac under direct vision allows inspecting the trapped bowel loop.",
                        "Viable bowel turns pink, peristalses, and shows visible mesenteric pulsations within 10 minutes of warm moist gauze packing."
                    ),
                    redFlags = listOf(
                        "Femoral hernias in elderly females carry up to 45% strangulation risk due to narrow rigid femoral ring.",
                        "Prosthetic mesh should be avoided or used with extreme caution in presence of frank bowel perforation and contaminated field."
                    ),
                    difficulty = "Intermediate",
                    pointsReward = 120
                )
            )
        ),

        // CHAPTER 7: VASCULAR SURGERY
        SubjectChapter(
            id = "surg_vascular",
            subjectId = "general_surgery",
            name = "Vascular Surgery",
            shortName = "Vascular",
            subtitle = "Aortic aneurysms, acute limb ischemia, carotid stenosis & mesenteric ischemia",
            iconType = "vascular",
            highYieldPearl = "Acute Limb Ischemia 6 P's: Pain, Pallor, Pulselessness, Paresthesia, Paralysis, Poikilothermia.",
            cases = listOf(
                MiniCase(
                    id = "mini_vasc_01",
                    chapterId = "surg_vascular",
                    subjectId = "general_surgery",
                    title = "72M with Pulsatile Abdominal Mass, Flank Pain & Syncope",
                    patientProfile = "72-year-old male with hypertension and smoking history",
                    chiefComplaint = "Sudden severe back and left flank pain followed by transient loss of consciousness",
                    vignette = "A 72-year-old male smoker collapses at home with tearing lower back pain radiating into his left flank. EMS finds him hypotensive with cold diaphoresis. In the emergency department, on abdominal palpation, an expansive pulsatile mass is palpated above the umbilicus.",
                    vitals = listOf(
                        "BP" to "82/50 mmHg (Profound hypotension)",
                        "HR" to "128 bpm",
                        "RR" to "26 breaths/min",
                        "SpO2" to "95% on 4L nasal cannula"
                    ),
                    physicalExam = listOf(
                        "Abdomen" to "Prominent, tender, expansile pulsatile mass approximately 7 cm in diameter palpated above umbilicus",
                        "Extremities" to "Weak, thready femoral pulses bilaterally; cool lower extremities",
                        "Skin" to "Grey, diaphoretic, mottled skin over trunk"
                    ),
                    keyInvestigation = "Bedside Point-of-Care Ultrasound (POCUS): Infrarenal abdominal aorta measuring 7.8 cm in transverse diameter with evidence of retroperitoneal fluid disruption.",
                    promptQuestion = "What is the diagnosis and protocol for resuscitation and surgery?",
                    options = listOf(
                        MiniCaseOption(1, "Ruptured Abdominal Aortic Aneurysm (rAAA); immediate transfer to operating room for emergency open repair or EVAR with permissive hypotension", true, "Classic triad of rAAA: sudden back/flank pain, pulsatile abdominal mass, and hypotension. Immediate OR transfer without delaying for CT in an unstable patient; keep SBP 70-90 mmHg to prevent 'popping the clot'."),
                        MiniCaseOption(2, "Renal colic; transfer to radiology for non-contrast helical CT KUB", false, "Pulsatile mass and shock exclude simple renal calculus; delaying for CT leads to circulatory arrest."),
                        MiniCaseOption(3, "Acute Pancreatitis; start aggressive IV hydration with 4L bolus", false, "Aggressive high-volume resuscitation 'pops the retroperitoneal clot' and causes lethal exsanguination."),
                        MiniCaseOption(4, "Lumbar disc herniation; administer epidural steroid injection", false, "Fatal misdiagnosis of rAAA as musculoskeletal back pain; vascular collapse is imminent.")
                    ),
                    explanation = "Ruptured AAA is a surgical catastrophe with up to 80% overall mortality. Most rupture into the retroperitoneum, which may temporarily tamponade bleeding and provide a brief window for survival. The classic triad is severe back/flank pain, pulsatile abdominal mass, and hypotension. Permissive hypotension (targeting systolic BP 70-90 mmHg) minimizes hydrostatic disruption of the tamponading retroperitoneal hematoma prior to vascular cross-clamping.",
                    operativePearls = listOf(
                        "Permissive hypotension: avoid overzealous fluid resuscitation which dilutes clotting factors and disrupts retroperitoneal tamponade.",
                        "Immediate proximal control (suprarenal or infrarenal aortic cross-clamp) or aortic balloon occlusion (REBOA) is the primary goal upon entering the abdomen."
                    ),
                    redFlags = listOf(
                        "Frequently misdiagnosed as renal colic or acute lumbago; always palpate for aorta in older patients with sudden flank pain.",
                        "Free intraperitoneal rupture causes instant catastrophic exsanguination."
                    ),
                    difficulty = "Advanced",
                    pointsReward = 200
                )
            )
        ),

        // CHAPTER 8: UROLOGICAL SURGERY
        SubjectChapter(
            id = "surg_uro",
            subjectId = "general_surgery",
            name = "Urological Surgery",
            shortName = "Urology",
            subtitle = "Testicular torsion, Fournier gangrene, obstructing stone with sepsis & trauma",
            iconType = "uro",
            highYieldPearl = "Testicular torsion is a 6-hour surgical emergency. Cremasteric reflex is absent; Prehn's sign is negative.",
            cases = listOf(
                MiniCase(
                    id = "mini_uro_01",
                    chapterId = "surg_uro",
                    subjectId = "general_surgery",
                    title = "15M with Sudden Unilateral Scrotal Pain & Absent Cremasteric Reflex",
                    patientProfile = "15-year-old male",
                    chiefComplaint = "Sudden agonizing right scrotal pain awakening him from sleep 2 hours ago",
                    vignette = "A 15-year-old boy presents to the emergency room with acute, excruciating right hemi-scrotal pain and lower abdominal nausea. He has no dysuria, urethral discharge, or history of trauma. The right testis is swollen, exquisitely tender, and lies in a high, transverse orientation.",
                    vitals = listOf(
                        "BP" to "128/80 mmHg",
                        "HR" to "104 bpm",
                        "Temp" to "37.0°C"
                    ),
                    physicalExam = listOf(
                        "Scrotum" to "Right hemiscrotum swollen, erythematous, exquisitely tender to touch; testis elevated horizontally ('bell-clapper' deformity)",
                        "Cremasteric Reflex" to "Absent on the right side; intact on the left side",
                        "Prehn's Sign" to "Negative (elevation of scrotum does NOT relieve pain, often worsens it)"
                    ),
                    keyInvestigation = "Color Doppler Ultrasound: Absent arterial blood flow and venous waveforms in the right testis with normal parenchymal flow on the contralateral left testis.",
                    promptQuestion = "What is the diagnosis and critical timeframe for surgical detorsion?",
                    options = listOf(
                        MiniCaseOption(1, "Testicular Torsion; emergency scrotal exploration, detorsion, and bilateral orchidopexy within 6 hours", true, "Testicular torsion causes ischemia of the testicle due to spermatic cord twisting. Salvage rate is >90% if explored within 6 hours. Bilateral orchidopexy is required because anatomical 'bell-clapper' deformity is bilateral."),
                        MiniCaseOption(2, "Epididymo-orchitis; discharge with 14 days of oral levofloxacin", false, "Isolated epididymitis usually occurs in sexually active men with positive cremasteric reflex and positive Prehn sign; missing torsion results in testicular infarction."),
                        MiniCaseOption(3, "Hydrocele; elective outpatient aspiration in clinic", false, "Hydrocele is non-tender and transilluminates light; acute pain and absent Doppler flow confirm torsion."),
                        MiniCaseOption(4, "Varicocele; advise scrotal elevation and follow-up in 1 month", false, "Varicocele feels like a painless 'bag of worms' on the left side, not sudden severe ischemic pain.")
                    ),
                    explanation = "Testicular torsion is a urologic emergency caused by abnormal fixation of the testis and epididymis to the tunica vaginalis (the 'bell-clapper deformity'), allowing the testis to twist on its spermatic cord. This obstructs venous outflow and subsequently arterial inflow, resulting in testicular infarction within 6 to 12 hours. Bilateral orchidopexy is mandatory to prevent future torsion on the unaffected side.",
                    operativePearls = listOf(
                        "Detorsion is typically 'open book' (rotating lateral to medial if viewed from below), but emergency exploration is required regardless.",
                        "Bilateral three-point orchidopexy using non-absorbable sutures prevents recurrence."
                    ),
                    redFlags = listOf(
                        "Salvage drops to < 50% at 12 hours and < 10% after 24 hours.",
                        "Never wait for ultrasound if clinical index of suspicion is high and imaging would cause operational delay."
                    ),
                    difficulty = "Core",
                    pointsReward = 100
                )
            )
        )
    )

    // OTHER CLINICAL SUBJECTS (Internal Medicine, Pediatrics, OB/GYN, Emergency Medicine, Orthopedics)
    val otherSubjects: List<MedicalSubject> = listOf(
        MedicalSubject(
            id = "internal_medicine",
            name = "Internal Medicine",
            shortName = "Medicine",
            subtitle = "Cardiology, pulmonology, nephrology, hematology & rheumatology",
            description = "High-yield diagnostic reasoning for complex medical inpatients and outpatients.",
            iconType = "medicine",
            chapters = listOf(
                SubjectChapter(
                    id = "med_cardio",
                    subjectId = "internal_medicine",
                    name = "Cardiology & Vascular Medicine",
                    shortName = "Cardiology",
                    subtitle = "ACS, heart failure, arrhythmias, pericarditis & tamponade",
                    iconType = "cardio",
                    highYieldPearl = "ST-elevation in contiguous leads + reciprocal depression = emergent catheterization laboratory activation.",
                    cases = listOf(
                        MiniCase(
                            id = "mini_cardio_01",
                            chapterId = "med_cardio",
                            subjectId = "internal_medicine",
                            title = "60M with Crushing Substernal Chest Pressure & Diaphoresis",
                            patientProfile = "60-year-old male with diabetes and hyperlipidemia",
                            chiefComplaint = "Substernal crushing chest pain radiating to left jaw and shoulder for 90 minutes",
                            vignette = "A 60-year-old accountant experiences sudden severe pressure on his chest 'like an elephant sitting on my sternum'. He is ashen, profusely sweating, and short of breath. Pain does not change with position or deep inspiration.",
                            vitals = listOf(
                                "BP" to "146/88 mmHg",
                                "HR" to "92 bpm",
                                "SpO2" to "96% on ambient air"
                            ),
                            physicalExam = listOf(
                                "Cardiovascular" to "S4 gallop; normal S1 and S2; no murmurs or pericardial friction rub",
                                "Lungs" to "Clear bilaterally; no crackles"
                            ),
                            keyInvestigation = "12-Lead ECG: 3 mm convex ST-segment elevation in leads V1-V4 with reciprocal ST depression in leads II, III, and aVF (Anterior STEMI).",
                            promptQuestion = "What is the diagnosis and immediate primary reperfusion target time?",
                            options = listOf(
                                MiniCaseOption(1, "Acute Anterior STEMI; emergent Primary Percutaneous Coronary Intervention (PCI) within 90 minutes door-to-balloon", true, "Anterior STEMI from LAD occlusion requires immediate reperfusion. Primary PCI within ≤90 minutes is the treatment of choice."),
                                MiniCaseOption(2, "Unstable Angina; discharge with sublingual nitroglycerin prescription", false, "ST-segment elevation indicates transmural acute infarction, not angina."),
                                MiniCaseOption(3, "Acute Pericarditis; high-dose ibuprofen and colchicine", false, "Pericarditis produces diffuse concave ST elevation across all leads with PR depression, not focal anterior ST elevation with reciprocal changes."),
                                MiniCaseOption(4, "Gastroesophageal reflux; oral antacid cocktail", false, "Never treat anterior STEMI with antacids; delays are fatal.")
                            ),
                            explanation = "Acute ST-elevation myocardial infarction (STEMI) indicates acute transmural myocardial ischemia, most frequently due to plaque rupture and occlusive thrombus in the Left Anterior Descending (LAD) coronary artery. Primary PCI within 90 minutes of medical contact salvages myocardium and improves survival.",
                            operativePearls = listOf(
                                "Dual antiplatelet therapy (Aspirin + P2Y12 inhibitor) and unfractionated heparin prior to catheterization.",
                                "Door-to-balloon time < 90 min (or fibrinolysis < 30 min if PCI center > 120 min away)."
                            ),
                            redFlags = listOf(
                                "Cardiogenic shock or ventricular fibrillation can occur precipitously in anterior STEMI.",
                                "Papillary muscle rupture presents with sudden pulmonary edema and new holosystolic murmur."
                            ),
                            difficulty = "Core",
                            pointsReward = 100
                        )
                    )
                )
            )
        ),
        MedicalSubject(
            id = "obgyn",
            name = "Obstetrics & Gynecology",
            shortName = "OB/GYN",
            subtitle = "Ectopic pregnancy, pre-eclampsia, ovarian torsion & pelvic emergencies",
            description = "Reproductive health, high-risk obstetrics, and gynecological surgical emergencies.",
            iconType = "obgyn",
            chapters = listOf(
                SubjectChapter(
                    id = "obgyn_emergencies",
                    subjectId = "obgyn",
                    name = "Obstetric & Gynecologic Emergencies",
                    shortName = "OB Emergencies",
                    subtitle = "Ruptured ectopic, ovarian torsion, PID & pre-eclampsia",
                    iconType = "pregnant",
                    highYieldPearl = "Any reproductive-age female with lower abdominal pain is pregnant until proven otherwise by beta-hCG.",
                    cases = listOf(
                        MiniCase(
                            id = "mini_obgyn_01",
                            chapterId = "obgyn_emergencies",
                            subjectId = "obgyn",
                            title = "27F with Amenorrhea, Severe Pelvic Pain & Shoulder Tip Pain",
                            patientProfile = "27-year-old female",
                            chiefComplaint = "Unilateral right iliac fossa pain and syncope following 6 weeks of amenorrhea",
                            vignette = "A 27-year-old woman presents to the ER following a syncopal episode at home. Her last menstrual period was 6 weeks ago. She has had spotty vaginal bleeding for 3 days. In triage she has right-sided pelvic peritonitis and referred right shoulder pain.",
                            vitals = listOf(
                                "BP" to "86/52 mmHg (Shock)",
                                "HR" to "124 bpm",
                                "Temp" to "36.6°C"
                            ),
                            physicalExam = listOf(
                                "Abdomen" to "Severe right lower quadrant tenderness with rebound and guarding",
                                "Pelvic Exam" to "Cervical motion tenderness; exquisitely tender right adnexal mass; cul-de-sac fullness"
                            ),
                            keyInvestigation = "Urine pregnancy test: Positive. Quantitative serum beta-hCG: 4,800 mIU/mL. Transvaginal Ultrasound: Empty uterine cavity with thickened endometrium; 3.5 cm complex right adnexal mass with 'ring of fire' Doppler flow and large free fluid in pouch of Douglas.",
                            promptQuestion = "What is the diagnosis and urgent treatment?",
                            options = listOf(
                                MiniCaseOption(1, "Ruptured Ectopic Pregnancy with Hemoperitoneum; immediate fluid resuscitation and emergency laparoscopic / open salpingectomy", true, "Empty uterus above the discriminatory hCG zone (>2,000-3,500 mIU/mL) with adnexal mass, hypotension, and hemoperitoneum confirms ruptured tubal ectopic pregnancy requiring immediate surgical intervention."),
                                MiniCaseOption(2, "Threatened Abortion; prescribe bed rest and repeat ultrasound in 1 week", false, "Discriminatory zone is exceeded without intrauterine sac; hemoperitoneum and shock indicate rupture."),
                                MiniCaseOption(3, "Methotrexate intramuscular injection single dose", false, "Methotrexate is strictly contraindicated in hemodynamically unstable patients, ectopic size >3.5-4 cm, or with active hemoperitoneum."),
                                MiniCaseOption(4, "Pelvic Inflammatory Disease; oral doxycycline and ceftriaxone", false, "Positive beta-hCG and hemoperitoneum distinguish ectopic pregnancy from isolated PID.")
                            ),
                            explanation = "Tubal ectopic pregnancy accounts for the majority of first-trimester pregnancy-related maternal deaths. Over 95% occur in the ampulla or isthmus of the Fallopian tube. Once rupture occurs, hemoperitoneum leads to diaphragmatic irritation (shoulder tip pain) and hypovolemic shock. Surgical salpingectomy is curative and lifesaving.",
                            operativePearls = listOf(
                                "Administer Rho(D) immune globulin (RhoGAM) if patient is Rh-negative.",
                                "Salpingostomy preserves fertility if contralateral tube is damaged, provided patient is hemodynamically stable."
                            ),
                            redFlags = listOf(
                                "Cornual / interstitial ectopic can rupture later (10-14 weeks) with catastrophic massive hemorrhage.",
                                "Heterotopic pregnancy (coexistent IUP and ectopic) is more common in assisted reproduction (IVF)."
                            ),
                            difficulty = "Core",
                            pointsReward = 120
                        )
                    )
                )
            )
        ),
        MedicalSubject(
            id = "pediatrics",
            name = "Pediatrics",
            shortName = "Pediatrics",
            subtitle = "Neonatology, congenital anomalies, pediatric emergencies & infections",
            description = "Pediatric clinical pearls, neonatal syndromes, and developmental milestones.",
            iconType = "child",
            chapters = listOf(
                SubjectChapter(
                    id = "peds_surg",
                    subjectId = "pediatrics",
                    name = "Pediatric Surgical Pathology",
                    shortName = "Peds Surgery",
                    subtitle = "Pyloric stenosis, intussusception, malrotation & Hirschsprung",
                    iconType = "baby",
                    highYieldPearl = "Projectile non-bilious vomiting in 3-6 week old male infant with palpable olive mass = Hypertrophic Pyloric Stenosis.",
                    cases = listOf(
                        MiniCase(
                            id = "mini_peds_01",
                            chapterId = "peds_surg",
                            subjectId = "pediatrics",
                            title = "5-Week-Old Male Infant with Projectile Non-Bilious Vomiting",
                            patientProfile = "5-week-old firstborn male infant",
                            chiefComplaint = "Non-bilious projectile vomiting immediately after every feed; voraciously hungry ('hungry vomiter')",
                            vignette = "A 5-week-old firstborn baby boy is brought by his anxious parents because for the last 5 days, he forcefully vomits breast milk across the room shortly after nursing. He immediately demands to be fed again. He has fewer wet diapers and has lost 200g of weight.",
                            vitals = listOf(
                                "HR" to "152 bpm",
                                "RR" to "36 breaths/min",
                                "Temp" to "36.9°C"
                            ),
                            physicalExam = listOf(
                                "General" to "Depressed anterior fontanelle, dry mucous membranes, reduced skin turgor",
                                "Abdomen" to "Visible peristaltic waves from left to right across upper abdomen; palpable firm, mobile, olive-sized 2 cm mass in epigastrium / right hypochondrium ('pyloric olive')"
                            ),
                            keyInvestigation = "Serum Electrolytes: Hypochloremic, hypokalemic metabolic alkalosis (Cl- 84 mEq/L, K+ 3.2 mEq/L, HCO3- 34 mEq/L). Abdominal Ultrasound: Pyloric muscle thickness > 3.0 mm and channel length > 14 mm ('doughnut' / 'target' sign).",
                            promptQuestion = "What is the diagnosis and mandatory initial treatment step BEFORE surgery?",
                            options = listOf(
                                MiniCaseOption(1, "Hypertrophic Pyloric Stenosis; aggressive IV fluid resuscitation to correct hypochloremic alkalosis before elective Ramstedt pyloromyotomy", true, "Pyloric stenosis is a medical emergency and surgical non-emergency. Fluid and electrolyte correction (normal saline + potassium) must occur before anesthesia to prevent intraoperative apnea."),
                                MiniCaseOption(2, "Immediate emergency pyloromyotomy on arrival without waiting for lab correction", false, "Operating on an infant with metabolic alkalosis carries high risk of general anesthesia hypoventilation, apnea, and cardiac arrhythmias."),
                                MiniCaseOption(3, "Gastroesophageal reflux disease; prescribe thickener and upright positioning", false, "Palpable olive mass, metabolic alkalosis, and projectile vomiting confirm pyloric stenosis."),
                                MiniCaseOption(4, "Duodenal atresia; immediate Ladd procedure", false, "Duodenal atresia presents with bilious vomiting on day 1 of life with 'double bubble' sign on X-ray, not non-bilious projectile vomiting at 5 weeks.")
                            ),
                            explanation = "Infantile Hypertrophic Pyloric Stenosis (IHPS) is hypertrophy of the circular smooth muscle of the pylorus. Persistent loss of gastric hydrochloric acid (HCl) through vomiting causes classic hypochloremic, hypokalemic metabolic alkalosis with paradoxical aciduria. The surgery (Ramstedt pyloromyotomy) is technically straightforward, but safe anesthesia demands complete metabolic correction beforehand.",
                            operativePearls = listOf(
                                "Pyloromyotomy incises the thickened longitudinal and circular muscle fibers down to the submucosa without perforating the duodenal mucosa.",
                                "A bubble test (injecting air via NG tube into stomach under saline) tests for mucosal integrity."
                            ),
                            redFlags = listOf(
                                "Never operate with Cl- < 95 mEq/L or HCO3- > 30 mEq/L due to risk of post-op respiratory arrest.",
                                "Bilious emesis is NOT pyloric stenosis and demands emergent rule-out of midgut volvulus."
                            ),
                            difficulty = "Core",
                            pointsReward = 120
                        )
                    )
                )
            )
        ),
        MedicalSubject(
            id = "orthopedics",
            name = "Orthopedic Surgery & Trauma",
            shortName = "Orthopedics",
            subtitle = "Fractures, compartment syndrome, septic arthritis & dislocations",
            description = "Musculoskeletal trauma, compartment syndromes, joint aspirates & reduction techniques.",
            iconType = "bone",
            chapters = listOf(
                SubjectChapter(
                    id = "ortho_trauma",
                    subjectId = "orthopedics",
                    name = "Orthopedic Emergencies",
                    shortName = "Ortho Emergencies",
                    subtitle = "Compartment syndrome, open fractures & septic arthritis",
                    iconType = "fracture",
                    highYieldPearl = "Pain out of proportion to injury and pain on passive stretch = Acute Compartment Syndrome until proven otherwise.",
                    cases = listOf(
                        MiniCase(
                            id = "mini_ortho_01",
                            chapterId = "ortho_trauma",
                            subjectId = "orthopedics",
                            title = "22M Tibial Fracture with Pain Out of Proportion on Passive Stretch",
                            patientProfile = "22-year-old male",
                            chiefComplaint = "Unremitting burning pain in lower leg 8 hours after closed tibial shaft fracture reduction and plaster casting",
                            vignette = "A 22-year-old soccer player suffered a closed mid-shaft tibia-fibula fracture that was reduced and placed in a circular cast 8 hours ago. He now cries in agony from intense deep pressure in his anterior calf that does not respond to IV morphine. Pain is excruciating when his great toe is passively dorsiflexed.",
                            vitals = listOf(
                                "BP" to "142/86 mmHg",
                                "HR" to "110 bpm",
                                "Temp" to "37.1°C"
                            ),
                            physicalExam = listOf(
                                "Anterior Compartment" to "Firm, woody, tense swelling; skin is taut and shiny",
                                "Passive Stretch" to "Severe exacerbation of deep pain on passive plantarflexion and stretch of toe extensors",
                                "Neurologic" to "Decreased sensation in first webbed space of foot (deep peroneal nerve distribution)",
                                "Distal Pulses" to "Dorsalis pedis pulse is still palpable (pulselessness is a very late sign!)"
                            ),
                            keyInvestigation = "Compartment pressure measurement (Stryker needle): Anterior compartment pressure = 48 mmHg (Diastolic BP 86 - Compartment pressure 48 = Delta P of 38 mmHg; absolute pressure > 30 mmHg or Delta P < 30 mmHg confirms compartment syndrome).",
                            promptQuestion = "What is the diagnosis and immediate limb-saving procedure?",
                            options = listOf(
                                MiniCaseOption(1, "Acute Compartment Syndrome; immediate cast bivalving down to skin followed by emergency dual-incision four-compartment fasciotomy", true, "Acute compartment syndrome is an orthopedic emergency. Elevated osteofascial pressure impairs microvascular perfusion, leading to irreversible muscle and nerve necrosis within 6 hours. Emergency four-compartment fasciotomy is mandatory."),
                                MiniCaseOption(2, "Deep Vein Thrombosis; start therapeutic low molecular weight heparin", false, "Recent fracture, tight cast, tense woody compartment, and nerve sensory deficit signify compartment syndrome, not simple DVT. Heparin will worsen hematoma."),
                                MiniCaseOption(3, "Expected fracture pain; increase IV hydromorphone PCA dose", false, "Pain out of proportion unresponsive to opioids is the hallmark red flag; masking it causes limb loss."),
                                MiniCaseOption(4, "Reflex sympathetic dystrophy; prescribe gabapentin and physical therapy", false, "Acute swelling within 24h of trauma is life/limb-threatening compartment hypertension, not chronic CRPS.")
                            ),
                            explanation = "Acute Compartment Syndrome occurs when tissue pressure within an inelastic fascial compartment exceeds capillary perfusion pressure (usually >30 mmHg or when Delta pressure [Diastolic BP - Compartment Pressure] is <30 mmHg). Muscle necrosis begins after 4 hours and is irreversible after 8 hours. Palpable distal pulses do NOT rule out compartment syndrome because systolic pressure typically exceeds compartment pressure.",
                            operativePearls = listOf(
                                "Perform dual-incision four-compartment release: anterolateral incision releases anterior and lateral compartments; posteromedial incision releases superficial and deep posterior compartments.",
                                "Leave fascial wounds open with negative pressure wound therapy (wound VAC) and plan delayed primary closure in 48-72h."
                            ),
                            redFlags = listOf(
                                "Waiting for pulselessness and pallor means the limb is already irrecoverably infarcted.",
                                "Rhabdomyolysis and myoglobinuria can cause acute kidney injury."
                            ),
                            difficulty = "Core",
                            pointsReward = 120
                        )
                    )
                )
            )
        )
    )

    // Master list of all clinical subjects
    fun getAllSubjects(): List<MedicalSubject> {
        val genSurgSubject = MedicalSubject(
            id = "general_surgery",
            name = "General Surgery",
            shortName = "General Surgery",
            subtitle = "Core surgical chapters & clinical case solving",
            description = "Master surgical decision making across breast, trauma, gastrointestinal, endocrine, HPB, hernia, vascular, pediatric, and urologic surgery.",
            iconType = "surgery",
            chapters = generalSurgeryChapters
        )
        return listOf(genSurgSubject) + otherSubjects
    }

    fun getSubjectById(subjectId: String): MedicalSubject? {
        return getAllSubjects().find { it.id == subjectId }
    }

    fun getAllChapters(): List<SubjectChapter> {
        return getAllSubjects().flatMap { it.chapters }
    }

    fun getChapterById(chapterId: String): SubjectChapter? {
        return getAllChapters().find { it.id == chapterId }
    }

    fun getAllMiniCases(): List<MiniCase> {
        return getAllChapters().flatMap { it.cases } + customCases
    }

    fun getMiniCaseById(caseId: String): MiniCase? {
        return getAllMiniCases().find { it.id == caseId }
    }

    fun searchMiniCases(query: String, subjectId: String? = null, chapterId: String? = null): List<MiniCase> {
        val trimmed = query.trim().lowercase()
        return getAllMiniCases().filter { case ->
            val matchesSubject = subjectId == null || case.subjectId == subjectId
            val matchesChapter = chapterId == null || case.chapterId == chapterId
            val matchesQuery = trimmed.isEmpty() ||
                case.title.lowercase().contains(trimmed) ||
                case.chiefComplaint.lowercase().contains(trimmed) ||
                case.vignette.lowercase().contains(trimmed) ||
                case.explanation.lowercase().contains(trimmed) ||
                case.options.any { it.text.lowercase().contains(trimmed) }
            matchesSubject && matchesChapter && matchesQuery
        }
    }
}
