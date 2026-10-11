package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CompendiumRepository
import com.example.data.SurgicalCategoryRepository
import com.example.model.CompendiumCategory
import com.example.model.MiniCase
import com.example.model.SubjectChapter
import com.example.ui.theme.MedEmeraldGreen
import com.example.ui.theme.MedGoldScore

enum class CategoryMode(val label: String) {
    MINI_CASES("Surgical Mini Cases"),
    PEARLS_COMPENDIUM("Diagnostic Pearls")
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedSubjectId: String,
    onSubjectSelected: (String) -> Unit,
    selectedChapterId: String?,
    onChapterSelected: (String?) -> Unit,
    solvedMiniCaseIds: Set<String>,
    onStartSolveMiniCase: (MiniCase) -> Unit,
    // Diagnostic Pearls parameters
    selectedCompendiumCategory: CompendiumCategory,
    onCompendiumCategorySelected: (CompendiumCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    var categoryMode by remember { mutableStateOf(CategoryMode.MINI_CASES) }
    val allSubjects = remember { SurgicalCategoryRepository.getAllSubjects() }
    val activeSubject = remember(selectedSubjectId) {
        allSubjects.find { it.id == selectedSubjectId } ?: allSubjects.first()
    }

    // Filter chapters according to search and selection
    val displayedChapters = remember(activeSubject, selectedChapterId, searchQuery) {
        val base = if (selectedChapterId != null) {
            activeSubject.chapters.filter { it.id == selectedChapterId }
        } else {
            activeSubject.chapters
        }

        if (searchQuery.isBlank()) {
            base
        } else {
            val q = searchQuery.trim().lowercase()
            base.mapNotNull { chapter ->
                val matchingCases = chapter.cases.filter { case ->
                    case.title.lowercase().contains(q) ||
                    case.chiefComplaint.lowercase().contains(q) ||
                    case.vignette.lowercase().contains(q) ||
                    case.promptQuestion.lowercase().contains(q) ||
                    case.explanation.lowercase().contains(q) ||
                    chapter.name.lowercase().contains(q)
                }
                if (matchingCases.isNotEmpty() || chapter.name.lowercase().contains(q)) {
                    chapter.copy(cases = if (matchingCases.isNotEmpty()) matchingCases else chapter.cases)
                } else null
            }
        }
    }

    val totalCasesInSubject = remember(activeSubject) { activeSubject.totalCasesCount }
    val solvedCountInSubject = remember(activeSubject, solvedMiniCaseIds) {
        activeSubject.chapters.sumOf { ch -> ch.cases.count { solvedMiniCaseIds.contains(it.id) } }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag("category_screen_root")
    ) {
        // TOP HEADER
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Clinical Categories",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Specialty chapters & high-yield case solving",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MedEmeraldGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "$solvedCountInSubject / $totalCasesInSubject Solved",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // VIEW MODE SELECTOR (Mini Cases vs Diagnostic Pearls)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f), RoundedCornerShape(10.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            CategoryMode.entries.forEach { mode ->
                val isSelected = categoryMode == mode
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent,
                    shadowElevation = if (isSelected) 2.dp else 0.dp,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { categoryMode = mode }
                        .testTag("mode_tab_${mode.name.lowercase()}")
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (mode == CategoryMode.MINI_CASES) Icons.Default.Healing else Icons.Default.Bookmark,
                            contentDescription = null,
                            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = mode.label,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // SEARCH BAR
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = {
                Text(
                    text = if (categoryMode == CategoryMode.MINI_CASES) "Search Breast, Trauma, GI, Endocrine mini cases..." else "Search conditions, triads, labs, red flags...",
                    fontSize = 12.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("category_search_input"),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (categoryMode == CategoryMode.MINI_CASES) {
            // SUBJECT SELECTOR TABS (General Surgery by default, plus other medical subjects)
            ScrollableTabRow(
                selectedTabIndex = allSubjects.indexOfFirst { it.id == selectedSubjectId }.coerceAtLeast(0),
                edgePadding = 0.dp,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary,
                indicator = { tabPositions ->
                    val idx = allSubjects.indexOfFirst { it.id == selectedSubjectId }.coerceAtLeast(0)
                    if (idx < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[idx]),
                            height = 3.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                divider = { HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)) }
            ) {
                allSubjects.forEach { subject ->
                    val isSelected = subject.id == selectedSubjectId
                    Tab(
                        selected = isSelected,
                        onClick = {
                            onSubjectSelected(subject.id)
                            onChapterSelected(null)
                        },
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = subject.shortName,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 13.sp
                                )
                                Surface(
                                    shape = CircleShape,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = subject.totalCasesCount.toString(),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontSize = 10.sp,
                                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        },
                        modifier = Modifier.testTag("tab_subject_${subject.id}")
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // CHAPTER FILTER CHIPS (Inside General Surgery: Breast, Trauma, GI, Endocrine, etc.)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChip(
                    selected = selectedChapterId == null,
                    onClick = { onChapterSelected(null) },
                    label = { Text("All Chapters", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier.testTag("chip_chapter_all")
                )

                activeSubject.chapters.forEach { chapter ->
                    val isSelected = selectedChapterId == chapter.id
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            if (isSelected) onChapterSelected(null) else onChapterSelected(chapter.id)
                        },
                        label = {
                            Text(
                                text = "${chapter.shortName} (${chapter.cases.size})",
                                fontSize = 11.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.testTag("chip_chapter_${chapter.id}")
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // LIST OF CHAPTERS & MINI CASES
            if (displayedChapters.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No mini cases match your search",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Try searching by disease, organ, symptom or chapter keyword",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().testTag("chapters_lazy_column"),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(displayedChapters, key = { it.id }) { chapter ->
                        ChapterSectionCard(
                            chapter = chapter,
                            solvedMiniCaseIds = solvedMiniCaseIds,
                            onStartSolveMiniCase = onStartSolveMiniCase
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        } else {
            // DIAGNOSTIC PEARLS COMPENDIUM VIEW
            CompendiumPearlsView(
                searchQuery = searchQuery,
                selectedCategory = selectedCompendiumCategory,
                onCategorySelected = onCompendiumCategorySelected
            )
        }
    }
}

@Composable
private fun ChapterSectionCard(
    chapter: SubjectChapter,
    solvedMiniCaseIds: Set<String>,
    onStartSolveMiniCase: (MiniCase) -> Unit
) {
    val solvedInThisChapter = chapter.cases.count { solvedMiniCaseIds.contains(it.id) }
    var isExpanded by remember { mutableStateOf(true) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("chapter_card_${chapter.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // CHAPTER HEADER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = when (chapter.iconType) {
                                    "breast" -> Icons.Default.LocalHospital
                                    "trauma" -> Icons.Default.Healing
                                    "gi" -> Icons.Default.MedicalServices
                                    "endocrine" -> Icons.Default.Stars
                                    else -> Icons.Default.LocalHospital
                                },
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = chapter.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (solvedInThisChapter == chapter.cases.size && chapter.cases.isNotEmpty()) {
                                    MedEmeraldGreen.copy(alpha = 0.15f)
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant
                                }
                            ) {
                                Text(
                                    text = "$solvedInThisChapter/${chapter.cases.size} Solved",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (solvedInThisChapter == chapter.cases.size && chapter.cases.isNotEmpty()) {
                                        MedEmeraldGreen
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    },
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = chapter.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Toggle Chapter",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // HIGH YIELD PEARL BANNER
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Stars,
                        contentDescription = null,
                        tint = MedGoldScore,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = chapter.highYieldPearl,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // MINI CASES LIST
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier.padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    chapter.cases.forEach { miniCase ->
                        val isSolved = solvedMiniCaseIds.contains(miniCase.id)
                        MiniCaseItemCard(
                            miniCase = miniCase,
                            isSolved = isSolved,
                            onSolveClick = { onStartSolveMiniCase(miniCase) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MiniCaseItemCard(
    miniCase: MiniCase,
    isSolved: Boolean,
    onSolveClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isSolved) MedEmeraldGreen.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        border = BorderStroke(
            1.dp,
            if (isSolved) MedEmeraldGreen.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .testTag("mini_case_item_${miniCase.id}")
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // CARD TOP ROW: PATIENT PROFILE & STATUS BADGE
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = miniCase.patientProfile,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = miniCase.difficulty,
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                    }
                }

                if (isSolved) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MedEmeraldGreen.copy(alpha = 0.15f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MedEmeraldGreen,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "SOLVED +${miniCase.pointsReward} XP",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MedEmeraldGreen,
                                fontSize = 10.sp
                            )
                        }
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MedGoldScore.copy(alpha = 0.18f)
                    ) {
                        Text(
                            text = "+${miniCase.pointsReward} XP",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // CASE TITLE
            Text(
                text = miniCase.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            // CHIEF COMPLAINT SNIPPET
            Text(
                text = miniCase.chiefComplaint,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(10.dp))

            // BOTTOM ACTION BUTTON
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isSolved) {
                    OutlinedButton(
                        onClick = onSolveClick,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(34.dp).testTag("review_case_btn_${miniCase.id}")
                    ) {
                        Text("Review Debrief", fontSize = 12.sp)
                    }
                } else {
                    Button(
                        onClick = onSolveClick,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.height(34.dp).testTag("solve_case_btn_${miniCase.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Solve Mini Case", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun CompendiumPearlsView(
    searchQuery: String,
    selectedCategory: CompendiumCategory,
    onCategorySelected: (CompendiumCategory) -> Unit
) {
    val searchResults = remember(searchQuery, selectedCategory) {
        CompendiumRepository.search(searchQuery, selectedCategory)
    }

    val expandedMap = remember { mutableStateMapOf<String, Boolean>() }

    Column(modifier = Modifier.fillMaxSize()) {
        // CATEGORY FILTER CHIPS
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CompendiumCategory.entries.forEach { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { onCategorySelected(category) },
                    label = { Text(category.label, fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier.testTag("chip_category_${category.name.lowercase()}")
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (searchResults.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No diagnostic pearls found.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().testTag("compendium_entries_list"),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(searchResults, key = { it.id }) { entry ->
                    val isExpanded = expandedMap[entry.id] ?: false

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandedMap[entry.id] = !isExpanded }
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = entry.title,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = entry.subtitle,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                IconButton(onClick = { expandedMap[entry.id] = !isExpanded }) {
                                    Icon(
                                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                        contentDescription = "Expand",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            if (isExpanded) {
                                Spacer(modifier = Modifier.height(8.dp))
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = entry.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Key Clinical Points:",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                entry.keyPoints.forEach { pt ->
                                    Text(
                                        text = "• $pt",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(vertical = 1.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Clinical Significance: ${entry.clinicalSignificance}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}
