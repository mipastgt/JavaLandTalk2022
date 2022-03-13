package org.rosettacode.polyspiral

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import javax.swing.Timer
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalGraphicsApi::class)
private fun drawSpiral(g: DrawScope, length: Int, angleIncrement: Float, uiScale: Float) {
    var x1 = g.size.width / 2.0f
    var y1 = g.size.height / 2.0f
    var len = length * uiScale
    var angle = angleIncrement
    for (i in 0 until 150) {
        val color = Color.hsv(360f * (i / 150.0f), 1.0f, 1.0f)
        val x2 = (x1 + cos(angle) * len).toFloat()
        val y2 = (y1 - sin(angle) * len).toFloat()
        g.drawLine(color, Offset(x1, y1), Offset(x2, y2), 1f * uiScale)
        x1 = x2
        y1 = y2
        len += 3 * uiScale
        angle = ((angle + angleIncrement) % (PI * 2.0)).toFloat()
    }
}

@Composable
private fun PolySpiralApp() {
    var inc by remember { mutableStateOf(0.0) }
    val uiScale = LocalDensity.current.density
    Timer(40) {
        inc = (inc + 0.05) % 360.0
    }.start()
    MaterialTheme {
        Surface {
            Canvas(modifier = Modifier.size(640.dp, 640.dp).background(Color.White).fillMaxSize().clipToBounds()) {
                drawSpiral(this, 5, Math.toRadians(inc).toFloat(), uiScale)
            }
        }
    }
}

fun main() = singleWindowApplication(
    title = "PolySpiral - Compose",
    state = WindowState(size = DpSize.Unspecified)
) {
    PolySpiralApp()
}
