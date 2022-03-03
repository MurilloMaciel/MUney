package com.maciel.murillo.finance_manager.compose_test

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun FabTest(text: String, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Text(text = text)
    }
}