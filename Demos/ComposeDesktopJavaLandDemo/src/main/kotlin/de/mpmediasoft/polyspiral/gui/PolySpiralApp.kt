package de.mpmediasoft.polyspiral.gui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import de.mpmediasoft.polyspiral.model.PolySpiralManager
import de.mpmediasoft.polyspiral.model.drawSpiral

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PolySpiralApp() {
    val coroutineScope = rememberCoroutineScope()
    val polySpiralManager = remember { PolySpiralManager(coroutineScope) }
    val polySpiralManagerState by polySpiralManager.polySpiralManagerState.collectAsState()
    val uiScale = LocalDensity.current.density

    fun angleIncrementDegString() = "${polySpiralManagerState.angleIncrementDeg.toInt().toString()}º"

    MaterialTheme {
        Surface {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().background(Color.Gray)) {
                    Button(
                        onClick = { if (polySpiralManagerState.isRendering) polySpiralManager.stopRendering() else polySpiralManager.startRendering() },
                        modifier = Modifier.padding(10.dp).width(100.dp)
                    ) {
                        Text(text = if (polySpiralManagerState.isRendering) "Stop" else "Start")
                    }

                    DataTooltipArea(angleIncrementDegString()) {
                        Card(
                            border = BorderStroke(2.dp,Color.Red),
                            backgroundColor = Color.White
                        ) {
                            CircularProgressIndicator(
                                progress = (polySpiralManagerState.angleIncrementDeg / 360).toFloat(),
                                modifier = Modifier.size(36.dp).padding(5.dp),
                                strokeWidth = 13.dp
                            )
                        }
                    }

                    DataTooltipArea(angleIncrementDegString()) {
                        Slider(
                            value = polySpiralManagerState.angleIncrementDeg.toFloat(),
                            valueRange = 0f..360f,
                            onValueChange = { polySpiralManager.angleIncrementDeg = it.toDouble() },
                            modifier = Modifier.padding(10.dp).width(200.dp)
                        )
                    }

                    Button(
                        onClick = { polySpiralManager.reset() },
                        modifier = Modifier.padding(10.dp).width(100.dp)
                    ) {
                        Text(text = "Reset")
                    }
                }

                Canvas(modifier = Modifier.fillMaxSize().clipToBounds().background(Color.White)) {
                    with (polySpiralManagerState) {
                        drawSpiral(ComposeDrawScope(this@Canvas), length * uiScale, lengthIncrement * uiScale, angleIncrementDeg, strokeWidth * uiScale)
                    }
                }
            }
        }
    }
}
