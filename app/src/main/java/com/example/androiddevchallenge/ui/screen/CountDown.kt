/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.purple500
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.max

@ExperimentalAnimationApi
@Composable
fun CountDown() {
    var totalTime by remember { mutableStateOf(0) }
    var timeRemaining by remember { mutableStateOf(0) }
    var timerRunning by remember { mutableStateOf(false) }

    val seconds = timeRemaining % 60
    val minutes = timeRemaining / 60
    val formatted = "%02d:%02d".format(minutes, seconds)

    if (timerRunning) {
        LaunchedEffect("Timer") {
            while (isActive) {
                delay(1000)
                if (isActive) {
                    timeRemaining--
                    if (timeRemaining == 0) {
                        totalTime = 0
                        timerRunning = false
                    }
                }
            }
        }
    }

    val target = when {
        !timerRunning && totalTime != timeRemaining -> 0f
        !timerRunning -> 1f
        else -> max(timeRemaining.toFloat() - 1, 0f) / totalTime
    }

    val progress by animateFloatAsState(
        targetValue = target,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.aspectRatio(1f), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = 1F,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxSize(),
                strokeWidth = 8.dp
            )
            if (timerRunning) {
                CircularProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    color = MaterialTheme.colors.secondary,
                    strokeWidth = 8.dp,
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row {
                    Text(text = formatted, style = MaterialTheme.typography.h2)
                }
                Row {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .size(36.dp)
                                .background(
                                    color = if (!timerRunning) purple500 else Color.Gray,
                                ),
                            onClick = {
                                totalTime += 60
                                timeRemaining = totalTime
                            },
                            enabled = !timerRunning
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowDropUp,
                                contentDescription = "Increment",
                                tint = Color.White
                            )
                        }
                        Card(
                            shape = RectangleShape,
                            border = BorderStroke(1.dp, Color.White),
                            backgroundColor = purple500
                        ) {
                            Text(
                                text = "MM",
                                modifier = Modifier
                                    .padding(horizontal = 4.dp).defaultMinSize(30.dp),
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }

                        IconButton(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .size(36.dp)
                                .background(
                                    color = if (!timerRunning && totalTime >= 60) purple500 else Color.Gray,
                                ),
                            onClick = {
                                if (totalTime >= 60) {
                                    totalTime -= 60
                                    timeRemaining = totalTime
                                } else {
                                    totalTime = 1
                                    timeRemaining = totalTime
                                }
                            },
                            enabled = !timerRunning && totalTime >= 60
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowDropDown,
                                contentDescription = "Decrement",
                                tint = Color.White
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .size(36.dp)
                                .background(
                                    color = if (!timerRunning) purple500 else Color.Gray,
                                ),
                            onClick = {
                                totalTime += 1
                                timeRemaining = totalTime
                            },
                            enabled = !timerRunning
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowDropUp,
                                contentDescription = "Increment",
                                tint = Color.White
                            )
                        }
                        Card(
                            shape = RectangleShape,
                            border = BorderStroke(1.dp, Color.White),
                            backgroundColor = purple500

                        ) {
                            Text(
                                text = "SS",
                                modifier = Modifier
                                    .padding(horizontal = 4.dp).defaultMinSize(30.dp),
                                textAlign = TextAlign.Center,
                                color = Color.White,
                            )
                        }
                        IconButton(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .size(36.dp)
                                .background(
                                    color = if (!timerRunning && totalTime > 0) purple500 else Color.Gray,
                                ),
                            onClick = {
                                if (totalTime > 0) {
                                    totalTime -= 1
                                    timeRemaining = totalTime
                                }
                            },
                            enabled = !timerRunning && totalTime > 0
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowDropDown,
                                contentDescription = "Decrement",
                                tint = Color.White
                            )
                        }
                    }
                }
                IconButton(
                    onClick = {
                        if (timerRunning) {
                            timerRunning = false
                            timeRemaining = 0
                            totalTime = 0
                        } else {
                            if (timeRemaining != totalTime) {
                                timeRemaining = totalTime
                            } else {
                                timerRunning = true
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .size(60.dp)
                        .background(
                            color = if (totalTime > 0) purple500 else Color.Gray,
                            shape = CircleShape
                        ),
                    enabled = totalTime > 0,

                ) {
                    Icon(
                        imageVector = if (timerRunning || timeRemaining != totalTime) Icons.Outlined.RestartAlt else Icons.Filled.PlayArrow,
                        contentDescription = if (timerRunning || timeRemaining != totalTime) "Reset" else "Start",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}
