package com.ikanoshiokara.todoy.components.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ListSpacer(
    modifier: Modifier = Modifier,
    spacerColor: Color = if(isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
) {
    Spacer(modifier = modifier
        .height(1.dp)
        .background(spacerColor)
        .fillMaxWidth()
    )
}