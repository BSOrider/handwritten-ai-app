package com.plcoding.weatherapp.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawingView() {
    val path by remember { mutableStateOf(Path()) }

    var points by remember { mutableStateOf<List<Offset>>(emptyList()) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
//                detectDragGestures { change, dragAmount ->
//                    change.consumeAllChanges()
//                    val position = change.position
//
//                }
                detectDragGestures(
                    onDragStart = { touch ->
                        points = listOf(touch)
                    },
                    onDrag = { change, _ ->
                        val pointsFromHistory = change.historical
                            .map { it.position }
                            .toTypedArray()
                        val newPoints = listOf(*pointsFromHistory, change.position)
                        points = points + newPoints

                    }
                )
            }
    ) {
        if (points.size > 1) {
            path.apply {
                val firstPoint = points.first()
                val rest = points.subList(1, points.size - 1)

                moveTo(firstPoint.x, firstPoint.y)
                rest.forEach {
                    lineTo(it.x, it.y)
                }
            }

            drawPath(path, Color.White, style = Stroke(width = 10f))
        }
    }
}