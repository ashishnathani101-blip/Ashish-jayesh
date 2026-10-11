package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.CaseTab
import com.example.model.LabItem
import com.example.model.ParamStatus
import com.example.model.RadiologyReport
import com.example.model.VitalItem
import com.example.ui.theme.MedAlertTertiary
import com.example.ui.theme.MedGoldScore
import com.example.ui.theme.MedGoldScoreContainer
import com.example.ui.theme.MedNormalGreen
import com.example.ui.theme.MedWarningOrange

@Composable
fun MainCaseDisplay(
    currentTab: CaseTab,
    isUnlocked: Boolean,
    isHintUsed: Boolean = false,
    activeHintText: String? = null,
    onUseHint: () -> Unit = {},
    isAnswerRevealed: Boolean = false,
    onRevealAnswer: () -> Unit = {},
    onNavigateTab: ((Int) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .testTag("main_case_display")
    ) {
        if (!isUnlocked) {
            // Locked Tab Placeholder
            LockedTabState(tab = currentTab)
        } else {
            // Minimal Tab Header Card
            TabHeaderCard(
                tab = currentTab,
                isHintUsed = isHintUsed,
                onUseHint = onUseHint
            )

            // Step Hint Callout Box if hint was requested/revealed
            if (isHintUsed && activeHintText != null) {
                Spacer(modifier = Modifier.height(8.dp))
                StepHintCallout(hintText = activeHintText, tabId = currentTab.id)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Dedicated Radiology & X-Ray Viewer (Prioritized for maximum visibility in X-ray cases)
            if (currentTab.radiology != null || currentTab.isXrayStudy) {
                RadiologyReportCard(
                    radiology = currentTab.radiology,
                    isXrayStudy = currentTab.isXrayStudy
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Vitals Signs & Hemodynamics Panel (if available on this tab)
            currentTab.vitals?.let { vitalsList ->
                VitalsPanel(vitals = vitalsList)
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Clinical Findings & Notes Details Card
            if (currentTab.details.isNotEmpty()) {
                DetailsCard(details = currentTab.details)
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Labs Diagnostics Display (if available on this tab)
            currentTab.labs?.let { labsList ->
                LabsPanel(labs = labsList)
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Diagnostic Note Callout (if available)
            currentTab.diagnosticNote?.let { note ->
                DiagnosticNoteCard(note = note)
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Clue Tab Slide Navigation Footer (Auto-slides across the 7 tabs)
            if (onNavigateTab != null && currentTab.id < 7) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateTab(currentTab.id) }
                        .testTag("slide_to_next_clue_card")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Next Clinical Clue Available",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Slide to Tab ${currentTab.id + 1} →",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Button(
                            onClick = { onNavigateTab(currentTab.id) },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("Slide to Tab ${currentTab.id + 1}", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // IF THIS IS TAB 7 (THE VERY LAST TAB), OFFER ANSWER REVEAL OPTION
            if (currentTab.id == 7 && !isAnswerRevealed) {
                FinalStepAnswerRevealCard(onRevealAnswer = onRevealAnswer)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun TabHeaderCard(
    tab: CaseTab,
    isHintUsed: Boolean,
    onUseHint: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "TAB ${tab.id} / 7",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 10.sp
                            ),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = tab.title,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Step Hint Button right inside the tab header
                OutlinedButton(
                    onClick = onUseHint,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(
                        1.dp,
                        if (isHintUsed) MaterialTheme.colorScheme.outlineVariant else MedGoldScore.copy(alpha = 0.8f)
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isHintUsed) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                        else MedGoldScoreContainer.copy(alpha = 0.5f)
                    ),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                    modifier = Modifier
                        .testTag("tab_header_hint_button_${tab.id}")
                        .height(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = "Hint",
                        tint = if (isHintUsed) MaterialTheme.colorScheme.onSurfaceVariant else MedGoldScore,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = if (isHintUsed) "Hint" else "Hint (-50 pts)",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        ),
                        color = if (isHintUsed) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = tab.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp
            )
        }
    }
}

/**
 * High-Yield Radiology / X-Ray Inspection Viewer
 * Displays authentic medical imaging PACS illuminator, interactive contrast controls,
 * anatomical tags, and radiologist findings.
 */
@Composable
private fun RadiologyReportCard(
    radiology: RadiologyReport?,
    isXrayStudy: Boolean
) {
    var isInverted by remember { mutableStateOf(false) }
    var zoomScale by remember { mutableFloatStateOf(1.0f) }
    var selectedSignTag by remember { mutableStateOf<String?>(null) }

    val report = radiology ?: RadiologyReport(
        modality = "Diagnostic Chest Radiograph (X-Ray)",
        projection = "Posteroanterior (PA) Erect View",
        findings = listOf(
            "Pleural Reflection" to "Thin visceral pleural line visible in apical and lateral hemithorax.",
            "Parenchyma" to "Absent vascular markings peripheral to visceral line.",
            "Mediastinum" to "Central tracheobronchial tree without deviation."
        ),
        impression = "Pneumothorax confirmed on upright radiographic scan.",
        keySigns = listOf("Visceral Pleural Line", "Peripheral Hyperlucency", "Absent Vascular Markings")
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("radiology_report_card")
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header Row: Modality & Priority Tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFF0F172A)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MedicalServices,
                            contentDescription = "Radiology",
                            tint = Color(0xFF38BDF8),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = report.modality,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = report.projection,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (report.isUrgent) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.errorContainer
                    ) {
                        Text(
                            text = "STAT STUDY",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Black,
                                fontSize = 9.sp
                            ),
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // PACS MEDICAL DIGITAL FILM VIEWER (Negative Illuminator Box)
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (isInverted) Color(0xFFE2E8F0) else Color(0xFF020617),
                border = BorderStroke(1.5.dp, Color(0xFF334155)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("pacs_viewer_box")
            ) {
                Column {
                    // PACS Top Toolbar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF0F172A).copy(alpha = 0.9f))
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "PACS STATION • CXR",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    letterSpacing = 0.5.sp
                                ),
                                color = Color(0xFF94A3B8)
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Invert Film Button
                            IconButton(
                                onClick = { isInverted = !isInverted },
                                modifier = Modifier.size(26.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Contrast,
                                    contentDescription = "Invert Contrast",
                                    tint = if (isInverted) Color(0xFF38BDF8) else Color(0xFFCBD5E1),
                                    modifier = Modifier.size(15.dp)
                                )
                            }

                            // Zoom In/Out Button
                            IconButton(
                                onClick = {
                                    zoomScale = if (zoomScale >= 1.4f) 1.0f else zoomScale + 0.3f
                                },
                                modifier = Modifier.size(26.dp)
                            ) {
                                Icon(
                                    imageVector = if (zoomScale > 1.0f) Icons.Default.ZoomOut else Icons.Default.ZoomIn,
                                    contentDescription = "Zoom Radiograph",
                                    tint = if (zoomScale > 1.0f) Color(0xFF38BDF8) else Color(0xFFCBD5E1),
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                    }

                    // RADIOGRAPHIC FILM VIEWPORT
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                            .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        // Display X-Ray image asset
                        val imageRes = report.imageDrawableRes ?: R.drawable.img_chest_xray
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = "Chest Radiograph Medical X-Ray",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer(
                                    scaleX = zoomScale,
                                    scaleY = zoomScale
                                )
                        )

                        // Anatomical Marker "R" (Right Patient Side)
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xCC000000),
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "R",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 14.sp
                                ),
                                color = Color(0xFF38BDF8),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        // Status Tag Indicator Overlay
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xCC000000),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = if (zoomScale > 1.0f) "${(zoomScale * 100).toInt()}% ZOOM" else "PA ERECT",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp
                                ),
                                color = Color(0xFFE2E8F0),
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            // KEY RADIOGRAPHIC SIGNS CHIPS
            if (report.keySigns.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    report.keySigns.forEach { sign ->
                        val isSelected = selectedSignTag == sign
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                            ),
                            modifier = Modifier.clickable {
                                selectedSignTag = if (isSelected) null else sign
                            }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) Color.White else Color(0xFF0284C7))
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = sign,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    ),
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            // STRUCTURED RADIOLOGICAL FINDINGS
            if (report.findings.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Radiologist Findings & Measurements",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        report.findings.forEachIndexed { index, (label, finding) ->
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 3.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.weight(0.35f)
                                    )
                                    Text(
                                        text = finding,
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.weight(0.65f)
                                    )
                                }
                                if (index < report.findings.size - 1) {
                                    HorizontalDivider(
                                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f),
                                        thickness = 0.5.dp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // OFFICIAL RADIOLOGIST IMPRESSION
            Spacer(modifier = Modifier.height(6.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Impression",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "OFFICIAL RADIOLOGICAL IMPRESSION",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 0.4.sp,
                                fontSize = 9.sp
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = report.impression,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 17.sp
                            ),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StepHintCallout(hintText: String, tabId: Int) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MedGoldScoreContainer.copy(alpha = 0.55f),
        border = BorderStroke(1.dp, MedGoldScore.copy(alpha = 0.6f)),
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("step_hint_callout_$tabId")
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = MedGoldScore,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "CLINICAL HINT (STEP $tabId)",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.6.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = hintText,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    lineHeight = 17.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun FinalStepAnswerRevealCard(onRevealAnswer: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.35f)),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("final_step_reveal_card")
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Visibility,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Final Clue (Tab 7) • Answer Reveal",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "All clinical clues are unlocked. Tap below to confirm the definitive diagnosis.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onRevealAnswer,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .testTag("tab7_card_reveal_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Visibility,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Reveal Case Answer",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
private fun DetailsCard(details: List<Pair<String, String>>) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Assignment,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Clinical Findings & History Notes",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            details.forEachIndexed { index, (label, value) ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(0.40f)
                        )
                        Text(
                            text = value,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(0.60f)
                        )
                    }

                    if (index < details.size - 1) {
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.25f),
                            thickness = 0.5.dp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Enhanced Vitals Signs & Hemodynamics Panel
 */
@Composable
private fun VitalsPanel(vitals: List<VitalItem>) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Speed,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Vital Signs & Hemodynamics",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Hemodynamic monitor active indicator
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF22C55E))
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "LIVE TELEMETRY",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 9.sp
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                vitals.forEach { item ->
                    val statusColor = when (item.status) {
                        ParamStatus.NORMAL -> MedNormalGreen
                        ParamStatus.HIGH, ParamStatus.ABNORMAL -> MedAlertTertiary
                        ParamStatus.LOW -> MedWarningOrange
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.name,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "${item.value} ${item.unit}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = statusColor
                                    )
                                )

                                if (item.status != ParamStatus.NORMAL) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = statusColor.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = item.status.name,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.ExtraBold,
                                                fontSize = 9.sp,
                                                color = statusColor
                                            ),
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LabsPanel(labs: List<LabItem>) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Science,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Laboratory Diagnostics & Panels",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            labs.forEachIndexed { _, lab ->
                val statusColor = when (lab.status) {
                    ParamStatus.NORMAL -> MedNormalGreen
                    ParamStatus.HIGH, ParamStatus.ABNORMAL -> MedAlertTertiary
                    ParamStatus.LOW -> MedWarningOrange
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(0.45f)) {
                        Text(
                            text = lab.testName,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Row(
                        modifier = Modifier.weight(0.3f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${lab.result} ${lab.unit}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = statusColor
                            )
                        )
                    }

                    Text(
                        text = "Ref: ${lab.normalRange}",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(0.25f)
                    )
                }
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f),
                    thickness = 0.5.dp
                )
            }
        }
    }
}

@Composable
private fun DiagnosticNoteCard(note: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = note,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                ),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun LockedTabState(tab: CaseTab) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked Tab",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tab ${tab.id}: ${tab.title} is Locked",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Enter a diagnosis guess below. If your guess is incorrect, the next clue tab will automatically unlock!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
