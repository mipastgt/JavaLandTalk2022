package de.mpmediasoft.polyspiral.gui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
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

    Surface {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .background(Color(210, 230, 255))
                    .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = { if (polySpiralManagerState.isRendering) polySpiralManager.stopRendering() else polySpiralManager.startRendering() },
                    modifier = Modifier.width(70.dp)
                ) {
                    Text(text = if (polySpiralManagerState.isRendering) "Stop" else "Start")
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(70.dp, 36.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                ) {
                    Text(
                        text = "%.2fº".format(polySpiralManagerState.angleIncrementDeg)
                    )
                }

                Slider(
                    value = polySpiralManagerState.angleIncrementDeg.toFloat(),
                    valueRange = 0f..360f,
                    onValueChange = { polySpiralManager.angleIncrementDeg = it.toDouble() },
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = { polySpiralManager.reset() },
                    modifier = Modifier
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