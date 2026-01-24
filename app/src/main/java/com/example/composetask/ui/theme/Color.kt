package com.example.composetask.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val black = Color(0xFF121212)
val orange = Color(0xFFA04000)
val lightGray = Color(0xFFE5E5E5)
val gray = Color(0xFF2C2C2C)
val red = Color(0xFFFF3040)
val white = Color(0xFFFFFFFF)
val mediumBlue = Color(0xFF0095F6)
val blue = Color(0xFF005BB5)
val whiteVariant = Color(0xFFFEFFF7)

val LightBorderGray = Color(0xFFE5E5E5)
val DarkBorderGray = Color(0xFF444444)


val espresso = Color(0xFF3E2723)
val peony = Color(0xFFF4C9D6)


val lilac = Color(0xFFCBA2C9)
val cream = Color(0xFFFEFBCE)

val blush = Color(0xFFE36887)
val morningButter = Color(0xFFF3D98F)

val celadon = Color(0xFFA8D3A8)
val chocolatePlum = Color(0xFF553832)

val softLinen = Color(0xFFF5F1E6)
val cherry = Color(0xFFF9A8BB)

val hotFuchsia = Color(0xFFF8395A)
val cottonRose = Color(0xFFEEB3B5)

val plumNoir = Color(0xFF341F28)
val coolBlue = Color(0xFFD8EFFD)





val ColorScheme.focusedTextFieldText: Color
    @Composable
    get() = if (isSystemInDarkTheme()) whiteVariant else black

val ColorScheme.unFocusedTextFieldText
@Composable
get() = if (isSystemInDarkTheme()) gray else lightGray

val ColorScheme.textFieldContainer
@Composable
get() = if (isSystemInDarkTheme()) gray else lightGray