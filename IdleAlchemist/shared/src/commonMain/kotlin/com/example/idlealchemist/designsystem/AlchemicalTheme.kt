package com.example.idlealchemist.designsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Defines the Alchemical workshop's purple-shifted abyssal dark theme. */
private val AlchemicalColors = darkColorScheme(
    background = Color(0xFF0E091B), // --bg-abyssal
    onBackground = Color(0xFFEADCC9), // --text-main
    surface = Color(0xFF1A132C), // --surface-panel
    onSurface = Color(0xFFEADCC9), // --text-main
    surfaceVariant = Color(0xFF271C3D), // --surface-raised
    onSurfaceVariant = Color(0xFF8E929E), // --text-muted
    outline = Color(0xFF382E5C), // --border-runic
    outlineVariant = Color(0xFF382E5C),
    primary = Color(0xFFEBB434), // --accent-gold
    onPrimary = Color(0xFF0E091B),
    primaryContainer = Color(0xFF382E5C),
    onPrimaryContainer = Color(0xFFEBB434),
    secondary = Color(0xFF38D6D1), // --accent-cyan
    onSecondary = Color(0xFF0E091B),
    secondaryContainer = Color(0xFF1A2E2D),
    onSecondaryContainer = Color(0xFF38D6D1),
    tertiary = Color(0xFF9F5DE2), // --accent-witch
    onTertiary = Color(0xFFEADCC9),
    tertiaryContainer = Color(0xFF2C1E4A),
    onTertiaryContainer = Color(0xFFC399F5),
    error = Color(0xFFE05338), // --accent-ember
    onError = Color(0xFFEADCC9),
    errorContainer = Color(0xFF4A1F18),
    onErrorContainer = Color(0xFFF39683),
)

/** Defines typography matching the medieval serif display and pixel UI design system. */
private val AlchemicalTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 38.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 1.5.sp,
    ),
)

/** Defines consistent rounded shapes for workshop components. */
private val AlchemicalShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp),
)

/**
 * Applies the shared Material 3 appearance with abyssal purple tones and thematic highlights.
 *
 * @param content Application content displayed inside the theme.
 */
@Composable
fun AlchemicalTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = AlchemicalColors,
        typography = AlchemicalTypography,
        shapes = AlchemicalShapes,
        content = content,
    )
}
