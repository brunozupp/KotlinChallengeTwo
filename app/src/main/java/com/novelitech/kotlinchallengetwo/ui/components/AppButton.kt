package com.novelitech.kotlinchallengetwo.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    color: Long,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(color)
        ),
        onClick = { onClick() }
    ) {
        Text(
            text = text
        )
    }
}