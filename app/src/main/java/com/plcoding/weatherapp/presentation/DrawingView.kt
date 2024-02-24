package com.plcoding.weatherapp.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawingView() {
    val path by remember { mutableStateOf(Path()) }
    var points by remember { mutableStateOf<List<Offset>>(emptyList()) }

    Column {

        Canvas(
            modifier = Modifier
                .width(360.dp)
                .height(360.dp)
                .background(color = Color.White)
                .border(6.dp, color = Color.Black)
                .pointerInput(Unit) {
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

                drawPath(path, Color.Black, style = Stroke(width = 10f))
            }
        }
        Row(modifier = Modifier.width(360.dp).padding(30.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { /*TODO*/ }) {
                Text("Clear")
            }
            Button(onClick = { /*TODO*/ }) {
                Text("Submit")
            }
        }
    }
}