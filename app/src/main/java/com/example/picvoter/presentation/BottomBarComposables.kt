package com.example.picvoter.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomButtons(
    modifier: Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomButton(
            modifier = modifier,
            text = "Gallery",
            onClick = {}
        )
        BottomButton(
            modifier = modifier,
            text = "Voter",
            onClick = {}
        )
    }
}

@Composable
fun BottomButton(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        elevation = null,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        modifier = modifier.padding(horizontal = 25.dp, vertical = 5.dp),
        content = { Text(text = text) },
        onClick = onClick
    )
}