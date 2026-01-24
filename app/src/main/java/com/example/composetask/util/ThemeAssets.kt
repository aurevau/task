package com.example.composetask.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.example.composetask.R
import com.example.composetask.ui.theme.AppTheme

@Composable
fun logoForTheme(
    theme: AppTheme
): Int {

    val dark = isSystemInDarkTheme()

    return when (theme) {

        AppTheme.BLOSSOM ->
            if (dark) R.drawable.morning_butter_logo
            else R.drawable.blush_logo

        AppTheme.ESPRESSO ->
            if (dark) R.drawable.peony_logo
            else R.drawable.espresso_logo

        AppTheme.HOT_FUCHSIA ->
            if (dark) R.drawable.cotton_rose_logo
            else R.drawable.hot_fucshia_logo

        AppTheme.LILAC ->
            if (dark) R.drawable.cream_logo
            else R.drawable.lilac_logo

        AppTheme.COOL_BLUE ->
            if (dark) R.drawable.cool_blue_logo
            else R.drawable.plum_noir_logo

        AppTheme.CHOCOLATE_PLUM ->
            if (dark) R.drawable.celadon_logo
            else R.drawable.chocolate_plum_logo

        AppTheme.SOFT_LINEN ->
            if (dark) R.drawable.soft_linen_logo
            else R.drawable.cherry_logo

        AppTheme.DEFAULT ->
            R.drawable.logo
    }
}