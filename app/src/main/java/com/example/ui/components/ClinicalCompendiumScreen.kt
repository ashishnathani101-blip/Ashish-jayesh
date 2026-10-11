package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CompendiumRepository
import com.example.model.CompendiumCategory
import com.example.model.CompendiumEntry
import com.example.ui.theme.MedGoldScore

@Composable
fun ClinicalCompendiumScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedCategory: CompendiumCategory,
    onCategorySelected: (CompendiumCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val searchResults = remember(searchQuery, selectedCategory) {
        CompendiumRepository.search(searchQuery, selectedCategory)
    }

    val expandedMap = remember { mutableStateMapOf<String, Boolean>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .testTag("clinical_category_screen")
    ) {
        // TOP HEADER
        Column {
            Text(
                text = "Clinical Categories",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Browse high-yield diagnostic pearls, condition criteria & radiological signs by specialty",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // SEARCH BAR
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = { Text("Search conditions, triads, labs, red flags...", fontSize = 13.sp) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear search",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("compendium_search_input"),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        // CATEGORY FILTER CHIPS (Horizontal Scrollable)
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

        Spacer(modifier = Modifier.height(12.dp))

        // RESULTS LIST
        if (searchResults.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "No clinical entries found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Try searching for terms like 'Beck', 'Appendix', 'Platelet', or 'Headache'",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(searchResults, key = { it.id }) { entry ->
                    val isExpanded = expandedMap[entry.id] ?: false
                    CompendiumCard(
                        entry = entry,
                        isExpanded = isExpanded,
                        onToggleExpand = {
                            expandedMap[entry.id] = !isExpanded
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun CompendiumCard(
    entry: CompendiumEntry,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleExpand() }
            .testTag("compendium_card_${entry.id}"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // TOP BADGES
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = when (entry.category) {
                        CompendiumCategory.CLASSIC_TRIADS -> Color(0xFFEDE9FE)
                        CompendiumCategory.RED_FLAGS -> Color(0xFFFFE4E6)
                        CompendiumCategory.DIAGNOSTIC_PEARLS -> Color(0xFFFEF3C7)
                        CompendiumCategory.LAB_RANGES -> Color(0xFFE0F2FE)
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }
                ) {
                    Text(
                        text = entry.category.label.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (entry.category) {
                            CompendiumCategory.CLASSIC_TRIADS -> Color(0xFF6D28D9)
                            CompendiumCategory.RED_FLAGS -> Color(0xFFBE123C)
                            CompendiumCategory.DIAGNOSTIC_PEARLS -> Color(0xFF92400E)
                            CompendiumCategory.LAB_RANGES -> Color(0xFF0369A1)
                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = entry.specialty,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // TITLE & SUBTITLE
            Text(
                text = entry.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = entry.subtitle,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 1.dp)
            )

            Text(
                text = entry.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )

            // EXPANDED SECTION
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "KEY CLINICAL POINTS",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    entry.keyPoints.forEach { point ->
                        Row(
                            modifier = Modifier.padding(top = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "• ",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = point,
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "CLINICAL ACTION & SIGNIFICANCE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MedGoldScore
                            )
                            Text(
                                text = entry.clinicalSignificance,
                                fontSize = 11.sp,
                                lineHeight = 15.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
