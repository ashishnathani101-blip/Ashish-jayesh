package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * Signature Medflix Medical Exit / Outro Animation
 * Inspired by the Netflix iconic ribbon outro, reimagined as a clinical
 * vital electrocardiogram (ECG) pulse transforming into the glowing "M" Medflix insignia.
 */
@Composable
fun MedflixExitAnimation(
    onExitComplete: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Master animation progress from 0f to 1f over 1400ms
    val animProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1400, easing = FastOutSlowInEasing)
        )
        delay(150)
        onExitComplete()
    }

    val progress = animProgress.value

    // Dark cinematic theater backdrop echoing Netflix's dramatic studio feel
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF090D16))
            .clickable(enabled = false) {}
            .testTag("medflix_exit_animation_overlay"),
        contentAlignment = Alignment.Center
    ) {
        // Subtle ambient radial glow behind the emblem
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val glowRadius = size.width * (0.3f + progress * 0.4f)
            val glowAlpha = if (progress < 0.7f) (progress / 0.7f) * 0.45f else ((1f - progress) / 0.3f) * 0.45f

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFE11D48).copy(alpha = glowAlpha),
                        Color(0xFF0D9488).copy(alpha = glowAlpha * 0.5f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = glowRadius
                ),
                radius = glowRadius,
                center = center
            )
        }

        // Animated ECG waveform and "M" Medflix Ribbon
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .scale(0.85f + (progress * 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // 1. Draw glowing ECG Cardiogram baseline pulse
                    val ecgPath = Path().apply {
                        val centerY = h * 0.65f
                        moveTo(0f, centerY)
                        lineTo(w * 0.25f, centerY)
                        // P-wave
                        lineTo(w * 0.32f, centerY - 14f)
                        lineTo(w * 0.38f, centerY)
                        // Q-wave dip
                        lineTo(w * 0.43f, centerY + 10f)
                        // R-wave sharp spike (Heartbeat peak)
                        lineTo(w * 0.50f, centerY - 95f)
                        // S-wave deep descent
                        lineTo(w * 0.56f, centerY + 45f)
                        // T-wave recovery
                        lineTo(w * 0.64f, centerY - 22f)
                        lineTo(w * 0.72f, centerY)
                        lineTo(w, centerY)
                    }

                    // Stroke color interpolates from energetic Medflix red to cool stable teal
                    val ecgColor = if (progress < 0.5f) Color(0xFFE11D48) else Color(0xFF14B8A6)
                    val strokeAlpha = ((1f - (progress - 0.4f).coerceAtLeast(0f) * 2f)).coerceIn(0f, 1f)

                    if (strokeAlpha > 0f) {
                        drawPath(
                            path = ecgPath,
                            color = ecgColor.copy(alpha = strokeAlpha),
                            style = Stroke(
                                width = 4.5.dp.toPx(),
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }

                    // 2. Medflix "M" Signature Medical Ribbon (Appears like the Netflix N)
                    val mAlpha = ((progress - 0.25f) / 0.4f).coerceIn(0f, 1f)
                    val mScale = ((progress - 0.25f) / 0.75f).coerceIn(0f, 1f)

                    if (mAlpha > 0f) {
                        val mWidth = w * 0.55f * (0.8f + mScale * 0.2f)
                        val mHeight = h * 0.65f * (0.8f + mScale * 0.2f)
                        val left = (w - mWidth) / 2f
                        val top = (h - mHeight) / 2f
                        val right = left + mWidth
                        val bottom = top + mHeight

                        // Left vertical pillar of "M" (Netflix crimson ribbon)
                        val leftPillar = Path().apply {
                            moveTo(left, bottom)
                            lineTo(left, top)
                            lineTo(left + mWidth * 0.24f, top)
                            lineTo(left + mWidth * 0.24f, bottom)
                            close()
                        }
                        drawPath(
                            path = leftPillar,
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFFE11D48), Color(0xFF9F1239))
                            ),
                            alpha = mAlpha
                        )

                        // Right vertical pillar of "M"
                        val rightPillar = Path().apply {
                            moveTo(right - mWidth * 0.24f, bottom)
                            lineTo(right - mWidth * 0.24f, top)
                            lineTo(right, top)
                            lineTo(right, bottom)
                            close()
                        }
                        drawPath(
                            path = rightPillar,
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFFE11D48), Color(0xFF9F1239))
                            ),
                            alpha = mAlpha
                        )

                        // Center diagonal ribbon v-shape (overlapping Netflix style with clinical teal crossover)
                        val centerRibbon = Path().apply {
                            moveTo(left, top)
                            lineTo(left + mWidth * 0.24f, top)
                            lineTo(w * 0.5f + mWidth * 0.10f, bottom)
                            lineTo(w * 0.5f - mWidth * 0.10f, bottom)
                            close()
                        }
                        drawPath(
                            path = centerRibbon,
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFFF2E56), Color(0xFF0F766E))
                            ),
                            alpha = mAlpha
                        )

                        val centerRibbonRight = Path().apply {
                            moveTo(right, top)
                            lineTo(right - mWidth * 0.24f, top)
                            lineTo(w * 0.5f - mWidth * 0.10f, bottom)
                            lineTo(w * 0.5f + mWidth * 0.10f, bottom)
                            close()
                        }
                        drawPath(
                            path = centerRibbonRight,
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFF0D9488), Color(0xFFE11D48))
                            ),
                            alpha = mAlpha
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Cinematic MEDFLIX typography
            val textAlpha = ((progress - 0.35f) / 0.35f).coerceIn(0f, 1f)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.alpha(textAlpha)
            ) {
                Text(
                    text = "M E D F L I X",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 8.sp
                    ),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color(0xFFE11D48),
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "SESSION COMPLETE • VITALS STABLE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp,
                        color = Color(0xFF2DD4BF)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Clinical cases synchronized • See you next shift, Clinician",
                    fontSize = 10.sp,
                    color = Color(0xFF94A3B8)
                )
            }
        }

        // Cancel button in case user wants to abort exit
        IconButton(
            onClick = onCancel,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp)
                .size(36.dp)
                .background(Color.White.copy(alpha = 0.15f), CircleShape)
                .testTag("medflix_exit_cancel_button")
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cancel Exit",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
