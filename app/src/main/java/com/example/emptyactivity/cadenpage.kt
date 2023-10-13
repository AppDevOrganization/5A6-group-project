package com.example.emptyactivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val REQUIRED_SIZE = 150.dp

class CadenPage {
    @Composable
    fun MainPage(breathList: MutableList<Double>, modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
        ) {
            HomeSection(title = "Your Previous Records", modifier = modifier) {
                BreathListDisplay(breathList = breathList)
            }
            Spacer(Modifier.padding(20.dp))
            HomeSection(title = "Challenge Yourself", modifier = modifier) {
                BreathButton(breathList = breathList)
            }
        }
    }

    @Composable
    fun BreathButton(breathList: MutableList<Double>, modifier: Modifier = Modifier) {
        val defaultElapsedTimeValue = 0.toDouble()
        var isHoldingBreath by rememberSaveable { mutableStateOf(false) }
        var elapsedTime by rememberSaveable { mutableStateOf(defaultElapsedTimeValue) }
        var beginningTime by rememberSaveable { mutableStateOf(System.currentTimeMillis()) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(top = 10.dp)
        ) {
            Text(
                text = if (!isHoldingBreath) "Click to start" else "Recording...",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = {
                    isHoldingBreath = !isHoldingBreath

                    if (isHoldingBreath) {
                        beginningTime = System.currentTimeMillis()
                    }
                    else {
                        elapsedTime = (System.currentTimeMillis() - beginningTime).toDouble() / 1000
                        breathList.add(element = elapsedTime)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .requiredSize(REQUIRED_SIZE)
            ) {
                Text(
                    text = if (!isHoldingBreath) "Breathe in" else "Breathe out",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            if (elapsedTime != defaultElapsedTimeValue && !isHoldingBreath) {
                val elapsedTimeText = String.format("%.2f", elapsedTime)

                Text(
                    text = "Time: ${elapsedTimeText}s",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            else {
                Text(
                    text = "",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }

    @Composable
    fun HomeSection(
        title: String,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.paddingFromBaseline(top = 20.dp, bottom = 6.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth()
            )
            content()
        }
    }

    @Composable
    fun BreathListDisplay(breathList: List<Double>, modifier: Modifier = Modifier) {
        val newList = breathList.reversed()

        Surface(
//        color = MaterialTheme.colorScheme.primaryContainer,
            shape = MaterialTheme.shapes.medium,
            modifier = modifier
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 15.dp),
                modifier = modifier.height(168.dp)
            ) {
                if (breathList.count() > 0) {
                    items(items = newList) { item ->
                        BreathListContainer(text = "${breathList.indexOf(item) + 1}.       ${String.format("%.2f", item)}s")
                    }
                }
                else {
                    items(items = listOf("You have no previous records")) { item ->
                        BreathListContainer(text = item, true)
                    }
                }
            }
        }
    }


    @Composable
    fun BreathListContainer(text: String, isError: Boolean = false) {
        Surface(
            color = if (!isError) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(5.dp)
            )
        }
    }

}