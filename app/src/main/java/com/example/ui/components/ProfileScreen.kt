package com.example.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MedicalInformation
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.ClinicianAchievement
import com.example.model.ClinicianProfile
import com.example.model.UserRole
import com.example.ui.theme.MedGoldScore
import com.example.ui.theme.MedGoldScoreContainer
import com.example.ui.theme.MedNormalGreen

@Composable
fun ProfileScreen(
    profile: ClinicianProfile,
    totalScore: Int,
    casesSolved: Int,
    totalEncounters: Int,
    currentStreak: Int,
    bestStreak: Int,
    clinicianRank: String,
    clinicianTierLevel: Int,
    nextRankThreshold: Int,
    achievements: List<ClinicianAchievement>,
    hintTokensCount: Int = 0,
    onUpdateProfile: (name: String, title: String, department: String, institution: String) -> Unit = { _, _, _, _ -> },
    onUpdateFullProfile: (
        name: String,
        role: UserRole,
        doctorField: String,
        doctorExperience: String,
        hospitalAffiliation: String,
        studentYear: String,
        studentBranch: String,
        studentUniversity: String,
        avatarUri: String?,
        avatarPreset: String
    ) -> Unit,
    onExitApp: () -> Unit = {},
    onPreviewExitAnimation: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showEditDialog by remember { mutableStateOf(false) }

    val accuracyPercent = if (totalEncounters > 0) (casesSolved * 100) / totalEncounters else 0
    val progressFloat = (totalScore.toFloat() / nextRankThreshold.toFloat()).coerceIn(0f, 1f)

    // Photo picker launcher for direct avatar click
    val directPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            onUpdateFullProfile(
                profile.name,
                profile.role,
                profile.doctorField,
                profile.doctorExperience,
                profile.hospitalAffiliation,
                profile.studentYear,
                profile.studentBranch,
                profile.studentUniversity,
                uri.toString(),
                profile.avatarPreset
            )
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .testTag("profile_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // TOP HEADER
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Clinician Profile",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = if (profile.role == UserRole.STUDENT) "Medical Student Portal" else "Physician Diagnostic Credentials",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MedGoldScoreContainer,
                        border = BorderStroke(1.dp, MedGoldScore.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = MedGoldScore,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "$hintTokensCount Tokens",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E)
                            )
                        }
                    }

                    IconButton(
                        onClick = { showEditDialog = true },
                        modifier = Modifier.testTag("edit_profile_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Profile",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        // CLINICIAN ID CARD (ADAPTIVE FOR STUDENTS & DOCTORS)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // AVATAR WITH PHOTO PICKER BADGE
                        Box(
                            modifier = Modifier
                                .size(68.dp)
                                .clickable {
                                    directPhotoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                        ) {
                            if (profile.avatarUri != null) {
                                AsyncImage(
                                    model = profile.avatarUri,
                                    contentDescription = "Profile Picture",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                )
                            } else {
                                ProfileAvatarBadge(
                                    preset = profile.avatarPreset,
                                    role = profile.role,
                                    name = profile.name,
                                    size = 64.dp
                                )
                            }

                            // Camera overlay icon
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primary,
                                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.surface),
                                modifier = Modifier
                                    .size(22.dp)
                                    .align(Alignment.BottomEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = "Change photo",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier
                                        .padding(3.dp)
                                        .fillMaxSize()
                                )
                            }
                        }

                        // NAME, ROLE & DETAILS
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = profile.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            // ROLE BADGE (Student vs Doctor)
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (profile.role == UserRole.STUDENT) {
                                    MaterialTheme.colorScheme.secondaryContainer
                                } else {
                                    MaterialTheme.colorScheme.primaryContainer
                                },
                                modifier = Modifier.padding(vertical = 3.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = if (profile.role == UserRole.STUDENT) Icons.Default.School else Icons.Default.LocalHospital,
                                        contentDescription = null,
                                        tint = if (profile.role == UserRole.STUDENT) {
                                            MaterialTheme.colorScheme.onSecondaryContainer
                                        } else {
                                            MaterialTheme.colorScheme.onPrimaryContainer
                                        },
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Text(
                                        text = if (profile.role == UserRole.STUDENT) "Medical Student" else "Doctor / Physician",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (profile.role == UserRole.STUDENT) {
                                            MaterialTheme.colorScheme.onSecondaryContainer
                                        } else {
                                            MaterialTheme.colorScheme.onPrimaryContainer
                                        }
                                    )
                                }
                            }

                            // ROLE-SPECIFIC SUBTITLES
                            if (profile.role == UserRole.STUDENT) {
                                Text(
                                    text = "Year: ${profile.studentYear} • ${profile.studentBranch}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = profile.studentUniversity,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            } else {
                                Text(
                                    text = "${profile.doctorField} (${profile.doctorExperience})",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = profile.hospitalAffiliation,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(12.dp))

                    // RANK & XP PROGRESS
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = MedGoldScore,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = clinicianRank,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MedGoldScoreContainer
                        ) {
                            Text(
                                text = "Tier $clinicianTierLevel",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = { progressFloat },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = MedGoldScore,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "$totalScore XP total",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Next tier: $nextRankThreshold XP",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // QUICK ACTION: EDIT PROFILE / CHANGE ROLE BUTTON
        item {
            OutlinedButton(
                onClick = { showEditDialog = true },
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (profile.role == UserRole.STUDENT) "Edit Student Details or Switch to Doctor" else "Edit Doctor Field & Experience",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // PERFORMANCE METRICS GRID
        item {
            Text(
                text = "CLINICAL PERFORMANCE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    title = "Solved",
                    value = "$casesSolved",
                    subtitle = "of $totalEncounters cases",
                    icon = Icons.Default.CheckCircle,
                    iconTint = MedNormalGreen,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Accuracy",
                    value = "$accuracyPercent%",
                    subtitle = "first-pass rate",
                    icon = Icons.Default.Speed,
                    iconTint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Streak",
                    value = "$currentStreak",
                    subtitle = "Best: $bestStreak",
                    icon = Icons.Default.TrendingUp,
                    iconTint = MedGoldScore,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // CLINICAL ACHIEVEMENTS
        item {
            Text(
                text = "MILESTONES & ACHIEVEMENTS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 4.dp, top = 6.dp)
            )
        }

        items(achievements) { achievement ->
            AchievementCard(achievement = achievement)
        }

        // MEDFLIX CINEMATIC EXPERIENCE & APP EXIT
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("medflix_experience_card"),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFFFE4E6),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.MedicalServices,
                                    contentDescription = null,
                                    tint = Color(0xFFE11D48),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "Medflix Experience",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Cinematic medical animations & session control",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = onPreviewExitAnimation,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("preview_exit_animation_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayCircle,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Preview Outro",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Button(
                            onClick = onExitApp,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE11D48)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("exit_medflix_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Exit Medflix",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }

    if (showEditDialog) {
        EditProfileDialog(
            currentProfile = profile,
            onDismiss = { showEditDialog = false },
            onSave = { name, role, doctorField, doctorExp, hospital, studentYear, studentBranch, studentUni, avatarUri, avatarPreset ->
                onUpdateFullProfile(
                    name,
                    role,
                    doctorField,
                    doctorExp,
                    hospital,
                    studentYear,
                    studentBranch,
                    studentUni,
                    avatarUri,
                    avatarPreset
                )
                showEditDialog = false
            }
        )
    }
}

@Composable
fun ProfileAvatarBadge(
    preset: String,
    role: UserRole,
    name: String,
    size: androidx.compose.ui.unit.Dp
) {
    val bgColor = if (role == UserRole.STUDENT) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primaryContainer
    val iconColor = if (role == UserRole.STUDENT) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onPrimaryContainer

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        when (preset) {
            "doctor_male", "doctor_female" -> {
                Icon(
                    imageVector = Icons.Default.LocalHospital,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(size * 0.5f)
                )
            }
            "student_male", "student_female" -> {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(size * 0.5f)
                )
            }
            "stethoscope" -> {
                Icon(
                    imageVector = Icons.Default.MedicalServices,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(size * 0.5f)
                )
            }
            else -> {
                Text(
                    text = name.take(2).uppercase(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = iconColor
                )
            }
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AchievementCard(achievement: ClinicianAchievement) {
    val isUnlocked = achievement.isUnlocked

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            1.dp,
            if (isUnlocked) MaterialTheme.colorScheme.outlineVariant else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = if (isUnlocked) MedGoldScoreContainer else MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.size(42.dp)
            ) {
                Icon(
                    imageVector = if (isUnlocked) Icons.Default.EmojiEvents else Icons.Default.Lock,
                    contentDescription = null,
                    tint = if (isUnlocked) MedGoldScore else MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(22.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = achievement.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isUnlocked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (isUnlocked) MedNormalGreen.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = achievement.progressText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isUnlocked) MedNormalGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(
                    text = achievement.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun EditProfileDialog(
    currentProfile: ClinicianProfile,
    onDismiss: () -> Unit,
    onSave: (
        name: String,
        role: UserRole,
        doctorField: String,
        doctorExperience: String,
        hospitalAffiliation: String,
        studentYear: String,
        studentBranch: String,
        studentUniversity: String,
        avatarUri: String?,
        avatarPreset: String
    ) -> Unit
) {
    var selectedRole by remember { mutableStateOf(currentProfile.role) }
    var name by remember { mutableStateOf(currentProfile.name) }
    var avatarUri by remember { mutableStateOf(currentProfile.avatarUri) }
    var avatarPreset by remember { mutableStateOf(currentProfile.avatarPreset) }

    // Doctor fields
    var doctorField by remember { mutableStateOf(currentProfile.doctorField) }
    var doctorExperience by remember { mutableStateOf(currentProfile.doctorExperience) }
    var hospitalAffiliation by remember { mutableStateOf(currentProfile.hospitalAffiliation) }

    // Student fields
    var studentYear by remember { mutableStateOf(currentProfile.studentYear) }
    var studentBranch by remember { mutableStateOf(currentProfile.studentBranch) }
    var studentUniversity by remember { mutableStateOf(currentProfile.studentUniversity) }

    // Photo picker launcher
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            avatarUri = uri.toString()
        }
    }

    val yearPresets = listOf("1st Year", "2nd Year", "3rd Year", "4th Year", "Intern")
    val doctorExpPresets = listOf("1-3 Years", "3-5 Years", "5-10 Years", "10+ Years")
    val doctorFieldPresets = listOf("Emergency Medicine", "Internal Medicine", "Cardiology", "Neurology", "Pediatrics", "General Surgery")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = "Edit Profile & Credentials",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Customize for Medical Student or Doctor practice",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(440.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // ROLE SELECTION TOGGLE (Student vs Doctor)
                item {
                    Text(
                        text = "I am a:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = selectedRole == UserRole.STUDENT,
                            onClick = { selectedRole = UserRole.STUDENT },
                            label = { Text("👨‍🎓 Medical Student") },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.School, contentDescription = null, modifier = Modifier.size(16.dp))
                            },
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = selectedRole == UserRole.DOCTOR,
                            onClick = { selectedRole = UserRole.DOCTOR },
                            label = { Text("🩺 Doctor / Physician") },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.LocalHospital, contentDescription = null, modifier = Modifier.size(16.dp))
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // PROFILE PHOTO SECTION
                item {
                    Text(
                        text = "Profile Picture",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            if (avatarUri != null) {
                                AsyncImage(
                                    model = avatarUri,
                                    contentDescription = "Chosen Avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                ProfileAvatarBadge(
                                    preset = avatarPreset,
                                    role = selectedRole,
                                    name = name,
                                    size = 54.dp
                                )
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Button(
                                onClick = {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                                modifier = Modifier.height(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddPhotoAlternate,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (avatarUri != null) "Change Photo" else "Pick Photo",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }

                            if (avatarUri != null) {
                                Text(
                                    text = "Reset to preset",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .clickable { avatarUri = null }
                                        .padding(top = 2.dp)
                                )
                            }
                        }
                    }

                    // Avatar presets
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Or choose avatar preset:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        val presets = listOf(
                            "doctor_male" to "👨‍⚕️ Doc M",
                            "doctor_female" to "👩‍⚕️ Doc F",
                            "student_male" to "👨‍🎓 Med M",
                            "student_female" to "👩‍🎓 Med F",
                            "stethoscope" to "🩺 Steth"
                        )
                        items(presets) { (id, label) ->
                            FilterChip(
                                selected = avatarPreset == id && avatarUri == null,
                                onClick = {
                                    avatarPreset = id
                                    avatarUri = null
                                },
                                label = { Text(label, fontSize = 11.sp) }
                            )
                        }
                    }
                }

                // COMMON FIELD: FULL NAME
                item {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(if (selectedRole == UserRole.STUDENT) "Student Name" else "Doctor / Clinician Name") },
                        placeholder = { Text(if (selectedRole == UserRole.STUDENT) "e.g. Ashish Nathani" else "e.g. Dr. Ashish Nathani, MD") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // STUDENT-SPECIFIC DETAILS
                if (selectedRole == UserRole.STUDENT) {
                    item {
                        Text(
                            text = "Year of Study",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(yearPresets) { yr ->
                                FilterChip(
                                    selected = studentYear == yr,
                                    onClick = { studentYear = yr },
                                    label = { Text(yr, fontSize = 11.sp) }
                                )
                            }
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = studentBranch,
                            onValueChange = { studentBranch = it },
                            label = { Text("Branch / Program") },
                            placeholder = { Text("e.g. MBBS, Clinical Medicine, MD Candidate") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = studentUniversity,
                            onValueChange = { studentUniversity = it },
                            label = { Text("Medical College / University") },
                            placeholder = { Text("e.g. Johns Hopkins, State Medical University") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // DOCTOR-SPECIFIC DETAILS
                if (selectedRole == UserRole.DOCTOR) {
                    item {
                        Text(
                            text = "Medical Field / Specialty",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(doctorFieldPresets) { fld ->
                                FilterChip(
                                    selected = doctorField == fld,
                                    onClick = { doctorField = fld },
                                    label = { Text(fld, fontSize = 11.sp) }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = doctorField,
                            onValueChange = { doctorField = it },
                            label = { Text("Specialty / Department") },
                            placeholder = { Text("e.g. Emergency & Internal Medicine") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
                        Text(
                            text = "Years of Experience",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(doctorExpPresets) { exp ->
                                FilterChip(
                                    selected = doctorExperience == exp,
                                    onClick = { doctorExperience = exp },
                                    label = { Text(exp, fontSize = 11.sp) }
                                )
                            }
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = hospitalAffiliation,
                            onValueChange = { hospitalAffiliation = it },
                            label = { Text("Hospital / Healthcare Institution") },
                            placeholder = { Text("e.g. Metropolitan General Hospital") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        name,
                        selectedRole,
                        doctorField,
                        doctorExperience,
                        hospitalAffiliation,
                        studentYear,
                        studentBranch,
                        studentUniversity,
                        avatarUri,
                        avatarPreset
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Save Profile")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
