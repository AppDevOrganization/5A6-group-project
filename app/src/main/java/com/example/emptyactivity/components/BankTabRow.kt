package com.example.emptyactivity.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.BankDestination
import java.util.Locale

/**
 * Date of Retrieval: 2023/11/02
 * All Tab-related functions and variables are based on the ones in the Rally app from the Navigation codelab.
 * https://developer.android.com/codelabs/jetpack-compose-navigation
 */
@Composable
fun BankTabRow(
    screens: List<BankDestination>,
    onTabSelected: (BankDestination) -> Unit,
    currentScreen: BankDestination
) {
    Surface(
        Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Row(Modifier.selectableGroup()) {
            screens.forEach { screen ->
                BankTab(
                    text = screen.route,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}

@Composable
private fun BankTab(
    text: String,
    onSelected: () -> Unit,
    selected: Boolean
) {
    Row(
        modifier = Modifier
            .padding(13.dp)
            .height(64.dp)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
            .clearAndSetSemantics { contentDescription = text }
    ) {
        Spacer(Modifier.width(10.dp))
        Text(text.uppercase(Locale.getDefault()))
    }
}