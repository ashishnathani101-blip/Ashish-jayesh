package com.example.data

object ClinicalCaseHints {

    /**
     * Returns a tailored, clinically pedagogical hint for a given case and tab id (1..7).
     * Hints are crafted specifically to nudge the clinician without giving away the answer directly.
     */
    fun getHint(caseId: String, tabId: Int): String {
        return when (caseId) {
            "case_01" -> when (tabId) {
                1 -> "Sudden unilateral pleuritic chest pain in a tall young male after an abrupt sensation indicates thoracic parietal compromise."
                2 -> "Sudden 'pop' followed by inability to expand the lung without preceding trauma suggests spontaneous pleural air leakage."
                3 -> "Tall, slender (asthenic) body habitus and cigarette smoking are recognized predisposing risk factors for apical bleb rupture."
                4 -> "Hyperresonance to percussion and absent breath sounds unilaterally without tracheal shift point toward unexpanded pleural space."
                5 -> "Acute hypoxemia (SpO2 91%) and tachypnea that improve promptly with supplemental O2 highlight ventilation-perfusion mismatch."
                6 -> "Normal cardiac troponin and normal D-dimer rule out acute myocardial infarction and pulmonary embolism."
                7 -> "Look closely at the PA chest radiograph: a visible visceral pleural line with absent peripheral lung markings confirms the diagnosis."
                else -> "Consider air trapped within the pleural space."
            }

            "case_02" -> when (tabId) {
                1 -> "Sudden pleuritic chest pain with air hunger following recent immobility is a hallmark of vascular embolic compromise."
                2 -> "Unilateral calf swelling and tenderness following orthopedic surgery indicates a probable deep vein thrombosis as the source."
                3 -> "Self-discontinuation of post-arthroplasty anticoagulant prophylaxis dramatically multiplies the risk of venous thromboembolism."
                4 -> "Loud P2 heart sound, tachypnea, and unilateral calf circumference discrepancy point to acute right heart strain and thromboembolic disease."
                5 -> "Sinus tachycardia combined with acute hypoxemia refractory to room air in a clear lung exam strongly indicates a ventilation-perfusion mismatch."
                6 -> "Massively elevated D-dimer (>4,000 ng/mL) combined with acute respiratory alkalosis and widened A-a gradient on ABG points to vascular occlusion."
                7 -> "Look closely at pulmonary arterial branches: vascular filling defects on CT-PA confirm obstruction of arterial flow."
                else -> "Think of thromboembolic occlusion in the pulmonary arterial tree."
            }

            "case_03" -> when (tabId) {
                1 -> "Fruity breath, profound lethargy, and deep rapid breathing (Kussmaul) indicate acute metabolic acidemia."
                2 -> "Polyuria, polydipsia, and omitting insulin during an acute viral illness precipitate rapid ketone accumulation."
                3 -> "Type 1 diabetes combined with absent insulin triggers massive unopposed lipolysis and ketogenesis."
                4 -> "Severe signs of dehydration, poor turgor, and dry membranes reflect massive osmotic diuresis from glycosuria."
                5 -> "Bedside glucose >500 mg/dL with heavy urine ketonuria confirms acute diabetic decompensation."
                6 -> "High anion gap metabolic acidosis (pH 7.12, HCO3 8, AG 28) with elevated beta-hydroxybutyrate is the biochemical triad."
                7 -> "Absence of focal infiltrates on CXR directs focus toward pure metabolic acidosis with profound dehydration."
                else -> "Consider metabolic diabetic crisis with acidosis and ketosis."
            }

            "case_04" -> when (tabId) {
                1 -> "Steady, severe RUQ pain following a fatty meal indicates acute gallbladder or biliary tree obstruction."
                2 -> "Pain radiating to the right infrascapular region and failing to resolve after several hours distinguishes acute cholecystitis from simple colic."
                3 -> "The 4 F's epidemiology (Female, Forties, Fertile, Fair) with prior self-resolving biliary colic episodes."
                4 -> "Arrest of deep inspiration upon palpation beneath the right costal margin (positive Murphy's sign) indicates peritoneal irritation of the gallbladder."
                5 -> "Low-grade fever and tachycardia alongside constant RUQ pain distinguish cholecystitis from uncomplicated biliary colic."
                6 -> "Leukocytosis with mild transaminitis points to local acute inflammation; normal bilirubin rules out acute choledocholithiasis."
                7 -> "Ultrasound showing gallbladder wall thickening (>4 mm), pericholecystic fluid, and acoustic shadowing from impacted stones confirms acute cholecystitis."
                else -> "Focus on acute inflammation of the gallbladder."
            }

            "case_05" -> when (tabId) {
                1 -> "Consider anatomical locations: migration of pain from visceral midline to a focal quadrant is classic for acute luminal pathology."
                2 -> "Notice the anorexia ('hamburger sign') and lack of bowel relief; visceral pain shifting to parietal peritoneal irritation signals inflammation reaching the serosa."
                3 -> "Lack of prior surgeries rules out adhesive obstruction; lack of relief with NSAIDs/acetaminophen suggests progressive surgical acute abdomen."
                4 -> "Peritoneal signs focal to McBurney's point and pain on hip extension (psoas sign) point to retrocecal or pelvic inflammation of a blind pouch."
                5 -> "Low-grade pyrexia and sinus tachycardia in a young adult indicate an acute infectious/inflammatory source rather than simple gastroenteritis."
                6 -> "Marked leukocytosis with a pronounced left shift (86% neutrophils) confirms bacterial inflammation; clear urinalysis excludes pyelonephritis/UTI."
                7 -> "A blind-ending, non-compressible tubular structure measuring >6 mm with an appendicolith on CT/ultrasound is definitive for acute inflammation."
                else -> "Focus on right lower quadrant surgical pathology."
            }

            "case_06" -> when (tabId) {
                1 -> "High fever, productive cough with rusty sputum, and pleuritic pain point to acute lobar parenchymal lung infection."
                2 -> "Shaking chills (rigors) followed by acute high fever and purulent sputum are hallmarks of typical bacterial pneumonia."
                3 -> "Recent viral illness often predisposes respiratory epithelium to bacterial superinfection."
                4 -> "Bronchial breath sounds, egophony (E to A changes), and dullness to percussion signal lobar consolidation."
                5 -> "Tachypnea, tachycardia, fever (>39°C), and oxygen desaturation indicate significant respiratory compromise."
                6 -> "Marked leukocytosis with left shift and sputum Gram stain showing Gram-positive lancet-shaped diplococci identify Streptococcus pneumoniae."
                7 -> "Dense lobar consolidation with air bronchograms on chest X-ray confirms acute lobar pneumonia."
                else -> "Focus on acute bacterial lung consolidation."
            }

            "case_07" -> when (tabId) {
                1 -> "Sudden onset severe occipital headache described as the 'worst of life' (thunderclap) is characteristic of subarachnoid hemorrhage."
                2 -> "Instant peak within seconds accompanied by neck stiffness and photophobia distinguishes intracranial bleed from migraine."
                3 -> "Smoking and family history of aneurysms are key predisposing factors for berry aneurysm rupture."
                4 -> "Nuchal rigidity, positive Kernig/Brudzinski signs, and absence of focal motor deficits indicate meningeal irritation by blood."
                5 -> "Marked hypertension (Cushing response) following sudden intracranial pressure spike."
                6 -> "Non-contrast CT head demonstrating hyperdensity in basal cisterns and sulci confirms subarachnoid hemorrhage."
                7 -> "CT cerebral angiography identifying a saccular aneurysm confirms the ruptured vascular source."
                else -> "Focus on acute vascular intracranial hemorrhage."
            }

            "case_08" -> when (tabId) {
                1 -> "Symmetrical ascending weakness beginning in the lower extremities following a diarrheal illness is classic for autoimmune peripheral neuropathy."
                2 -> "Difficulty climbing stairs progressing to hand weakness with paresthesias signals rapid ascending polyneuropathy."
                3 -> "Antecedent gastroenteritis (especially Campylobacter jejuni) triggers molecular mimicry against peripheral nerve gangliosides."
                4 -> "Absent deep tendon reflexes (areflexia) and bilateral flaccid quadriparesis are cardinal neurological exam findings."
                5 -> "Decreasing forced vital capacity (FVC) and labile heart rate indicate impending respiratory failure and autonomic instability."
                6 -> "Positive Campylobacter antibodies with normal creatine kinase and electrolytes rule out periodic paralysis or myositis."
                7 -> "Lumbar puncture showing albuminocytologic dissociation (elevated protein with normal cell count) is pathognomonic for Guillain-Barré Syndrome."
                else -> "Focus on acute autoimmune inflammatory polyradiculoneuropathy."
            }

            "case_09" -> when (tabId) {
                1 -> "Severe epigastric pain that bores directly through into the mid-back and is eased by leaning forward is classic for retroperitoneal organ inflammation."
                2 -> "Recent heavy alcohol binge preceding constant severe vomiting and epigastric agony is a major diagnostic clue."
                3 -> "Social history of alcohol abuse without gallstone symptoms points toward a non-biliary toxic-metabolic etiology."
                4 -> "Marked tachycardia and low BP indicate third-spacing of fluid into the retroperitoneum."
                5 -> "Periumbilical bluish ecchymosis (Cullen's sign) signifies retroperitoneal blood tracking through fascial planes."
                6 -> "Serum lipase elevated to >3 times the upper limit of normal is the biochemical hallmark diagnostic of acute pancreatitis."
                7 -> "Cross-sectional CT imaging demonstrating peripancreatic inflammatory stranding confirms the diagnosis."
                else -> "Think of acute inflammation of the pancreas."
            }

            "case_10" -> when (tabId) {
                1 -> "Sudden onset of hives, swelling, and respiratory distress within minutes of an oral drug exposure indicates acute systemic hypersensitivity."
                2 -> "Rapid progression from itching palms to throat constriction (globus) and dizziness suggests impending airway and vascular collapse."
                3 -> "First-time administration of a beta-lactam antibiotic (amoxicillin) can trigger rapid IgE-mediated mast cell degranulation in sensitized patients."
                4 -> "Severe hypotension (BP 76/44) combined with stridor and hypoxia confirms distributive anaphylactic shock."
                5 -> "Diffuse urticaria alongside inspiratory stridor and expiratory wheezes indicates multi-organ mucosal and cutaneous involvement."
                6 -> "Elevated serum tryptase confirms acute massive mast cell degranulation; severe lactic acidosis reflects poor tissue perfusion."
                7 -> "Immediate administration of intramuscular Epinephrine (1:1000) in the thigh is life-saving and diagnostic."
                else -> "Think of acute IgE-mediated anaphylaxis / anaphylactic shock."
            }

            else -> when (tabId) {
                1 -> "Review the chief complaint and triage observations carefully for organ-system clues."
                2 -> "Track the timeline and progression of symptoms to narrow down acute vs chronic etiology."
                3 -> "Check underlying risk factors, prior history, and medication adherence."
                4 -> "Physical examination findings reveal key focal signs and peritoneal/neurological indicators."
                5 -> "Abnormal vital sign patterns help distinguish inflammatory, metabolic, or hemodynamic instability."
                6 -> "Laboratory tests isolate biochemical, hematological, and serological hallmarks."
                7 -> "Confirmatory diagnostic imaging or testing provides pathognomonic evidence for this condition."
                else -> "Review the clinical findings closely."
            }
        }
    }
}
