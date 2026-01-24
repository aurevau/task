package com.example.composetask.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = gray,
    surface = black,
    background = black,
    onSurface = whiteVariant,
    onBackground = whiteVariant,
    outline = DarkBorderGray


)

private val LightColorScheme = lightColorScheme(
    primary = black,
    surface = whiteVariant,
    background = whiteVariant,
    onSurface = black,
    onBackground = black,
    outline = LightBorderGray


)

private val BlossomLightScheme = lightColorScheme(
    primary = blush,
    onPrimary = morningButter,


    tertiary = cherry,

    background = morningButter,
    onBackground = blush,
    outline = blush,

    surface = morningButter,
    onSurface = blush,
    error = red
)

private val BlossomDarkScheme = darkColorScheme(
    primary = morningButter,
    onPrimary = blush,


    tertiary = cherry,

    background = blush,
    onBackground = morningButter,
    outline = morningButter,

    surface = blush,
    onSurface = morningButter,
    error = red
)

private val HotFuchsiaDarkScheme = darkColorScheme(
    primary = cottonRose,
    onPrimary = hotFuchsia,


    tertiary = cherry,

    background = hotFuchsia,
    onBackground = cottonRose,
    outline = cottonRose,

    surface = hotFuchsia,
    onSurface = cottonRose,
    error = red
)

private val HotFuchsiaLightScheme = lightColorScheme(
    primary = hotFuchsia,
    onPrimary = cottonRose,


    tertiary = cherry,

    background = cottonRose,
    onBackground = hotFuchsia,
    outline = hotFuchsia,

    surface = cottonRose,
    onSurface = hotFuchsia,
    error = red
)

private val EspressoLightScheme = lightColorScheme(
    primary = espresso,
    onPrimary = peony,


    tertiary = cherry,

    background = peony,
    onBackground = espresso,
    outline = espresso,

    surface = peony,
    onSurface = espresso,
    error = red
)

private val EspressoDarkScheme = darkColorScheme(
    primary = peony,
    onPrimary = espresso,


    tertiary = cherry,

    background = espresso,
    onBackground = peony,
    outline = peony,

    surface = espresso,
    onSurface = peony,
    error = red
)

private val CoolBlueDarkScheme = darkColorScheme(
    primary = coolBlue,
    onPrimary = plumNoir,


    tertiary = cherry,

    background = plumNoir,
    onBackground = coolBlue,
    outline = coolBlue,

    surface = plumNoir,
    onSurface = coolBlue,
    error = red
)

private val CoolBlueLightScheme = lightColorScheme(
    primary = plumNoir,
    onPrimary = coolBlue,


    tertiary = cherry,

    background = coolBlue,
    onBackground = plumNoir,
    outline = plumNoir,

    surface = coolBlue,
    onSurface = plumNoir,
    error = red
)

private val ChocolatePlumLightScheme = lightColorScheme(
    primary = chocolatePlum,
    onPrimary = celadon,


    tertiary = cherry,

    background = celadon,
    onBackground = chocolatePlum,
    outline = chocolatePlum,

    surface = celadon,
    onSurface = chocolatePlum,
    error = red
)

private val ChocolatePlumDarkScheme = darkColorScheme(
    primary = celadon,
    onPrimary = chocolatePlum,


    tertiary = cherry,

    background = chocolatePlum,
    onBackground = celadon,
    outline = celadon,

    surface = chocolatePlum,
    onSurface = celadon,
    error = red
)

private val LilacDarkScheme = darkColorScheme(
    primary = cream,
    onPrimary = lilac,


    tertiary = cherry,

    background = lilac,
    onBackground = cream,
    outline = cream,

    surface = lilac,
    onSurface = cream,
    error = red
)

private val LilacLightScheme = lightColorScheme(
    primary = lilac,
    onPrimary = cream,


    tertiary = cherry,

    background = cream,
    onBackground = lilac,
    outline = lilac,

    surface = cream,
    onSurface = lilac,
    error = red
)

private val SoftLinenLightScheme = lightColorScheme(
    primary = cherry,
    onPrimary = softLinen,


    tertiary = cherry,

    background = softLinen,
    onBackground = cherry,
    outline = cherry,

    surface = softLinen,
    onSurface = cherry,
    error = red
)

private val SoftLinenDarkScheme = darkColorScheme(
    primary = softLinen,
    onPrimary = cherry,


    tertiary = cherry,

    background = cherry,
    onBackground = softLinen,
    outline = softLinen,

    surface = cherry,
    onSurface = softLinen,
    error = red
)

@Composable
fun ComposeTaskTheme(
    theme: AppTheme = AppTheme.DEFAULT,
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        theme == AppTheme.DEFAULT && dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        theme == AppTheme.BLOSSOM ->
            if (darkTheme) BlossomDarkScheme else BlossomLightScheme

        theme == AppTheme.HOT_FUCHSIA ->
            if (darkTheme) HotFuchsiaDarkScheme else HotFuchsiaLightScheme

        theme == AppTheme.CHOCOLATE_PLUM ->
            if (darkTheme) ChocolatePlumDarkScheme else ChocolatePlumLightScheme

        theme == AppTheme.ESPRESSO ->
            if (darkTheme) EspressoDarkScheme else EspressoLightScheme

        theme == AppTheme.COOL_BLUE ->
            if (darkTheme) CoolBlueDarkScheme else CoolBlueLightScheme

        theme == AppTheme.LILAC ->
            if (darkTheme) LilacDarkScheme else LilacLightScheme

        theme == AppTheme.SOFT_LINEN ->
            if (darkTheme) SoftLinenDarkScheme else SoftLinenLightScheme

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}